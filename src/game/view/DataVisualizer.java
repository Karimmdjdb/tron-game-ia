package game.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import game.data.DataCollector;

public class DataVisualizer extends Application {

    // Structure pour le graphe basé sur nbJoueursParTeam :
    // profondeur -> nbJoueursParTeam -> (nom de l'algo -> parties gagnées)
    private Map<Integer, Map<Integer, Map<String, Integer>>> dataPlayers = DataCollector.getDeltaTeamResults();
    // Structure pour le graphe basé sur taille de la grille :
    // profondeur -> taille de la grille -> (nom de l'algo -> parties gagnées)
    private Map<Integer, Map<Integer, Map<String, Integer>>> dataGridSize = DataCollector.getDeltaSizeResults();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Graphiques des parties gagnées");

        // Création du premier graphique : l'axe X représente le nombre de joueurs par équipe
        // et chaque série (couleur) représente un algorithme.
        BarChart<String, Number> barChartPlayers = createBarChartByAlgorithm(
            "Performance (Joueurs par équipe)",
            dataPlayers,
            "Nombre de joueurs par équipe",
            "Parties gagnées"
        );

        // Création du deuxième graphique : l'axe X représente la taille de la grille
        // et chaque série (couleur) représente un algorithme.
        BarChart<String, Number> barChartGrid = createBarChartByAlgorithm(
            "Performance (Taille de la grille)",
            dataGridSize,
            "Taille de la grille",
            "Parties gagnées"
        );

        VBox root = new VBox(20);
        root.getChildren().addAll(barChartPlayers, barChartGrid);

        Scene scene = new Scene(root, 800, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Crée un BarChart en agrégant les données par algorithme.
     * L'axe X représente la donnée (nombre de joueurs ou taille de la grille)
     * et chaque série (couleur) correspond à un algorithme.
     * Les valeurs sont agrégées sur toutes les profondeurs.
     *
     * @param title      Titre du graphique.
     * @param data       Structure de données : profondeur -> (donnée -> (algorithme -> parties gagnées))
     * @param xAxisLabel Label de l'axe X.
     * @param yAxisLabel Label de l'axe Y.
     * @return BarChart affichant les données agrégées.
     */
    private BarChart<String, Number> createBarChartByAlgorithm(String title,
                                                                Map<Integer, Map<Integer, Map<String, Integer>>> data,
                                                                String xAxisLabel,
                                                                String yAxisLabel) {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel(xAxisLabel);

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(yAxisLabel);

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle(title);

        // Agrégation : pour chaque algorithme, pour chaque catégorie (nombre de joueurs ou taille),
        // on somme les parties gagnées sur toutes les profondeurs.
        Map<String, Map<Integer, Integer>> aggregatedData = new HashMap<>();

        for (Map<Integer, Map<String, Integer>> innerMap : data.values()) {
            for (Map.Entry<Integer, Map<String, Integer>> entry : innerMap.entrySet()) {
                Integer categoryKey = entry.getKey(); // nombre de joueurs ou taille de grille
                Map<String, Integer> algoMap = entry.getValue();
                for (Map.Entry<String, Integer> algoEntry : algoMap.entrySet()) {
                    String algoName = algoEntry.getKey();
                    Integer wins = algoEntry.getValue();
                    aggregatedData.putIfAbsent(algoName, new HashMap<>());
                    Map<Integer, Integer> catMap = aggregatedData.get(algoName);
                    catMap.put(categoryKey, catMap.getOrDefault(categoryKey, 0) + wins);
                }
            }
        }

        // Déterminer toutes les catégories (pour l'axe X) de manière triée
        TreeSet<Integer> categories = new TreeSet<>();
        for (Map<Integer, Integer> catMap : aggregatedData.values()) {
            categories.addAll(catMap.keySet());
        }

        // Créer une série pour chaque algorithme
        for (Map.Entry<String, Map<Integer, Integer>> entry : aggregatedData.entrySet()) {
            String algoName = entry.getKey();
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(algoName);

            // Pour chaque catégorie, ajouter le point correspondant (afficher 0 si aucune donnée)
            for (Integer cat : categories) {
                Integer wins = entry.getValue().getOrDefault(cat, 0);
                series.getData().add(new XYChart.Data<>(cat.toString(), wins));
            }
            barChart.getData().add(series);
        }
        return barChart;
    }

    /**
     * Exemple de données fictives pour le graphe basé sur nbJoueursParTeam.
     */
    private Map<Integer, Map<Integer, Map<String, Integer>>> createSampleDataPlayers() {
        Map<Integer, Map<Integer, Map<String, Integer>>> sampleData = new HashMap<>();

        // Profondeur 1, nbJoueursParTeam = 3
        Map<Integer, Map<String, Integer>> teamMap1 = new HashMap<>();
        Map<String, Integer> algoMap1 = new HashMap<>();
        algoMap1.put("AlgoA", 10);
        algoMap1.put("AlgoB", 15);
        teamMap1.put(3, algoMap1);
        sampleData.put(1, teamMap1);

        // Profondeur 2, nbJoueursParTeam = 4
        Map<Integer, Map<String, Integer>> teamMap2 = new HashMap<>();
        Map<String, Integer> algoMap2 = new HashMap<>();
        algoMap2.put("AlgoA", 20);
        algoMap2.put("AlgoB", 25);
        teamMap2.put(4, algoMap2);
        sampleData.put(2, teamMap2);

        // Autre donnée pour montrer l'agrégation :
        // Profondeur 1, nbJoueursParTeam = 4
        Map<Integer, Map<String, Integer>> teamMap3 = sampleData.getOrDefault(1, new HashMap<>());
        Map<String, Integer> algoMap3 = new HashMap<>();
        algoMap3.put("AlgoA", 5);
        algoMap3.put("AlgoB", 7);
        teamMap3.put(4, algoMap3);
        sampleData.put(1, teamMap3);

        return sampleData;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
