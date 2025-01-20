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
}
