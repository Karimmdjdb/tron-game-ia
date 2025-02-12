package game.algo;

import java.util.Map;

import game.model.entities.Bike;
import game.model.platform.Direction;
import game.model.platform.Platform;

public class Paranoid {

    public static Direction simulate(Platform platform, int depth, int player_id) {
        return (Direction)paranoid(new GameState(platform), depth, player_id, true);
    }
    @SuppressWarnings("unchecked")
    public static Object paranoid(GameState game_state, int depth, int player_id, boolean is_first_call) {
         // si c'est un noeud on retourne l'évaluation de l'état de jeu
        if(depth == 0 || game_state.isTerminal()) {
            return game_state.evaluate();
        }

        // on calcule l'id du prochain joueur
        int next_player_id = player_id % Bike.getPlayersNumber() + 1;

        // si le joueur est éliminé il n'influence pas la suite du calcul
        if(!game_state.canPlayerMove(player_id)) {
            return (Map<Integer, Integer>)paranoid(game_state, depth - 1, next_player_id, false);
        }

        int best_score = Integer.MIN_VALUE;
        Direction best_move = null;

        for(Direction direction : Direction.values()) {
            game_state.simulateMove(player_id, direction);
            int worst_case_score = Integer.MAX_VALUE;
            for(int opponent_id = 1; opponent_id <= Bike.getPlayersNumber(); opponent_id++) {
                if(player_id == opponent_id) continue;
                int oppenent_best_score = 0;
                worst_case_score = Math.min(worst_case_score, oppenent_best_score);
            }

            int score = game_state.evaluate().get(player_id) - worst_case_score;
            if(score > best_score) {
                best_score = score;
                best_move = direction;
            }

            game_state.goBack(player_id);
        }

        if(is_first_call) return best_move;
        
        return best_score;
    }

    public static int opponentParanoid(GameState game_state, int opponent_id) {
        int best_score = Integer.MIN_VALUE;
        for(Direction direction : Direction.values()) {
            game_state.simulateMove(opponent_id, direction);
            best_score = Math.max(best_score, game_state.evaluate().get(opponent_id));
            game_state.goBack(opponent_id);
        }
        return best_score;
    }
}
