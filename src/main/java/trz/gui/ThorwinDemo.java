package trz.gui;

/**
 * Created with IntelliJ IDEA.
 * User: luigi
 * Date: 17/01/18
 * Time: 15.57
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;


import static thorwin.math.Math.polyfit;
import static thorwin.math.Math.polynomial;

public class ThorwinDemo extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        // stup the chart
        XYChart.Series<Number,Number> series1 = new XYChart.Series<>();
        XYChart.Series<Number,Number> series2 = new XYChart.Series<>();

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        LineChart<Number,Number> chart =
                new LineChart<Number, Number>(xAxis, yAxis);
        chart.setCreateSymbols(false);
        chart.getData().add(series1);
        chart.getData().add(series2);

        // setup chart series
        double[] xs = {0.0, 1.0, 2.0, 3.0, 4.0, 5.0};
        double[] ys = {0.5, 1.3, 2.4, 5.6, 8.8, 9.1};

        for (int i = 0; i < xs.length; i++) {
            series1.getData().add(new XYChart.Data<>(xs[i], ys[i]));
        }

        // calculate the polynomial coefficients and calculate trend points
        double[] coefficients = polyfit(xs, ys, 2);

        for (double x = 0; x <= 5.0; x += 0.05) {
            double y = polynomial(x, coefficients);
            series2.getData().add(new XYChart.Data<>(x,y));
        }

        // setup scene and stage
        Scene scene = new Scene( chart );

        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(ThorwinDemo.class, args);
    }
}
