package uk.ac.aber.dcs.CS123.BonkSim.Forms;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import uk.ac.aber.dcs.CS123.BonkSim.World;

/**
 * Created by qinusty on 26/04/16.
 */
public class GraphForm extends javafx.application.Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        long maxY = 0;
        long maxX = 0;
        long[] data = World.getInstance().getBonkPopulations();

        primaryStage.setTitle("Bonk Simulation");
        StackPane mainPane = new StackPane();
        //axis
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Bonk Population");
        xAxis.setLabel("Cycle Number");
        // init axis
        for (int i = 0; i < data.length; i++) {
            if (data[i] > maxY) {
                maxY = data[i];
            }
            if (data[i] != 0) {
                maxX = i + 1;
            }
        }
        xAxis.setAutoRanging(true);
        yAxis.setAutoRanging(true);
        LineChart<Number, Number> chart = new LineChart<Number, Number>(xAxis, yAxis);
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Bonk Population");
        for (int i = 0; i < maxX; i++) {
            Rectangle rect = new Rectangle(0, 0);
            rect.setVisible(false);
            XYChart.Data<Number, Number> point = new XYChart.Data(i, data[i]);
            point.setNode(rect);
            series.getData().add(point);
        }

        chart.getData().add(series);
        mainPane.getChildren().add(chart);

        Scene simScene = new Scene(mainPane, 700, 400);
        primaryStage.setScene(simScene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    @Override
    public void init() throws Exception {
        super.init();
    }

    public GraphForm() {
        super();
    }






}
