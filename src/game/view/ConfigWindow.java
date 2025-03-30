package game.view;

import game.exec.Game;
import game.util.config.ConfigReader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Pos;

public class ConfigWindow extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Configuration du jeu");

        // Charger les paramètres depuis le fichier config.txt
        String platformSize = ConfigReader.get("platform_size");
        String depth = ConfigReader.get("depth");
        String nbPlayersPerTeam = ConfigReader.get("nb_players_per_team");
        String team1Strat = ConfigReader.get("team_1_strat");
        String team2Strat = ConfigReader.get("team_2_strat");

        // Titre
        Text title = new Text("Paramètres du jeu");
        title.setFont(Font.font("Arial", 18));
        title.setStyle("-fx-fill: #333;");

        // Paramètres communs (affichés mais non modifiables)
        VBox commonParamsBox = new VBox(10);
        commonParamsBox.setAlignment(Pos.CENTER);
        Label platformSizeLabel = new Label("Taille de la grille: " + platformSize);
        Label depthLabel = new Label("Profondeur de recherche: " + depth);
        
        platformSizeLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        depthLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        commonParamsBox.getChildren().addAll(platformSizeLabel, depthLabel);

        // Conteneur pour les équipes (2 colonnes)
        HBox teamsBox = new HBox(50);
        teamsBox.setAlignment(Pos.CENTER);

        // Équipe 1
        VBox teamABox = new VBox(10);
        teamABox.setAlignment(Pos.CENTER);
        Label teamATitle = new Label("Équipe 1");
        Label teamAPlayers = new Label("Joueurs: " + nbPlayersPerTeam);
        Label teamALabel = new Label("Algorithme: " + team1Strat);
        
        teamATitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        teamAPlayers.setStyle("-fx-font-size: 14px;");
        teamALabel.setStyle("-fx-font-size: 14px;");
        
        teamABox.getChildren().addAll(teamATitle, teamAPlayers, teamALabel);

        // Équipe 2
        VBox teamBBox = new VBox(10);
        teamBBox.setAlignment(Pos.CENTER);
        Label teamBTitle = new Label("Équipe 2");
        Label teamBPlayers = new Label("Joueurs: " + nbPlayersPerTeam);
        Label teamBLabel = new Label("Algorithme: " + team2Strat);
        
        teamBTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        teamBPlayers.setStyle("-fx-font-size: 14px;");
        teamBLabel.setStyle("-fx-font-size: 14px;");
        
        teamBBox.getChildren().addAll(teamBTitle, teamBPlayers, teamBLabel);

        teamsBox.getChildren().addAll(teamABox, teamBBox);

        // Boutons
        Button startButton = new Button("Lancer le jeu");
        startButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        startButton.setOnAction(e -> {
            try {
                Game game = new Game();
                game.start(new Stage());
                primaryStage.close();
            } catch (Exception ex){
                ex.printStackTrace();
            }

        });

        Button simulateButton = new Button("Simuler N parties");
        simulateButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px; -fx-background-color: #FF9800; -fx-text-fill: white;");
        simulateButton.setOnAction(e -> {
            try {
                WaitingWindow waiting = new WaitingWindow();
                waiting.start(new Stage());
                primaryStage.close();
            } catch (Exception ex){
                ex.printStackTrace();
            }
        });

        HBox buttonsBox = new HBox(20);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(startButton, simulateButton);

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(title, commonParamsBox, teamsBox, buttonsBox);
        root.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 20px;");

        primaryStage.setScene(new Scene(root, 500, 350));
        primaryStage.show();
    }
}
