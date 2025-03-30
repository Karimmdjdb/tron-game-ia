package game.view;

import game.exec.Simulation;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WaitingWindow extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Création du contenu de la fenêtre d'attente
        Label message = new Label("Veuillez patienter pendant les calculs...");
        ProgressIndicator progressIndicator = new ProgressIndicator();
        VBox root = new VBox(10, message, progressIndicator);
        root.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(root, 300, 150);
        Stage waitingStage = new Stage();
        waitingStage.setTitle("Calcul en cours");
        waitingStage.setScene(scene);
        waitingStage.show();

        // Tâche de simulation (à adapter avec vos calculs réels)
        Task<Void> simulationTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                effectuerSimulations();
                return null;
            }
            
            @Override
            protected void succeeded() {
                super.succeeded();
                // Ferme la fenêtre d'attente dès que les calculs sont terminés
                waitingStage.close();
                try {
                    DataVisualizer visualizer = new DataVisualizer();
                    visualizer.start(new Stage());
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
            
            @Override
            protected void failed() {
                super.failed();
                waitingStage.close();
            }
        };

        // Lancer la tâche dans un thread séparé pour ne pas bloquer l'interface utilisateur
        Thread simulationThread = new Thread(simulationTask);
        simulationThread.setDaemon(true);
        simulationThread.start();
    }
    
    /**
     * Méthode simulant des calculs longs. Remplacez ce code par vos propres simulations.
     */
    private void effectuerSimulations() {
        Simulation.main(new String[]{});
    }

    public static void main(String[] args) {
        launch(args);
    }
}
