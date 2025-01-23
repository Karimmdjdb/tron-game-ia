package game.model.platform;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import game.model.entities.Bike;

public class Platform extends game.util.mvc.AbstractObservable implements game.util.mvc.Observer {

    public final static Random random = new Random();
    public final static int SIZE = 100;

    private static Platform singleton;

    private List<Bike> bikes;
    private Set<Position> visited;

    private Platform() {
        bikes = new ArrayList<>();
        visited = new HashSet<>();
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

    public Set<Position> getVisitedPositions() {
        return visited;
    }

    public boolean isPositionValid(Position position) {
        // si le joueur sort de la plateforme
        if(position.getCordX() < 0 || position.getCordY() < 0 || position.getCordX() >= SIZE || position.getCordY() >= SIZE) return false;
        // si le joueur passe par une case déja visitée
        if(visited.contains(position)) return false;
        return true;
    }

    public void addVisitedPosition(Position position) {
        visited.add(position);
    }

    public Position getRandomFreePosition() {
        return Position.from(random.nextInt(SIZE), random.nextInt(SIZE));
    }
}