package game.controller;

import java.util.Random;

import game.algo.MinMax;
import game.exec.Game;
import game.model.entities.Bike;
import game.model.platform.Platform;

public class Controller {

    private static final int DEPTH = 10;

    private Platform model;
    private Bike.Direction[] dirs = new Bike.Direction[] {
        Bike.Direction.LEFT,
        Bike.Direction.UP,
        Bike.Direction.RIGHT,
        Bike.Direction.DOWN
    };

    private Random rand = new Random();

    public Controller(Platform model) {
        this.model = model;
    }

    public void update() {
        if(!model.isGameOver()) {
        // for(Bike bike : model.getBikes()) {
            // bike.move(dirs[1]);
        // }
        Bike b1 = model.getTeamA().getMembers().get(0);
        Bike b2 = model.getTeamB().getMembers().get(0);
        long debut = System.nanoTime();
        // Appel de la fonction
        if(b1.isAlive()) b1.move(MinMax.minmax(model, DEPTH, true));
        if(b2.isAlive()) b2.move(MinMax.minmax(model, DEPTH, false));
        long fin = System.nanoTime();
        System.out.println("Temps d'exécution : " + (fin - debut) / 1_000_000 + " ms");
        model.checkCollisions();
        if(model.isGameOver()) {
            System.out.println("END GAME.");
        }}
    }
}
