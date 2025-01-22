package game.model.entities;

import java.util.ArrayList;
import java.util.List;

import game.model.platform.*;

public class Bike extends game.util.mvc.AbstractObservable {

    private Position head;
    private List<Position> streak;
    private boolean is_alive = true;

    public enum Direction {
        LEFT,
        UP,
        RIGHT,
        DOWN
    }

    public Bike(Position initial_position) {
        head = initial_position;
        streak = new ArrayList<>();
    }

    public Position getHeadPosition() {
        return head;
    }

    public List<Position> getStreakPositions() {
        return streak;
    }

    public void move(Direction dir) {

        if(!is_alive) return;

        // calcul de la nouvelle position
        int new_cord_x = head.getCordX(), new_cord_y = head.getCordY();
        switch(dir) {
            case LEFT:
                new_cord_x--;
                break;
            case UP:
                new_cord_y--;
                break;
            case RIGHT:
                new_cord_x++;
                break;
            case DOWN:
                new_cord_y++;
                break;
            default:
                break;
        }

        Platform platform = Platform.getInstance();

        // si la nouvelle position est valide alors on effectue le mouvement
        Position new_position = Position.from(new_cord_x, new_cord_y);
        if(platform.isPositionValid(new_position)) {
            streak.add(head);
            head = new_position;
            platform.addVisitedPosition(new_position);
            fireChangements();
            return;
        }
        is_alive = false;
    }

    public boolean isAlive() {
        return is_alive;
    }
}
