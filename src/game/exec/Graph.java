package game.exec;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class Graph extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Exemple de graphique JavaFX");

        // Axes
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("X");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Y");

        // Graphique
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Courbe de test");

        // Données
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Série 1");
        series.getData().add(new XYChart.Data<>(0, 1));
        series.getData().add(new XYChart.Data<>(1, 3));
        series.getData().add(new XYChart.Data<>(2, 2));
        series.getData().add(new XYChart.Data<>(3, 6));

        // Ajouter les données au graphique
        lineChart.getData().add(series);

        // Scène
        Scene scene = new Scene(lineChart, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
