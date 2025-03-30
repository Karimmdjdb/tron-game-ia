package game.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.*;

import game.data.DataCollector;

public class DataVisualizer extends Application {

    // Données pour "taille de grille" : profondeur -> taille de grille -> (algorithme -> parties gagnées)
    private Map<Integer, Map<Integer, Map<String, Integer>>> dataGridSize = DataCollector.getDeltaSizeResults();
    // Données pour "taille d'équipes" : profondeur -> taille d'équipe -> (algorithme -> parties gagnées)
    private Map<Integer, Map<Integer, Map<String, Integer>>> dataTeamSize = DataCollector.getDeltaTeamResults();

    public DataVisualizer() {
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Bar Charts des parties gagnées");

        // Créer une section pour la catégorie "taille de grille"
        VBox boxGrid = new VBox(10);
        boxGrid.setPadding(new Insets(10));
        Label labelGrid = new Label("Catégorie : Variation de profondeur de recherche et de taille de grille");
        HBox chartsGrid = new HBox(20);
        chartsGrid.setAlignment(Pos.CENTER);
        // Pour chaque algorithme présent dans dataGridSize, créer un bar chart
        Set<String> algosGrid = getAlgorithms(dataGridSize);
        for (String algo : algosGrid) {
            BarChart<String, Number> chart = createBarChartForAlgorithm(dataGridSize, algo, "Taille de grille");
            chartsGrid.getChildren().add(chart);
        }
        boxGrid.getChildren().addAll(labelGrid, chartsGrid);

        // Créer une section pour la catégorie "taille d'équipe"
        VBox boxTeam = new VBox(10);
        boxTeam.setPadding(new Insets(10));
        Label labelTeam = new Label("Catégorie : Variation de profondeur de recherche et d'effectif des équipes'");
        HBox chartsTeam = new HBox(20);
        chartsTeam.setAlignment(Pos.CENTER);
        // Pour chaque algorithme présent dans dataTeamSize, créer un bar chart
        Set<String> algosTeam = getAlgorithms(dataTeamSize);
        for (String algo : algosTeam) {
            BarChart<String, Number> chart = createBarChartForAlgorithm(dataTeamSize, algo, "Taille d'équipe");
            chartsTeam.getChildren().add(chart);
        }
        boxTeam.getChildren().addAll(labelTeam, chartsTeam);

        // Disposer les deux sections verticalement
        VBox mainBox = new VBox(30);
        mainBox.setAlignment(Pos.CENTER);
        mainBox.getChildren().addAll(boxGrid, boxTeam);

        BorderPane root = new BorderPane();
        Label mainLabel = new Label("Influence de la profondeur de recherche sur le jeu : impact de la taille des équipes et de la grille");
        mainLabel.setPadding(new Insets(10));
        root.setTop(mainLabel);
        BorderPane.setAlignment(mainLabel, Pos.CENTER);
        root.setCenter(mainBox);

        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Crée un BarChart pour un algorithme donné à partir des données.
     * L'axe X représente la profondeur de recherche et l'axe Y le nombre de parties gagnées.
     * Chaque série correspond à une valeur (taille de grille ou taille d'équipe).
     */
    private BarChart<String, Number> createBarChartForAlgorithm(Map<Integer, Map<Integer, Map<String, Integer>>> data,
                                                                  String algorithm,
                                                                  String sizeLabel) {
        // Axe des catégories pour la profondeur
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Profondeur");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Parties gagnées");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setBarGap(0);       // Pas d'espacement entre les barres d'une même catégorie
        barChart.setCategoryGap(10); // Vous pouvez ajuster cette valeur selon vos préférences

        barChart.setTitle("Algorithme : " + algorithm + " (" + sizeLabel + ")");
        barChart.setLegendVisible(true);

        // Récupérer l'ensemble des profondeurs (clé extérieure)
        Set<Integer> depthSet = new TreeSet<>(data.keySet());
        // Récupérer l'ensemble des tailles (clé interne)
        Set<Integer> sizeSet = new TreeSet<>();
        for (Map<Integer, Map<String, Integer>> inner : data.values()) {
            sizeSet.addAll(inner.keySet());
        }
        // Pour chaque taille, créer une série
        for (Integer size : sizeSet) {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Taille " + size);
            // Pour chaque profondeur, récupérer le nombre de parties gagnées pour l'algorithme et la taille donnée
            for (Integer depth : depthSet) {
                int wins = 0;
                if (data.containsKey(depth) && data.get(depth).containsKey(size)) {
                    wins = data.get(depth).get(size).getOrDefault(algorithm, 0);
                }
                series.getData().add(new XYChart.Data<>(depth.toString(), wins));
            }
            barChart.getData().add(series);
        }
        return barChart;
    }

    // Méthode utilitaire pour extraire l'ensemble des algorithmes d'un jeu de données.
    private Set<String> getAlgorithms(Map<Integer, Map<Integer, Map<String, Integer>>> data) {
        Set<String> algos = new TreeSet<>();
        for (Map<Integer, Map<String, Integer>> innerMap : data.values()) {
            for (Map<String, Integer> map : innerMap.values()) {
                algos.addAll(map.keySet());
            }
        }
        return algos;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
