package trz.gui;

/**
 * Created with IntelliJ IDEA.
 * User: luigi
 * Date: 16/01/18
 * Time: 14.32
 */
import javafx.animation.Interpolator;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ExperimentalSplineChart extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart lineChart = new LineChart(xAxis, yAxis);
        lineChart.setCreateSymbols(false);
        lineChart.setData(this.getChartData());
        lineChart.setTitle("Chart");

        StackPane root = new StackPane();
        root.getChildren().add(lineChart);
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    private ObservableList<Series<Double, Double>> getChartData() {
        double deltaT = 0.1;
        double steps = 60 / deltaT;

        double aValue = 10;
        ObservableList<Series<Double, Double>> answer = FXCollections
                .observableArrayList();
        Series<Double, Double> aSeries = new Series();
        aSeries.setName("pressure");

        double[] xVals = {0,10,20,30,40,50,60,70};
        double[] yVals = {-4, -2, -1, 0, 1, 2, 3, 4};
        Interpolator pathInterpolator = new BestFitSplineInterpolator(xVals, yVals);



        for (int i = 0; i < (int)steps; i++) {
            double timePoint = deltaT * i;
            aValue = pathInterpolator.interpolate(timePoint, 4, deltaT);


            aSeries.getData().add(new XYChart.Data(timePoint, aValue));

        }
        answer.addAll(aSeries);
        return answer;
    }
}