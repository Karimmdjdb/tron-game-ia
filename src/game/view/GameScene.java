package game.view;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;


/**
 * Classe qui spécialise la classe Scene.
 */
public class GameScene extends Scene{

    private GameCanvas canvas;

    /**
     * Constructeur de la classe.
     * @param parent le noeud JavaFX parent
     * @param width la largeur de la scéne
     * @param height la hauteur de la scéne
     */
    public GameScene(Parent parent, double width, double height) {
        super(parent, width, height);
        setFill(Color.BLACK);
        canvas = new GameCanvas(width, height); // création du canvas
        ((Group)parent).getChildren().add(canvas); // ajout du canvas au groupe root

        // ajout des ecouteurs d'évenement de changement de taille
        widthProperty().addListener((ops, oldVal, newVal) -> {
            rescaleCanvas();
        });
        heightProperty().addListener((ops, oldVal, newVal) -> {
            rescaleCanvas();
        });
    }

    /**
     * Redimensionne le canvas pour correspondre à la scéne.
     */
    private void rescaleCanvas() {
        canvas.setWidth(this.getWidth());
        canvas.setHeight(this.getHeight());
        canvas.draw();
    }
}
