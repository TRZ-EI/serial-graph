package trz.gui;

/**
 * Created with IntelliJ IDEA.
 * User: luigi
 * Date: 16/01/18
 * Time: 14.32
 */
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ChartSandBoxForSpline extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart lineChart = new LineChart(xAxis, yAxis);
        lineChart.setCreateSymbols(false);
        lineChart.setData(getChartData());
        lineChart.setTitle("Chart");

        StackPane root = new StackPane();
        root.getChildren().add(lineChart);
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    private ObservableList<XYChart.Series<String, Double>> getChartData() {
        double aValue = 10;
        double cValue = 20;
        ObservableList<XYChart.Series<String, Double>> answer = FXCollections
                .observableArrayList();
        Series<String, Double> aSeries = new Series<String, Double>();
        Series<String, Double> cSeries = new Series<String, Double>();
        aSeries.setName("a");
        cSeries.setName("C");

        for (int i = 2001; i < 2021; i++) {
            aSeries.getData().add(new XYChart.Data(Integer.toString(i), aValue));
            aValue = aValue + Math.random()*100 -50;
            cSeries.getData().add(new XYChart.Data(Integer.toString(i), cValue));
            cValue = cValue + Math.random()*100 -50 ;
        }
        answer.addAll(aSeries, cSeries);
        return answer;
    }
}