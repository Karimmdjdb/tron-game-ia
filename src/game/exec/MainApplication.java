package game.exec;

import game.view.ConfigWindow;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApplication extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        ConfigWindow configWindow = new ConfigWindow();
        configWindow.start(primaryStage);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}