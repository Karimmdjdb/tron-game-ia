package game.algo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import game.model.entities.Bike;
import game.model.platform.Direction;
import game.model.platform.Platform;
import game.model.platform.Position;

public class GameState {

    private static final int SIZE = Platform.SIZE;

    private Map<Integer, Position> players;
    private Stack<Position> visited;

    public GameState(Platform platform) {
        players = new HashMap<>();
        visited = new Stack<>();

        for(Bike bike : platform.getBikes()) {
            players.put(bike.getId(), bike.getHeadPosition());
            visited.addAll(bike.getStreakPositions());
        }
    }

    public void simulateMove(int player_id, Direction direction) {
        Position old_position = players.get(player_id);
        int x_offset = 0, y_offset = 0;
        switch(direction) {
            case LEFT:
                x_offset = -1; y_offset = 0;
                break;
            case UP:
                x_offset = 0; y_offset = -1;
                break;
            case RIGHT:
                x_offset = 1; y_offset = 0;
                break;
            case DOWN:
                x_offset = 0; y_offset = 1;
                break;
            default:
                break;
        }
        Position new_position = Position.from(old_position.getCordX() + x_offset, old_position.getCordY() + y_offset);
        visited.push(old_position);
        players.put(player_id, new_position);
    }

    public void goBack(int player_id) {
        players.put(player_id, visited.pop());
    }

    public boolean isTerminal() {
        for(Position player_position : players.values()) {
            if(!visited.contains(player_position)) return false;
        }
        return true;
    }

    public boolean canPlayerMove(int player_id) {
        return !(visited.contains(players.get(player_id)) || isPlayerOut(player_id));
    }

    public boolean isPlayerOut(int player_id) {
        Position position = players.get(player_id);
        return position.getCordX() < 0 || position.getCordY() < 0 || position.getCordX() >= SIZE || position.getCordY() >= SIZE;
    }

    public Map<Integer, Integer> evaluate() {
        Map<Integer, Integer> scores_vector = new HashMap<>();
        for(Map.Entry<Integer, Position> player : players.entrySet()) {
            int id = player.getKey();
            Position position = player.getValue();
            int score = 0;
            if(visited.contains(position) || isPlayerOut(id)) score = -1;
            else {
                int s1 = evaluateDistanceToWall(id);
                // System.out.print(s1 + " | ");
                score += s1;
                int s2 = evaluateLiberty(id);
                score += s2;
                // System.out.print(s2 + " | ");
                int s3 = evaluateDistanceWithOthers(id);
                // System.out.println(s3);
                score += s3;
            }
            scores_vector.put(id, score);
        }
        return scores_vector;
    }

    public int evaluateDistanceToWall(int player_id) {
        Position position = players.get(player_id);
        int x = position.getCordX(), y = position.getCordY(), dist = Integer.MAX_VALUE;
        dist = Math.min(dist, x);
        dist = Math.min(dist, y);
        dist = Math.min(dist, SIZE-x);
        dist = Math.min(dist, SIZE-y);
        return dist;
    }

    public int evaluateLiberty(int player_id) {
        Set<Position> visited = new HashSet<>();
        Queue<Position> queue = new LinkedList<>();
        queue.add(players.get(player_id));
        visited.add(players.get(player_id));

        int depth = 5;
        int area = 0;

        while (!queue.isEmpty()) {
            Position current = queue.poll();
            area++;

            for (Direction dir : Direction.values()) {
                Position neighbor = getNextPosition(current, dir);

                // Vérifier si la position est valide et non occupée
                if (isValidPosition(neighbor) && !visited.contains(neighbor)) {
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
               pos.getCordY() >= 0 && pos.getCordY() < SIZE && !visited.contains(pos);
    }

    private int evaluateDistanceWithOthers(int player_id) {
        int min_dist = Integer.MAX_VALUE;
        Position p1 = players.get(player_id);
        for(Map.Entry<Integer, Position> player2 : players.entrySet()) {
           if(player_id == player2.getKey()) continue;
           Position p2 = player2.getValue();
           int dist = (int)Math.sqrt(Math.pow(p2.getCordX()-p1.getCordX(), 2)+Math.pow(p2.getCordY()-p1.getCordY(), 2));
           min_dist = Math.min(min_dist, dist);
        }
        return min_dist;
    }
}
