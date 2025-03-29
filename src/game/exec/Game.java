package game.exec;

import game.controller.Controller;
import game.controller.GameInitializer;
import game.model.platform.Platform;
import game.view.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Game extends Application {

    private long last_update = 0;
    private final static long TICK = 41_000_000;
    private String teamAStrategy;
    private String teamBStrategy;

    @Override
    public void start(Stage stage) throws Exception {
        // Lancer la fenêtre de sélection des algorithmes
        AlgorithmSelectWindow algoWindow = new AlgorithmSelectWindow();

        // Attendre que l'utilisateur sélectionne les algorithmes
        algoWindow.setOnStrategySelected((selectedTeamAStrategy, selectedTeamBStrategy) -> {
            teamAStrategy = selectedTeamAStrategy;
            teamBStrategy = selectedTeamBStrategy;
            startGame(stage);
        });

        algoWindow.start(new Stage());
    }

    private void startGame(Stage stage) {
        Platform platform = Platform.getInstance();
        Controller control = new Controller(platform);
        
        // Initialiser le jeu avec les stratégies sélectionnées
        GameInitializer.init(teamAStrategy, teamBStrategy);

        Group root = new Group();

        AnimationTimer timer = new AnimationTimer() {
            public void handle(long now) {
                if (now - last_update >= TICK) {
                    control.update();
                    last_update = now;
                }
            }
        };

        double width = 500;
        double height = 500;
        Scene scene = new GameScene(root, width, height);

        stage.setScene(scene);
        stage.show();

        timer.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
