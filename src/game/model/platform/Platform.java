package game.model.platform;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import game.model.entities.*;

public class Platform extends util.mvc.AbstractObservable implements util.mvc.Observer {
    public final static Random random = new Random();
    public final static int SIZE = 10;
    private static Platform singleton;
    private Entity[][] grid;
    private int size;

    private Platform() {
        grid = new Entity[SIZE][SIZE];
    }

    public static Platform getInstance() {
        if(singleton == null) {
            singleton = new Platform();
        }
        return singleton;
    }

    @Override
    public void update(util.mvc.Observable source) {
        fireChangements();
    }

    public boolean isPositionValid(Position position) {
        if(position.getCordX() < 0 || position.getCordY() < 0 || position.getCordX() >= size || position.getCordY() >= size) return false;
        return true;
    }

    public void moveEntity(Position source, Position destination) {
        grid[destination.getCordY()][destination.getCordX()] = grid[source.getCordY()][source.getCordX()];
        grid[source.getCordY()][source.getCordX()] = null;
    }

    public void removeEntity(Position position) {
        grid[position.getCordY()][position.getCordX()] = null;
    }

    public void setEntity(Entity entity) {
        grid[entity.getCordY()][entity.getCordX()] = entity;
    }

    public Position getRandomFreePosition() {
        List<Position> all_free_positions = new ArrayList<>();
        for(int x = 0; x < SIZE; x++)
        for(int y = 0; y < SIZE; y++) {
            if(grid[y][x] == null)
            all_free_positions.add(Position.from(x, y));
        }
        int index = random.nextInt(all_free_positions.size());
        return all_free_positions.get(index);
    }
}