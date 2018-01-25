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
        chart.setCreateSymbols(true);
        chart.getData().add(series1);
        chart.getData().add(series2);

        // setup chart series
        double[] xs = this.getXData();
        double[] ys = this.getYData();
//        double[] xs = {0.0, 0.5, 2.0, 3.0, 4.0, 5.0};
//        double[] ys = {0.0, 4.0, 3.0, 3.5, 3.0, 2.5};

        for (int i = 0; i < xs.length; i++) {
            series1.getData().add(new XYChart.Data<>(xs[i], ys[i]));
        }

        // calculate the polynomial coefficients and calculate trend points
        double[] coefficients = polyfit(xs, ys, 10);

        for (double x = 0; x <= 10.0; x += 0.05) {
            double y = polynomial(x, coefficients);
            series2.getData().add(new XYChart.Data<>(x,y));
        }

        // setup scene and stage
        Scene scene = new Scene( chart );

        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }
    private double[] getXData(){
        double[] retValue ={0.0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1.0,1.1,1.2,1.3,1.4,1.5,1.6,1.7,1.8,1.9,2.0,2.1,2.2,2.3,
                2.4,2.5,2.6,2.7,2.8,2.9,3.0,3.1,3.2,3.3,3.4,3.5,3.6,3.7,3.8,3.9,4.0,4.1,4.2,4.3,4.4,4.5,4.6,4.7,4.8,4.9,5.0,5.1,5.2,
                5.3,5.4,5.5,5.6,5.7,5.8,5.9,6.0,6.1,6.2,6.3,6.4,6.5,6.6,6.7,6.8,6.9,7.0,7.1,7.2,7.3,7.4,7.5,7.6,7.7,7.8,7.9,8.0,8.1,
                8.2,8.3,8.4,8.5,8.6,8.7,8.8,8.9,9.0,9.1,9.2,9.3,9.4,9.5,9.6,9.7,9.8,9.9};
        return retValue;
    }
    private double[] getYData(){
        double[] retValue = {0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,
                0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,-0.3,-0.4,-0.7,-0.8,-1.1,-1.2,-1.6,-1.9,-2.0,-2.1,-2.2,-2.3,-2.4,-2.5,-2.6,-2.7,-2.2,
                -2.2,-2.2,-2.2,-2.2,-2.2,-2.2,-1.8,-1.5,-1.2,-0.8,-0.5,-0.4,0.0,0.0,0.3,0.4,0.5,0.6,0.7,0.8,0.3,0.0,0.0,-0.3,-0.4,
                -0.7,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.1,-1.1,-1.1,-1.1,-1.1,-1.1,-1.1,-1.1,
                -1.1,-1.1};
        return retValue;
    }


    public static void main(String[] args) {
        launch(ThorwinDemo.class, args);
    }
}
