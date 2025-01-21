package game.model.platform;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import game.model.entities.Bike;

public class Platform extends game.util.mvc.AbstractObservable implements game.util.mvc.Observer {
    public final static Random random = new Random();
    public final static int SIZE = 100;
    private static Platform singleton;
    private int size;
    private List<Bike> bikes;

    private Platform() {
        bikes = new ArrayList<>();
    }

    public static Platform getInstance() {
        if(singleton == null) {
            singleton = new Platform();
        }
        return singleton;
    }

    @Override
    public void update(game.util.mvc.Observable source) {
        fireChangements();
    }

    public List<Bike> getBikes() {
        return bikes;
    }

    public void addBike(Bike bike) {
        bikes.add(bike);
        bike.addObserver(this);
    }

    public boolean isPositionValid(Position position) {
        if(position.getCordX() < 0 || position.getCordY() < 0 || position.getCordX() >= size || position.getCordY() >= size) return false;
        return true;
    }

    public Position getRandomFreePosition() {
        return Position.from(random.nextInt(SIZE), random.nextInt(SIZE));
    }
}