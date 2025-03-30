package game.algo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import game.model.platform.Direction;
import game.model.platform.Platform;

public class MaxN {
    /**
     * Appel initial.
     * @param platform    la plateforme de jeu
     * @param depth       profondeur de recherche
     * @param me_id       id du joueur qui choisit son coup.
     * @return            meilleure direction
     */
    public static Direction search(Platform platform, int depth, int me_id) {
        Map<Integer, Integer> res = search(
                new GameState(platform),
                depth,
                platform.getPlayerOrder(),
                platform.getPlayerOrder().indexOf(me_id),
                new HashMap<>(),
                true
        );
        if(res == null || res.get(0) == null) return Direction.DOWN;
        return Direction.values()[res.get(0)];
    }
    /**
     * Recherche MAXN avec élagage alpha‑beta.
     * @param state         l'état courant
     * @param depth         profondeur restante
     * @param player_order  liste ordonnée des identifiants des joueurs
     * @param current_index indice du joueur courant dans playerOrder
     * @param alpha         borne alpha (Map : player_id -> valeur)
     * @return              vecteur de scores (Map : player_id -> score)
     */
    public static Map<Integer, Integer> search(GameState state, int depth, List<Integer> player_order, int current_index, Map<Integer, Integer> alpha, boolean is_first_call) {
        int current_player = player_order.get(current_index);
        if (depth == 0 || state.isTerminal()) {
            return state.evaluate();
        }
        // Initialiser bestVector avec des valeurs très faibles
        Map<Integer, Integer> best_vector = new HashMap<>();
        for (Integer p : player_order) {
            best_vector.put(p, Integer.MIN_VALUE);
        }
        List<Direction> moves = state.legalMoves(current_player);
        if (moves.isEmpty()) {
            return state.evaluate();
        }
        Map<Integer, Integer> best_dir = null;
        for (Direction move : moves) {
            state.simulateMove(current_player, move);
            // Copie de alpha pour la branche enfant
            Map<Integer, Integer> child_alpha = new HashMap<>(alpha);
            int next_index = (current_index + 1) % player_order.size();
            Map<Integer, Integer> vector = search(state, depth - 1, player_order, next_index, child_alpha, false);
            state.goBack(current_player);
            // Mise à jour du vecteur bestVector pour le joueur courant
            if (vector.get(current_player) > best_vector.get(current_player)) {
                best_vector = vector;
                best_dir = Map.of(0, move.ordinal());
            }
            // Mise à jour de la borne alpha pour le joueur courant
            int current_alpha = alpha.getOrDefault(current_player, Integer.MIN_VALUE);
            if (best_vector.get(current_player) > current_alpha) {
                alpha.put(current_player, best_vector.get(current_player));
            }
            // Vous pouvez insérer ici une condition de coupe adaptée pour MAXN
        }

        if(is_first_call) return best_dir;
        return best_vector;
    }
}
