package game.platform;

import game.entities.*;

public class Platform extends util.mvc.AbstractModel implements util.mvc.Observer {

    private Entity[][] grid;
    public Platform() {
        grid = new Entity[10][10];
    }

    @Override
    public void update(util.mvc.Model source) {
        fireChangements();
    }
}