package trz.gui;

/**
 * Created with IntelliJ IDEA.
 * User: luigi
 * Date: 14/01/18
 * Time: 18.23
 */


import eu.hansolo.enzo.charts.SimpleLineChart;
import eu.hansolo.enzo.common.Section;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Demo extends Application {
    private static int      noOfNodes = 0;
    private SimpleLineChart chart;

    @Override public void init() {
        XYChart.Series series = new XYChart.Series();
        series.setName("temperature");
        series.getData().add(new XYChart.Data("7:00", 27.3));
        series.getData().add(new XYChart.Data("8:00", 28.2));
        series.getData().add(new XYChart.Data("9:00", 28.5));
        series.getData().add(new XYChart.Data("10:00", 29.2));
        series.getData().add(new XYChart.Data("11:00", 29.6));
        series.getData().add(new XYChart.Data("12:00", 31.5));
        series.getData().add(new XYChart.Data("13:00", 32.0));
        series.getData().add(new XYChart.Data("14:00", 31.2));
        series.getData().add(new XYChart.Data("15:00", 30.2));
        series.getData().add(new XYChart.Data("16:00", 29.68));
        series.getData().add(new XYChart.Data("17:00", 30.05));
        series.getData().add(new XYChart.Data("18:00", 31.25));

        chart = new SimpleLineChart();
        chart.setSections(new Section[]{
                new Section(0, 10),
                new Section(10, 15),
                new Section(15, 25),
                new Section(25, 30),
                new Section(30, 40)
        });
        chart.getStyleClass().addAll(SimpleLineChart.STYLE_CLASS_BLUE_TO_RED_5);
        chart.setSectionRangeVisible(true);
        chart.setUnit("°C");
        chart.setSeries(series);
    }

    @Override public void start(Stage stage) throws Exception {
        StackPane pane = new StackPane();
        pane.setPadding(new Insets(5, 5, 5, 5));
        pane.getChildren().addAll(chart);

        Scene scene = new Scene(pane);
        //scene.setFullScreen(true);

        stage.setTitle("Demo SimpleLineChart");
        stage.setScene(scene);
        stage.show();

       this.calcNoOfNodes(scene.getRoot());
        System.out.println(noOfNodes + " Nodes in SceneGraph");
    }

    @Override public void stop() {

    }

    public static void main(final String[] args) {
        Application.launch(args);
    }


    // ******************** Misc **********************************************
    private void calcNoOfNodes(Node node) {
        if (node instanceof Parent) {
            if (((Parent) node).getChildrenUnmodifiable().size() != 0) {
                ObservableList<Node> tempChildren = ((Parent) node).getChildrenUnmodifiable();
                noOfNodes += tempChildren.size();
                for (Node n : tempChildren) {
                    calcNoOfNodes(n);
                    //System.out.println(n.getStyleClass().toString());
                }
            }
        }
    }
}