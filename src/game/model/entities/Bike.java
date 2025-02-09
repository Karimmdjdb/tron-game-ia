package game.model.entities;

import java.util.ArrayList;
import java.util.List;

import game.model.platform.*;

public class Bike extends game.util.mvc.AbstractObservable {
    private static int total_bikes_number = 0;
    private Position head;
    private List<Position> streak;
    private boolean is_alive = true;
    private final int id;

    public enum Direction {
        LEFT,
        UP,
        RIGHT,
        DOWN
    }

    public Bike(Position initial_position) {
        head = initial_position;
        streak = new ArrayList<>();
        id = ++total_bikes_number;
    }

    public Position getHeadPosition() {
        return head;
    }

    public List<Position> getStreakPositions() {
        return streak;
    }

    public void kill() {
        is_alive = false;
    }

    public int getId() {
        return id;
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
        streak.add(head);
        platform.addVisitedPosition(head);
        head = new_position;
        fireChangements();
    }

    public boolean isAlive() {
        return is_alive;
    }

    public static int getPlayersNumber() {
        return total_bikes_number;
    }
}
