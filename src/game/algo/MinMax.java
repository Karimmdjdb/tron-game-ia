package game.algo;
import java.time.format.SignStyle;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import game.model.entities.Bike;
import game.model.entities.Bike.Direction;
import game.model.platform.Platform;
import game.model.platform.Position;

public class MinMax {

    private static Set<Direction> possibles_moves;
    static {
        possibles_moves = new HashSet<>();
        possibles_moves.add(Direction.LEFT);
        possibles_moves.add(Direction.UP);
        possibles_moves.add(Direction.RIGHT);
        possibles_moves.add(Direction.DOWN);
    }

    public static class GameState {
        private Position p1_head;
        private Position p2_head;
        private List<Position> p1_streak;
        private List<Position> p2_streak;

        private GameState(Position p1_head, Position p2_head, List<Position> p1_streak, List<Position> p2_streak) {
            this.p1_head = p1_head;
            this.p2_head = p2_head;
            this.p1_streak = new ArrayList<>(p1_streak);
            this.p2_streak = new ArrayList<>(p2_streak);
        }

        public void simulateMove(Bike.Direction dir, boolean maximizing_player) {
            if(maximizing_player) {
                p1_streak.add(p1_head);
                switch (dir) {
                    case LEFT:
                        p1_head = Position.from(p1_head.getCordX()-1, p1_head.getCordY());
                        break;
                    case UP:
                        p1_head = Position.from(p1_head.getCordX(), p1_head.getCordY()-1);
                        break;
                    case RIGHT:
                        p1_head = Position.from(p1_head.getCordX()+1, p1_head.getCordY());
                        break;
                    case DOWN:
                        p1_head = Position.from(p1_head.getCordX(), p1_head.getCordY()+1);
                        break;
                    default:
                        break;
                }
            } else {
                p2_streak.add(p2_head);
                switch (dir) {
                    case LEFT:
                        p2_head = Position.from(p2_head.getCordX()-1, p2_head.getCordY());
                        break;
                    case UP:
                        p2_head = Position.from(p2_head.getCordX(), p2_head.getCordY()-1);
                        break;
                    case RIGHT:
                        p2_head = Position.from(p2_head.getCordX()+1, p2_head.getCordY());
                        break;
                    case DOWN:
                        p2_head = Position.from(p2_head.getCordX(), p2_head.getCordY()+1);
                        break;
                    default:
                        break;
                }
            }
        }

        public void goBack(boolean maximizing_player) {
            if(maximizing_player) {
                p1_head = p1_streak.remove(p1_streak.size()-1);
            } else {
                p2_head = p2_streak.remove(p2_streak.size()-1);
            }
        }

        public boolean isTerminal() {
            if(
                p1_streak.contains(p1_head) ||
                p2_streak.contains(p1_head) ||
                p1_streak.contains(p2_head) ||
                p2_streak.contains(p2_head)) return true;
            if(
                p1_head.getCordX() < 0 ||
                p1_head.getCordX() >= SIZE ||
                p1_head.getCordY() < 0 ||
                p1_head.getCordY() > SIZE) return true;
            if(
                p2_head.getCordX() < 0 ||
                p2_head.getCordX() >= SIZE ||
                p2_head.getCordY() < 0 ||
                p2_head.getCordY() > SIZE) return true;
            return false;
        }

        public int evaluate() {
            // Vérification des états terminaux
    if (p1_streak.contains(p1_head) || p2_streak.contains(p1_head) ||
    p1_head.getCordX() < 0 || p1_head.getCordX() >= SIZE ||
    p1_head.getCordY() < 0 || p1_head.getCordY() >= SIZE) {
    return Integer.MIN_VALUE; // Défaite du joueur maximisant
}
if (p1_streak.contains(p2_head) || p2_streak.contains(p2_head) ||
    p2_head.getCordX() < 0 || p2_head.getCordX() >= SIZE ||
    p2_head.getCordY() < 0 || p2_head.getCordY() >= SIZE) {
    return Integer.MAX_VALUE; // Défaite du joueur minimisant (victoire de p1)
}
return 0;
        }

        public int evaluateLife() {
            int eval = 0;
            if(!p1_streak.contains(p1_head) && !p2_streak.contains(p1_head)) eval += 10;
            if(!p1_streak.contains(p2_head) && !p2_streak.contains(p2_head)) eval -= 10;
            return eval;
        }

        public int evaluateDistanceToWall() {
            return 1;
        }
    }

    private static final int SIZE = Platform.SIZE;

    public static Bike.Direction minmax(Platform platform, int depth, boolean is_maximising) {
        // on récupére les deux joueurs
        Bike p1 = platform.getTeamA().getMembers().get(0);
        Bike p2 = platform.getTeamB().getMembers().get(0);

        // conversion de la plateforme en état
        GameState state = new GameState(
            p1.getHeadPosition(),
            p2.getHeadPosition(),
            p1.getStreakPositions(),
            p2.getStreakPositions()
        );

        // appel initial
        int best = minmax(state, depth, is_maximising, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
        Bike.Direction best_direction;
        switch (best) {
            case 0:
                best_direction = Bike.Direction.LEFT;
                break;
            case 1:
                best_direction = Bike.Direction.UP;
                break;
            case 2:
                best_direction = Bike.Direction.RIGHT;
                break;
            case 3:
                best_direction = Bike.Direction.DOWN;
                break;
            default:
                best_direction = Bike.Direction.DOWN;
                break;
        }
        return best_direction;
    }

    private static int minmax(GameState state, int depth, boolean is_maximizing, boolean first_call, int alpha, int beta) {

        if(depth == 0 || state.isTerminal()) {
            return state.evaluate();
        }

        int best_score;
        int best_move = -1;
        int i = 0;

        if(is_maximizing) { // si c'est le joueur max
            int max_eval = Integer.MIN_VALUE;
            for(Direction dir : possibles_moves) {
                state.simulateMove(dir, true);
                int eval = minmax(state, depth-1, false, false, alpha, beta);
                if(eval > max_eval) {
                    best_move = i;
                }
                state.goBack(true);
                max_eval = Math.max(max_eval, eval);
                alpha = Math.max(alpha, eval);
                if(alpha >= beta) break;
                i++;
            }
            best_score = max_eval;
        } else { // si c'est le joueur min
            int min_eval = Integer.MAX_VALUE;
            for(Direction dir : possibles_moves) {
                state.simulateMove(dir, false);
                int eval = minmax(state, depth-1, true, false, alpha, beta);
                if(eval < min_eval) {
                    best_move = i;
                }
                state.goBack(false);
                min_eval = Math.min(min_eval, eval);
                beta = Math.min(beta, eval);
                if(beta <= alpha) break;
                i++;
            }
            best_score = min_eval;
        }

        return first_call ? best_move : best_score;
    }
}
