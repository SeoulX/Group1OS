/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package osfinals;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 *
 * @author Drian
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Pane GraphPane;
    
    @FXML
    private LineChart seekGraph;
    
    @FXML
    private TextField reqSequence;
    
    @FXML
    private TextField headStart;
    
    @FXML
    private TextField diskSize;
    
    @FXML
    private Button runBtn;

    @FXML
    private Text headMovement;
    
    private int MAX_CYLINDERS = 0;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        runBtn.setOnAction(e -> {
            String[] requests = reqSequence.getText().split(", ");
            int headPosition = Integer.parseInt(headStart.getText());
            int totalHeadMovement = 0;
            // Create a new series for the chart
            XYChart.Series<Number, Number> series = new XYChart.Series<>();

            // Add the initial head position to the chart
            series.getData().add(new XYChart.Data<>(0,headPosition ));

            // Process each request
            int n = requests.length;
            
            for (int i = 0; i < n; i++) {
                int request = Integer.parseInt(requests[i]);

                // Calculate the distance between the current head position and the request
                int distance = Math.abs(request - headPosition);

                // Add the distance to the total head movement
                totalHeadMovement += distance;
                System.out.println("Distance Between " + request + " and " + headPosition + " is " + distance);

                // Update the head position
                headPosition = request;
                // Add the new head position to the chart
                series.getData().add(new XYChart.Data<>(i+1 , headPosition));
            }
            
            // Display the total head movement on the UI
            headMovement.setText(String.valueOf(totalHeadMovement));

            // Clear any existing data from the chart and add the new series
            seekGraph.getData().clear();
            seekGraph.getData().add(series);
            MAX_CYLINDERS = Integer.parseInt(diskSize.getText());
            // Set the chart axis bounds
            NumberAxis xAxis = (NumberAxis) seekGraph.getXAxis();
            xAxis.setAutoRanging(false);
            xAxis.setLowerBound(0);
            xAxis.setTickLabelGap(3);
            xAxis.setTickLength(5);
            xAxis.setTickUnit(1);
            xAxis.setUpperBound(10);

            NumberAxis yAxis = (NumberAxis) seekGraph.getYAxis();
            yAxis.setAutoRanging(false);
            yAxis.setTickUnit(1);
            yAxis.setLowerBound(0);
            yAxis.setTickLabelGap(3);
            yAxis.setTickLength(8);
            yAxis.setTickUnit(10);
            yAxis.setUpperBound(MAX_CYLINDERS-1);
        });
    }
}
