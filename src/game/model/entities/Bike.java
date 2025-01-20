package game.model.entities;

import game.model.platform.*;

public class Bike extends Entity {

    public enum Direction {
        LEFT,
        UP,
        RIGHT,
        DOWN
    }

    public Bike() {
        super();
    }

    public Bike(int cord_x, int cord_y) {
        super(cord_x, cord_y);
    }

    public Bike(Position position) {
        super(position);
    }

    public void move(Direction dir) {
        int new_cord_x = cord_x, new_cord_y = cord_y;
        switch(dir) {
            case LEFT:
                new_cord_x++;
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
        Position new_position = Position.from(new_cord_x, new_cord_y);
        if(platform.isPositionValid(new_position)) {
            Position old_position = Position.from(cord_x, cord_y);
            platform.moveEntity(old_position, new_position);
            cord_x = new_cord_x;
            cord_y = new_cord_y;
        }
    }
}
