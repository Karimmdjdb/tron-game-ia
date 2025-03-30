package game.view;


import game.model.entities.Bike;
import game.model.platform.Platform;
import game.model.platform.Position;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
// import javafx.scene.paint.CycleMethod;
// import javafx.scene.paint.RadialGradient;
// import javafx.scene.paint.Stop;
import game.util.mvc.Observable;
import game.util.mvc.Observer;


/**
 * Classe qui spécialise la classe Canvas, ainsi qu'une vue de l'application.
 */
public class GameCanvas extends Canvas implements Observer {
    private static final Color
                        BACKGROUND,
                        TEAM1_ALIVE,
                        TEAM1_DEAD,
                        TEAM2_ALIVE,
                        TEAM2_DEAD;
    static {
        BACKGROUND = Color.rgb(16, 16, 16);
        TEAM1_ALIVE = Color.rgb(255, 87, 51, 1.0);
        TEAM1_DEAD = Color.rgb(255, 87, 51, .2);
        TEAM2_ALIVE = Color.rgb(51, 194, 255, 1.0);
        TEAM2_DEAD = Color.rgb(51, 194, 255, .2);
    }
    private GraphicsContext gc;
    private Platform platform;

    /**
     * Constructeur de la classe.
     * @param width largeur du canvas
     * @param height hauteur du canvas
     */
    public GameCanvas(double width, double height, Platform platform) {
        super(width, height);
        platform.addObserver(this);
        this.platform = platform;
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

        // réinitialisation du canvas
        gc.clearRect(0, 0, getWidth(), getHeight());

        // dessin de l'arriére plan
        gc.setFill(BACKGROUND);
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
        double sw = width / platform.getSize();
        double sh = height / platform.getSize();

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

        gc.setFill(Color.WHITESMOKE);
        gc.fillRect(cord_x*sw, cord_y*sh, sw, sh);
    }

    private void drawStreak(Bike bike) {
        // calcul des proportions
        double width = getWidth();
        double height = getHeight();
        double sw = width / platform.getSize();
        double sh = height / platform.getSize();

        // dessin
        for(Position streak : bike.getStreakPositions()) {
            if(platform.getTeamA().getMembers().contains(bike)){
                gc.setFill(bike.isAlive() ? TEAM1_ALIVE : TEAM1_DEAD);
            }
            if(platform.getTeamB().getMembers().contains(bike)) {
                gc.setFill(bike.isAlive() ? TEAM2_ALIVE : TEAM2_DEAD);
            }
            double cord_x = streak.getCordX();
            double cord_y = streak.getCordY();
            gc.fillRect(cord_x*sw, cord_y*sh, sw, sh);
        }
    }


}
