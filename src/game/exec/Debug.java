package game.exec;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Debug extends Application {
    private GraphicsContext gc;
    private Canvas canvas;
    private Scene scene;
    public static void main(String[] args) {
        // launch(args);
        // Platform platform = Platform.getInstance();
        // Bike b1 = new Bike(platform.getRandomFreePosition());
        // platform.setEntity(b1);
        // Bike b2 = new Bike(platform.getRandomFreePosition());
        // platform.setEntity(b2);
        // System.out.println("Bike 1 : x=" + b1.getCordX() + " y=" + b1.getCordY());
        // System.out.println("Bike 2 : x=" + b2.getCordX() + " y=" + b2.getCordY());
        System.out.println("Everything OK !");
    }

    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        scene = new Scene(root, 640, 480);

        scene.setFill(Color.rgb(7, 26, 38));

        canvas = new Canvas(scene.getWidth(), scene.getHeight());
        gc = canvas.getGraphicsContext2D();

        scene.widthProperty().addListener((ops, oldVal, newVal) -> {
            draw();
        });

        root.getChildren().add(canvas);
        stage.setScene(scene);
        stage.show();
        draw();
    }

    private void draw() {
        canvas.setWidth(scene.getWidth());
        canvas.setHeight(scene.getHeight());
        gc.setFill(Color.BLUE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.ALICEBLUE);
        gc.fillRect(0, 0, canvas.getWidth()*0.5, canvas.getHeight()*0.5);
    }
}