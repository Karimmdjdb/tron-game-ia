package game.model.entities;

import game.model.platform.Position;

public class Entity extends util.mvc.AbstractObservable {

    protected int cord_x, cord_y;

    public Entity() {

    }

    public Entity(int cord_x, int cord_y) {
        this.cord_x = cord_x;
        this.cord_y = cord_y;
    }

    public Entity(Position position) {
        this.cord_x = position.getCordX();
        this.cord_y = position.getCordY();
    }

    public int getCordX() {
        return cord_x;
    }

    public int getCordY() {
        return cord_y;
    }

    public void setCordX(int cord_x) {
        this.cord_x = cord_x;
    }

    public void setCordY(int cord_y) {
        this.cord_y = cord_y;
    }


}