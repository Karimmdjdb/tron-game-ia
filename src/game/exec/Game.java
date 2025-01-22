package game.exec;

import game.controller.Controller;
import game.model.entities.Bike;
import game.model.platform.Platform;
import game.view.GameScene;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * Classe éxécutable qui lance l'application.
 */
public class Game extends Application {
    private long last_update = 0;
    private final static long TICK = 41_000_000;

    /**
     * {@inheritdoc}
     */
    @Override
    public void start(Stage stage) throws Exception {

        Group root = new Group();
        Controller control = new Controller(Platform.getInstance());
        AnimationTimer timer = new AnimationTimer() {
            /**
             * Cette méthode sera executée à chaque actualisation de l'affichage avec un certain frame rate
             */
            public void handle(long now) {
                if(now - last_update >= TICK) { // condition pour mettre à jour le modéle à une certaine fréquence (tick)
                    control.update();
                    last_update = now;
                }
            }
        };

        timer.start();

        double width = 500;
        double height = 500;
        Scene scene = new GameScene(root, width, height);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * méthode d'entrée du programme.
     * @param args paramétres spécifiés au lancement du programme
     */
    public static void main(String[] args) {
        Platform platform = Platform.getInstance();
        Bike b1 = new Bike(platform.getRandomFreePosition());
        platform.addBike(b1);
        Bike b2 = new Bike(platform.getRandomFreePosition());
        platform.addBike(b2);
        System.out.println("Bike 1 : x=" + b1.getHeadPosition().getCordX() + " y=" + b1.getHeadPosition().getCordY());
        System.out.println("Bike 2 : x=" + b2.getHeadPosition().getCordX() + " y=" + b2.getHeadPosition().getCordY());
        System.out.println("Everything OK !");

        launch(args);
    }
}
