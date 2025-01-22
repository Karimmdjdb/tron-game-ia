package game.model.platform;

public class Position {
    private int cord_x, cord_y;

    private Position(int cord_x, int cord_y) {
        this.cord_x = cord_x;
        this.cord_y = cord_y;
    }

    public static Position from(int cord_x, int cord_y) {
        return new Position(cord_x, cord_y);
    }

    public int getCordX() {
        return cord_x;
    }

    public int getCordY() {
        return cord_y;
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof Position) {
            Position other_position = (Position) other;
            return other_position.cord_x == this.cord_x && other_position.cord_y == this.cord_y;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return cord_x + cord_y;
    }

    @Override
    public String toString() {
        return String.format("(%d , %d)", cord_x, cord_y);
    }
}
