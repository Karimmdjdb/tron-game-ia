package game.exec;

import game.controller.Controller;
import game.controller.GameInitializer;
import game.data.DataCollector;
import game.model.entities.Bot;
import game.model.platform.Platform;
import game.util.config.ConfigReader;
import game.util.config.Shared;
import game.view.DataVisualizer;

public class Simulation {
    public static void main(String[] args) {

        // on commence par faire varier la taille de la grille
        for(int deltaSize = 0; deltaSize < 3; deltaSize++) {
            for(int i = 1; i <= 4; i++) {
                Platform platform = new Platform();
                Controller control = new Controller(platform);

                // Initialiser le jeu avec les stratégies sélectionnées
                GameInitializer.init(platform);

                while(!platform.isGameOver()) control.update();

                Bot winner = (Bot)(platform.getWinner());
                if(winner == null) continue;
                DataCollector.addDeltaSize(Shared.DEPTH, Shared.SIZE, winner.getStrategyName());
                DataCollector.addDeltaTeam(Shared.DEPTH, Shared.NB_PLAYERS, winner.getStrategyName());
                System.out.println(i + "/500 winner : " + winner.getId());
            }
            Shared.SIZE += 50;
        }

        // on reset la taille de la grille
        Shared.SIZE = Integer.parseInt(ConfigReader.get("platform_size"));

        // puis ont fait varier le nombre de joueurs par équipe
        for(int deltaPlayers = 0; deltaPlayers < 3; deltaPlayers++) {
            for(int i = 1; i <= 4; i++) {
                Platform platform = new Platform();
                Controller control = new Controller(platform);

                // Initialiser le jeu avec les stratégies sélectionnées
                GameInitializer.init(platform);

                while(!platform.isGameOver()) control.update();

                Bot winner = (Bot)(platform.getWinner());
                if(winner == null) continue;
                DataCollector.addDeltaSize(Shared.DEPTH, Shared.SIZE, winner.getStrategyName());
                DataCollector.addDeltaTeam(Shared.DEPTH, Shared.NB_PLAYERS, winner.getStrategyName());
                System.out.println(i + "/500 winner : " + winner.getId());
            }
            Shared.NB_PLAYERS++;
        }

        // on affiche les résultats
        DataVisualizer.main(args);
    }
}
