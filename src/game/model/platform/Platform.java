package game.model.platform;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import game.model.entities.Bike;
import game.util.config.ConfigReader;

public class Platform extends game.util.mvc.AbstractObservable implements game.util.mvc.Observer {

    public final static Random random;
    public final static int SIZE;

    static {
        random = new Random();
        SIZE = Integer.parseInt(ConfigReader.get("platform_size"));
    }

    private static Platform singleton;

    private List<Bike> bikes;
    private Set<Position> visited;
    private Team team_A, team_B;
    private Set<Bike> alive_bikes;
    private List<Integer> playerOrder;

    private Platform() {
        bikes = new ArrayList<>();
        visited = new HashSet<>();
        alive_bikes = new HashSet<>();
    }

    public static Platform getInstance() {
        if(singleton == null) {
            singleton = new Platform();
        }
        return singleton;
    }

    @Override
    public void update(game.util.mvc.Observable source) {
        // fireChangements();
    }

    public List<Bike> getBikes() {
        return bikes;
    }

    public void addBike(Bike bike) {
        bikes.add(bike);
        alive_bikes.add(bike);
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

    public void checkCollisions() {
        // si un joueur touche un mur
        for(Bike b : bikes) {
            if(!isPositionValid(b.getHeadPosition())){
                b.kill();
                alive_bikes.remove(b);
            }
        }

        // si deux joueurs se rentrent dedans
        for(Bike b1 : bikes)
        for(Bike b2 : bikes) {
            if(b1 == b2) continue;
            if(b1.getHeadPosition().equals(b2.getHeadPosition())) {
                b1.kill();
                alive_bikes.remove(b1);
                b2.kill();
                alive_bikes.remove(b2);
            }
        }
        fireChangements();
    }

    public void addVisitedPosition(Position position) {
        visited.add(position);
    }

    public Position getRandomFreePosition() {
        return Position.from(random.nextInt(SIZE), random.nextInt(SIZE));
    }

    public void setTeamA(Team team) {
        bikes.addAll(team.getMembers());
        alive_bikes.addAll(team.getMembers());
        for(Bike b : team.getMembers()) {
            b.addObserver(this);
        }
        team_A = team;
    }

    public void setTeamB(Team team) {
        bikes.addAll(team.getMembers());
        alive_bikes.addAll(team.getMembers());
        for(Bike b : team.getMembers()) {
            b.addObserver(this);
        }
        team_B = team;
    }

    public Team getTeamA() {
        return team_A;
    }

    public Team getTeamB() {
        return team_B;
    }

    public boolean isGameOver() {
        return alive_bikes.size() <= 1;
    }

    public void setPlayerOrder() {
        playerOrder = new ArrayList<>();
        for(Bike b : bikes) playerOrder.add(b.getId());
        Collections.shuffle(playerOrder);
    }

    public List<Integer> getPlayerOrder() {
        return playerOrder;
    }

    public List<Integer> getTeamAIds() {
        List<Integer> ids = new ArrayList<>();
        for(Bike b : team_A.getMembers()) {
            ids.add(b.getId());
        }
        return ids;
    }

    public List<Integer> getTeamBIds() {
        List<Integer> ids = new ArrayList<>();
        for(Bike b : team_B.getMembers()) {
            ids.add(b.getId());
        }
        return ids;
    }
}