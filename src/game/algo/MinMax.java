package game.algo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import game.model.entities.Bike;
import game.model.platform.Direction;
import game.model.platform.Platform;
import game.model.platform.Position;

public class MinMax {

    private static List<Direction> possibles_moves;
    static {
        possibles_moves = List.of(
            Direction.LEFT,
            Direction.UP,
            Direction.RIGHT,
            Direction.DOWN
        );
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

        public void simulateMove(Direction dir, boolean maximizing_player) {
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
            if(p1_head.getCordX() == p2_head.getCordX() && p1_head.getCordY() == p2_head.getCordY()) return true;
            return false;
        }

        public int evaluate() {
            int eval = 0;
            if(p1_head.equals(p2_head)) eval = Integer.MIN_VALUE;
            // Vérification des états terminaux
            if (p1_streak.contains(p1_head) || p2_streak.contains(p1_head) ||
            p1_head.getCordX() < 0 || p1_head.getCordX() >= SIZE ||
            p1_head.getCordY() < 0 || p1_head.getCordY() >= SIZE) {
                eval = (Integer.MIN_VALUE/2); // Défaite du joueur maximisant
            }
            if (p1_streak.contains(p2_head) || p2_streak.contains(p2_head) ||
                p2_head.getCordX() < 0 || p2_head.getCordX() >= SIZE ||
                p2_head.getCordY() < 0 || p2_head.getCordY() >= SIZE) {
                eval =  (Integer.MAX_VALUE/2); // Défaite du joueur minimisant (victoire de p1)
            }

            if (!p1_streak.contains(p1_head) && !p2_streak.contains(p1_head) &&
            p1_head.getCordX() >= 0 && p1_head.getCordX() < SIZE &&
            p1_head.getCordY() >= 0 && p1_head.getCordY() < SIZE) {
                eval += 1000; // Défaite du joueur maximisant
            }

            if (!p1_streak.contains(p2_head) && !p2_streak.contains(p2_head) &&
            p2_head.getCordX() >= 0 && p2_head.getCordX() < SIZE &&
            p2_head.getCordY() >= 0 && p2_head.getCordY() < SIZE) {
                eval -= 1000; // Défaite du joueur maximisant
            }

            int p1_controlled_area = bfsControlledArea(p1_head, p1_streak, p2_streak);
            int p2_controlled_area = bfsControlledArea(p2_head, p2_streak, p1_streak);

            return eval + (p1_controlled_area - p2_controlled_area);
        }

        private int bfsControlledArea(Position start, List<Position> ownTrail, List<Position> opponentTrail) {
            Set<Position> visited = new HashSet<>();
            Queue<Position> queue = new LinkedList<>();
            queue.add(start);
            visited.add(start);

            int depth = 3;
            int area = 0;

            while (!queue.isEmpty()) {
                Position current = queue.poll();
                area++;

                for (Direction dir : Direction.values()) {
                    Position neighbor = getNextPosition(current, dir);

                    // Vérifier si la position est valide et non occupée
                    if (isValidPosition(neighbor) && !visited.contains(neighbor)
                        && !ownTrail.contains(neighbor) && !opponentTrail.contains(neighbor)) {
                        visited.add(neighbor);
                        queue.add(neighbor);
                    }
                }
                depth--;
                if(depth==0) break;
            }
            return area;
        }

        private Position getNextPosition(Position pos, Direction dir) {
            switch (dir) {
                case LEFT: return Position.from(pos.getCordX() - 1, pos.getCordY());
                case UP: return Position.from(pos.getCordX(), pos.getCordY() - 1);
                case RIGHT: return Position.from(pos.getCordX() + 1, pos.getCordY());
                case DOWN: return Position.from(pos.getCordX(), pos.getCordY() + 1);
                default: return pos;
            }
        }

        private boolean isValidPosition(Position pos) {
            return pos.getCordX() >= 0 && pos.getCordX() < SIZE &&
                   pos.getCordY() >= 0 && pos.getCordY() < SIZE;
        }
    }

    private static final int SIZE = Platform.SIZE;

    public static Direction minmax(Platform platform, int depth, boolean is_maximising) {
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
        Direction best_direction;
        switch (best) {
            case 0:
                best_direction = Direction.LEFT;
                break;
            case 1:
                best_direction = Direction.UP;
                break;
            case 2:
                best_direction = Direction.RIGHT;
                break;
            case 3:
                best_direction = Direction.DOWN;
                break;
            default:
                best_direction = Direction.DOWN;
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
