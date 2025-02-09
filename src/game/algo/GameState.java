package game.algo;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import game.model.entities.Bike;
import game.model.entities.Bike.Direction;
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
            int score = !visited.contains(position) && !isPlayerOut(id) ? 1 : 0;
            scores_vector.put(id, score);
        }
        return scores_vector;
    }
}
