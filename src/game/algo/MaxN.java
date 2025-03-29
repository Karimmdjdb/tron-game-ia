package game.algo;

import java.util.HashMap;
import java.util.Map;

import game.model.entities.Bike;
import game.model.platform.Direction;
import game.model.platform.Platform;

public class MaxN {

    public static Direction simulate(Platform platform, int depth, int player_id) {
        Map<Integer, Integer> alphas = new HashMap<>();
        for(int id=1; id <= Bike.getPlayersNumber(); id++) {
            alphas.put(id, Integer.MIN_VALUE);
        }
        return (Direction)maxn(new GameState(platform), depth, player_id, true, alphas);
    }

    @SuppressWarnings("unchecked")
    private static Object maxn(GameState game_state, int depth, int player_id, boolean is_first_call, Map<Integer, Integer> alphas_vector) {

        // si c'est un noeud on retourne l'évaluation de l'état de jeu
        if(depth == 0 || game_state.isTerminal()) {
            return game_state.evaluate();
        }

        // on calcule l'id du prochain joueur
        int next_player_id = player_id % Bike.getPlayersNumber() + 1;

        // si le joueur est éliminé il n'influence pas la suite du calcul
        if(!game_state.canPlayerMove(player_id)) {
            return (Map<Integer, Integer>)maxn(game_state, depth - 1, next_player_id, false, alphas_vector);
        }

        Map<Integer, Integer> best_vector = null;
        Direction best_move = null;
        Map<Integer, Integer> alphas_local = new HashMap<>(alphas_vector);

        for(Direction direction : Direction.values()) {
            game_state.simulateMove(player_id, direction);
            Map<Integer, Integer> eval_vector = (Map<Integer, Integer>)maxn(game_state, depth - 1, next_player_id, false, alphas_local);
            game_state.goBack(player_id);

            if(best_vector == null || eval_vector.get(player_id) > best_vector.get(player_id)) {
                best_vector = eval_vector;
                best_move = direction;

                int max_alpha = Math.max(alphas_local.get(player_id), best_vector.get(player_id));
                alphas_local.put(player_id, max_alpha);
            }

            // if(best_vector.get(player_id) <= alphas_vector.get(player_id)) {
            //     break;
            // }
        }

        if(is_first_call) return best_move;

        return best_vector;

    }
}
