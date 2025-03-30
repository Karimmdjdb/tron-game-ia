package game.algo;

import java.util.List;
import game.model.platform.Direction;
import game.model.platform.Platform;

public class SOS {

    /**
     * Appel initial.
     * @param platform    la plateforme de jeu
     * @param depth       profondeur de recherche
     * @param me_id       id du joueur qui choisit son coup.
     * @return            meilleure direction
     */
    public static Direction search(Platform platform, int depth, int me_id) {
        List<Integer> team = platform.getTeamAIds();
        if(!team.contains(me_id)) team = platform.getTeamBIds();
        Integer res = search(
            new GameState(platform),
            depth,
            platform.getPlayerOrder(),
            platform.getPlayerOrder().indexOf(me_id),
            me_id,
            team,
            Integer.MIN_VALUE,
            Integer.MAX_VALUE,
            true
        );
        if(res == null || res >= 4) return Direction.DOWN;
        return Direction.values()[res];
    }

    /**
     * Algorithme SOS avec élagage alpha‑beta.
     * Pour les membres de l'équipe, on maximise une évaluation composite (score individuel + synergie);
     * pour les adversaires, on minimise ce score.
     *
     * @param state        l'état courant
     * @param depth        profondeur restante
     * @param player_order  liste ordonnée des identifiants des joueurs
     * @param current_index indice du joueur courant
     * @param me           identifiant du joueur dont on optimise le score
     * @param team         liste des identifiants des coéquipiers
     * @param alpha        borne alpha
     * @param beta         borne beta
     * @return             score composite évalué
     */
    public static Integer search(GameState state, int depth, List<Integer> player_order, int current_index, int me, List<Integer> team, int alpha, int beta, boolean is_first_call) {
        int current_player = player_order.get(current_index);
        if (depth == 0 || state.isTerminal()) {
            return evaluateSOS(state, me, team);
        }
        // Si currentPlayer est dans notre équipe, on maximise
        if (team.contains(current_player)) {
            int best_score = Integer.MIN_VALUE;
            List<Direction> moves = state.legalMoves(current_player);
            if (moves.isEmpty()) {
                return evaluateSOS(state, me, team);
            }
            Integer best_dir = null;
            for (Direction move : moves) {
                state.simulateMove(current_player, move);
                int score = search(state, depth - 1, player_order, (current_index + 1) % player_order.size(), me, team, alpha, beta, false);
                state.goBack(current_player);
                best_score = Math.max(best_score, score);
                if(score == best_score) best_dir = move.ordinal();
                alpha = Math.max(alpha, best_score);
                if (beta <= alpha) {
                    break;
                }
            }
            if(current_player == me && is_first_call) return best_dir;
            return best_score;
        } else { // Pour les adversaires, on minimise le score composite
            int worst_score = Integer.MAX_VALUE;
            List<Direction> moves = state.legalMoves(current_player);
            if (moves.isEmpty()) {
                return evaluateSOS(state, me, team);
            }
            for (Direction move : moves) {
                state.simulateMove(current_player, move);
                int score = search(state, depth - 1, player_order, (current_index + 1) % player_order.size(), me, team, alpha, beta, false);
                state.goBack(current_player);
                worst_score = Math.min(worst_score, score);
                beta = Math.min(beta, worst_score);
                if (beta <= alpha) {
                    break;
                }
            }
            return worst_score;
        }
    }

    /**
     * Évaluation composite pour SOS.
     * Combine le score individuel du joueur "me" et une partie pondérée de la somme des scores de l'équipe.
     */
    public static int evaluateSOS(GameState state, int me, List<Integer> team) {
        int individual_score = state.evaluate().getOrDefault(me, 0);
        int team_synergy = 0;
        for (int teammate : team) {
            team_synergy += state.evaluate().getOrDefault(teammate, 0);
        }
        double weight = 0.5;
        return (int)(individual_score + weight * team_synergy);
    }
}
