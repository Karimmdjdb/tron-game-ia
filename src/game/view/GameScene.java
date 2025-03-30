package game.view;

import game.model.platform.Platform;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;


/**
 * Classe qui spécialise la classe Scene.
 */
public class GameScene extends Scene {

    private GameCanvas canvas;

    /**
     * Constructeur de la classe.
     * @param parent le noeud JavaFX parent
     * @param width la largeur de la scéne
     * @param height la hauteur de la scéne
     */
    public GameScene(Parent parent, double width, double height, Platform platform) {
        super(parent, width, height);
        setFill(Color.BLACK);
        canvas = new GameCanvas(width, height, platform); // création du canvas
        canvas.widthProperty().bind(this.widthProperty());
        canvas.heightProperty().bind(this.heightProperty());
        ((Group)parent).getChildren().add(canvas); // ajout du canvas au groupe root

        // ajout des ecouteurs d'évenement de changement de taille
        widthProperty().addListener((ops, oldVal, newVal) -> {
            redraw();
        });
        heightProperty().addListener((ops, oldVal, newVal) -> {
            redraw();
        });
    }

    /**
     * Redimensionne le canvas pour correspondre à la scéne.
     */
    private void redraw() {
        canvas.draw();
    }
}
