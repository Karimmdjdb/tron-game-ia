package game.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Pos;

public class AlgorithmSelectWindow extends Application {

    // Listner pour les strategies selectionner
    public interface StrategySelectedListener {
        void onStrategySelected(String teamAStrategy, String teamBStrategy);
    }

    private StrategySelectedListener listener;

    public void setOnStrategySelected(StrategySelectedListener listener) {
        this.listener = listener;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Choisir l'algorithme pour chaque équipe");

        Text title = new Text("Choisissez un algorithme pour chaque équipe");
        title.setFont(Font.font("Arial", 15));

        // equipe A
        VBox teamABox = new VBox(10);
        teamABox.setAlignment(Pos.CENTER);  
        teamABox.getChildren().add(new javafx.scene.control.Label("Team A Algorithm:"));
        ComboBox<String> teamAComboBox = new ComboBox<>();
        teamAComboBox.getItems().addAll("MaxNStrategy", "ParanoidStrategy", "RandomStrategy");
        teamABox.getChildren().add(teamAComboBox);

        // equipe B
        VBox teamBBox = new VBox(10);
        teamBBox.setAlignment(Pos.CENTER);
        teamBBox.getChildren().add(new javafx.scene.control.Label("Team B Algorithm:"));
        ComboBox<String> teamBComboBox = new ComboBox<>();
        teamBComboBox.getItems().addAll("MaxNStrategy", "ParanoidStrategy", "RandomStrategy");
        teamBBox.getChildren().add(teamBComboBox);


        Button startButton = new Button("Commencer la partie");
        startButton.setOnAction(e -> {
            String teamAStrategy = teamAComboBox.getValue();
            String teamBStrategy = teamBComboBox.getValue();
            
            // Appeler le listener pour signaler les stratégies sélectionnées
            if (listener != null) {
                listener.onStrategySelected(teamAStrategy, teamBStrategy);
            }
            primaryStage.close();
        });

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(title, teamABox, teamBBox, startButton);

        // un peu de CSS
        root.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 20px;");
        startButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        title.setStyle("-fx-fill: #333;");

        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.show();
    }
}
