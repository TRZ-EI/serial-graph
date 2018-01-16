package trz.gui;

/**
 * Created with IntelliJ IDEA.
 * User: luigi
 * Date: 16/01/18
 * Time: 12.29
 */

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BestFitSplineDemo extends Application {

    private static final Duration CYCLE_TIME = Duration.seconds(7);

    private static final int PLOT_SIZE = 500;
    private static final int N_SEGS    = PLOT_SIZE / 10;

    public void start(Stage stage) {
        Path path = new Path();
        path.setStroke(Color.DARKGREEN);

        final Interpolator pathInterpolator = new BestFitSplineInterpolator(
                new double[] { 0.0, 0.25, 0.5, 0.75, 1.0 },
                new double[] { 0.0, 0.5,  0.3, 0.8,  0.0 }
        );

        // interpolated spline function plot.
        plotSpline(path, pathInterpolator, true);

        // animated dot moving along the plot according to a distance over time function.
        final Interpolator timeVsDistanceInterpolator = new BestFitSplineInterpolator(
                new double[] { 0.0, 0.25, 0.5, 0.75, 1.0 },
                new double[] { 0.0, 0.1,  0.4, 0.85, 1.0 }
        );

        Circle dot = new Circle(5, Color.GREENYELLOW);
        PathTransition transition = new PathTransition(CYCLE_TIME, path, dot);
        transition.setInterpolator(timeVsDistanceInterpolator);
        transition.setAutoReverse(true);
        transition.setCycleCount(PathTransition.INDEFINITE);
        transition.play();

        // show a light grey path representing the distance over time.
        Path timeVsDistancePath = new Path();
        timeVsDistancePath.setStroke(Color.CORAL.darker());
        timeVsDistancePath.getStrokeDashArray().setAll(15d, 10d, 5d, 10d);
        plotSpline(timeVsDistancePath, timeVsDistanceInterpolator, true);

        stage.setScene(
                new Scene(
                        new Group(
                                timeVsDistancePath,
                                path,
                                dot
                        ),
                        Color.rgb(35,39,50)
                )
        );
        stage.show();
    }

    // plots an interpolated curve in segments along a path
    // if invert is true then y=0 will be in the bottom left, otherwise it is in the top right
    private void plotSpline(Path path, Interpolator pathInterpolator, boolean invert) {
        final double y0 = pathInterpolator.interpolate(0, PLOT_SIZE, 0);
        path.getElements().addAll(
                new MoveTo(0, invert ? PLOT_SIZE - y0 : y0)
        );

        for (int i = 0; i < N_SEGS; i++) {
            final double frac = (i + 1.0) / N_SEGS;
            final double x = frac * PLOT_SIZE;
            final double y = pathInterpolator.interpolate(0, PLOT_SIZE, frac);
            path.getElements().add(new LineTo(x, invert ? PLOT_SIZE - y : y));
        }
    }

    public static void main(String[] args) { launch(args); }



}