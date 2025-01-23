package game.controller;

import java.util.Random;

import game.algo.MinMax;
import game.model.entities.Bike;
import game.model.platform.Platform;

public class Controller {

    private Platform model;
    private Bike.Direction[] dirs = new Bike.Direction[] {Bike.Direction.LEFT, Bike.Direction.UP, Bike.Direction.RIGHT, Bike.Direction.DOWN};
    private Random rand = new Random();

    public Controller(Platform model) {
        this.model = model;
    }

    public void update() {
        // for(Bike bike : model.getBikes()) {
            // bike.move(dirs[1]);
        // }
        Bike b1 = model.getBikes().get(0);
        Bike b2 = model.getBikes().get(1);
        b1.move(MinMax.minmax(model, 10, true));
        b2.move(MinMax.minmax(model, 10, false));
    }
}
