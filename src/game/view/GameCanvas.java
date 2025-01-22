package game.view;

import game.model.entities.Bike;
import game.model.platform.Platform;
import game.model.platform.Position;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import game.util.mvc.Observable;
import game.util.mvc.Observer;


/**
 * Classe qui spécialise la classe Canvas, ainsi qu'une vue de l'application.
 */
public class GameCanvas extends Canvas implements Observer {
    private GraphicsContext gc;

    /**
     * Constructeur de la class.
     * @param width largeur du canvas
     * @param height hauteur du canvas
     */
    public GameCanvas(double width, double height) {
        super(width, height);
        Platform.getInstance().addObserver(this);
        gc = getGraphicsContext2D();
        draw();
    }

    /**
     * {@inheritdoc}
     */
    @Override
    public void update(Observable source) {
        draw();
    }

    /**
     * Déssine les éléments du jeu dans le canvas.
     */
    public void draw() {
        Platform platform = Platform.getInstance();

        // réinitialisation du canvas
        gc.clearRect(0, 0, getWidth(), getHeight());

        // dessin de l'arriére plan
        gc.setFill(Color.rgb(1, 23, 28));
        gc.fillRect(0, 0, getWidth(), getHeight());

        // dessin des joueurs
        for(Bike bike : platform.getBikes()) drawStreak(bike);
        for(Bike bike : platform.getBikes()) drawBike(bike);
    }

    /**
     * Déssine le joueur dans le canvas.
     * @param bike l'instance de bike a désinner
     */
    private void drawBike(Bike bike) {
        // calcul des proportions
        double width = getWidth();
        double height = getHeight();
        double sw = width / Platform.SIZE;
        double sh = height / Platform.SIZE;

        // dessin
        double cord_x = bike.getHeadPosition().getCordX();
        double cord_y = bike.getHeadPosition().getCordY();

        // RadialGradient gradient = new RadialGradient(
        //     -1,
        //     0.5,
        //     cord_x*sw+30/2,
        //     cord_y*sh/2,
        //     10*50,
        //     false,
        //     CycleMethod.NO_CYCLE,
        //     new Stop(0.8, Color.WHITE.deriveColor(1, 1, 1, 0.5)),
        //     new Stop(1, Color.TRANSPARENT)
        // );

        // gc.setFill(gradient);
        // gc.fillOval(cord_x*sw-10/2, cord_y*sh-10/2, 10*2, 10*2);

        gc.setFill(Color.PINK);
        gc.fillOval(cord_x*sw, cord_y*sh, 10, 10);
    }

    private void drawStreak(Bike bike) {
        // calcul des proportions
        double width = getWidth();
        double height = getHeight();
        double sw = width / Platform.SIZE;
        double sh = height / Platform.SIZE;

        // dessin
        for(Position streak : bike.getStreakPositions()) {
            gc.setFill(Color.BLUEVIOLET);
            double cord_x = streak.getCordX();
            double cord_y = streak.getCordY();
            gc.fillRect(cord_x*sw, cord_y*sh, 10, 10);
        }
    }


}
