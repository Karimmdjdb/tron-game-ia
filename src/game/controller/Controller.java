package game.controller;


import game.model.entities.Bike;
import game.model.platform.Platform;
import game.algo.MinMax;

public class Controller {

    private static final int DEPTH = 10;

    private Platform model;

    public Controller(Platform model) {
        this.model = model;
    }

    public void update() {
        if(!model.isGameOver()) {

            Bike b1 = model.getTeamA().getMembers().get(0);
            Bike b2 = model.getTeamB().getMembers().get(0);

            long debut = System.nanoTime();

            // minmax
            if(b1.isAlive()) b1.move(MinMax.minmax(model, DEPTH, true));
            if(b2.isAlive()) b2.move(MinMax.minmax(model, DEPTH, false));

            long fin = System.nanoTime();

            System.out.println("Temps de calcul MinMax : " + (fin - debut) / 1_000_000 + " ms");

            model.checkCollisions();

            if(model.isGameOver()) {
                System.out.println("END GAME.");
            }
        }
    }
}
