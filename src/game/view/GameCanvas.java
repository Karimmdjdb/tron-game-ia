package game.view;

import game.model.entities.Bike;
import game.model.platform.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
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
        for(Bike bike : platform.getBikes()) drawBike(bike);
    }

    /**
     * Déssine une moto (joueur) dans le canvas.
     * @param bike l'instance de bike a désinner
     */
    private void drawBike(Bike bike) {
        // calcul des proportions
        double width = getWidth();
        double height = getHeight();
        double sw = width / Platform.SIZE;
        double sh = height / Platform.SIZE;

        // dessin
        gc.setFill(Color.PINK);
        double cord_x = bike.getHeadPosition().getCordX();
        double cord_y = bike.getHeadPosition().getCordY();
        gc.fillOval(cord_x*sw, cord_y*sh, 10, 10);
    }


}
