package game.algo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import game.model.entities.Bike;
import game.model.platform.Direction;
import game.model.platform.Platform;

public class MaxN {

    public static Direction simulate(Platform platform, int depth, int player_id) {

        return (Direction)maxn(new GameState(platform), depth, player_id, true);
    }

    @SuppressWarnings("unchecked")
    private static Object maxn(GameState game_state, int depth, int player_id, boolean is_first_call) {

        // si c'est un noeud on retourne l'évaluation de l'état de jeu
        if(depth == 0 || game_state.isTerminal()) {
            return game_state.evaluate();
        }

        // on calcule l'id du prochain joueur
        int next_player_id = player_id % Bike.getPlayersNumber() + 1;

        // si le joueur est éliminé il n'influence pas la suite du calcul
        if(!game_state.canPlayerMove(player_id)) {
            return (Map<Integer, Integer>)maxn(game_state, depth - 1, next_player_id, false);
        }

        Map<Integer, Integer> best_vector = null;
        Direction best_move = null;

        final List<Map<Integer, Integer>> TEST = new ArrayList<>();
        for(Direction direction : Direction.values()) {
            game_state.simulateMove(player_id, direction);
            Map<Integer, Integer> eval_vector = (Map<Integer, Integer>)maxn(game_state, depth - 1, next_player_id, false);
            TEST.add(eval_vector);
            game_state.goBack(player_id);

            if(best_vector == null || eval_vector.get(player_id) > best_vector.get(player_id)) {
                best_vector = eval_vector;
                best_move = direction;
            }
        }

        if(is_first_call) return best_move;

        return best_vector;

    }
}
