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
    private final static int 
        DEPTH_STEPS = Integer.parseInt(ConfigReader.get("depth_steps")),
        DEPTH_DELTA = Integer.parseInt(ConfigReader.get("depth_delta")),
        GRID_SIZE_STEPS = Integer.parseInt(ConfigReader.get("grid_size_steps")),
        GRID_SIZE_DELTA = Integer.parseInt(ConfigReader.get("grid_size_delta")),
        PLAYERS_STEPS = Integer.parseInt(ConfigReader.get("nb_players_steps")),
        PLAYERS_DELTA = Integer.parseInt(ConfigReader.get("nb_players_delta")),
        SIMULATIONS_PER_SAMPLE = Integer.parseInt(ConfigReader.get("simulations_per_sample"));
    public static void main(String[] args) {
        // on commence par faire varier la profondeur de recherche et la taille de la grille
        for(int deltaDepth = 0; deltaDepth < DEPTH_STEPS; deltaDepth++) {
            for(int deltaSize = 0; deltaSize < GRID_SIZE_STEPS; deltaSize++) {
                for(int i = 1; i <= SIMULATIONS_PER_SAMPLE; i++) {
                    Platform platform = new Platform();
                    Controller control = new Controller(platform);
    
                    // Initialiser le jeu avec les stratégies sélectionnées
                    GameInitializer.init(platform);
    
                    while(!platform.isGameOver()) control.update();
    
                    Bot winner = (Bot)(platform.getWinner());
                    if(winner == null) continue;
                    DataCollector.addDeltaSize(Shared.DEPTH, Shared.SIZE, winner.getStrategyName());
                }
                // System.out.println("variation de profondeur : " + deltaDepth + "\n variation de taille de grille : " + deltaSize + "\n");
                Shared.SIZE += GRID_SIZE_DELTA;
            }
            Shared.SIZE = Integer.parseInt(ConfigReader.get("platform_size"));
            Shared.DEPTH += DEPTH_DELTA;
        }

        // on reset la profondeur de recherche et la taille de la grille
        Shared.DEPTH = Integer.parseInt(ConfigReader.get("depth"));
        Shared.SIZE = Integer.parseInt(ConfigReader.get("platform_size"));

        // puis ont fait varier la profondeur de recherche et le nombre de joueurs par équipe
        for(int deltaDepth = 0; deltaDepth < DEPTH_STEPS; deltaDepth++) {
            for(int deltaPlayers = 0; deltaPlayers < PLAYERS_STEPS; deltaPlayers++) {
                for(int i = 1; i <= SIMULATIONS_PER_SAMPLE; i++) {
                    Platform platform = new Platform();
                    Controller control = new Controller(platform);
    
                    // Initialiser le jeu avec les stratégies sélectionnées
                    GameInitializer.init(platform);
    
                    while(!platform.isGameOver()) control.update();
    
                    Bot winner = (Bot)(platform.getWinner());
                    if(winner == null) continue;
                    DataCollector.addDeltaTeam(Shared.DEPTH, Shared.NB_PLAYERS, winner.getStrategyName());
                }
                // System.out.println("variation de profondeur : " + deltaDepth + "\n variation de nb joueurs : " + deltaPlayers + "\n");
                Shared.NB_PLAYERS += PLAYERS_DELTA;
            }
            Shared.NB_PLAYERS = Integer.parseInt(ConfigReader.get("nb_players_per_team"));
            Shared.DEPTH += DEPTH_DELTA;
        }

        // on affiche les résultats
        DataVisualizer.main(args);
    }
}
