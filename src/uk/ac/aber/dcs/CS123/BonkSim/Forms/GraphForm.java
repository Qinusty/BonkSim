package uk.ac.aber.dcs.CS123.BonkSim.Forms;

import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import uk.ac.aber.dcs.CS123.BonkSim.World;

/**
 * Created by qinusty on 26/04/16.
 */
public class GraphForm extends javafx.application.Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        int maxY = 0;
        int maxX = 0;
        int[] data = World.getInstance().getBonkPopulations();

        primaryStage.setTitle("Bonk Simulation");
        StackPane mainPane = new StackPane();
        //axis
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Bonk Population");
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
            series.getData().add(new XYChart.Data(i, data[i]));
        }
        chart.getData().add(series);
        mainPane.getChildren().add(chart);



        Scene simScene = new Scene(mainPane, 700, 400);
        primaryStage.setScene(simScene);
        primaryStage.show();
        // TODO: THIS STUFF
        // CANT LAUNCH MORE THAN ONCE, ISSUE FOR LOOPING THE APPLICATION.
        // ALSO, ADD INDIVIDUAL ZAP KILLS PER CYCLE ON SEPERATE TAB
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
