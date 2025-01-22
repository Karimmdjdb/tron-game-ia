package game.controller;

import java.util.Random;

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
        for(Bike bike : model.getBikes()) {
            bike.move(dirs[rand.nextInt(4)]);
        }
    }
}
