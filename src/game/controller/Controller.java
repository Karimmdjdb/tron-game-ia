package game.controller;


import game.model.entities.Bike;
import game.model.platform.Platform;
import game.algo.MaxN;
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
            if(b1.isAlive()) b1.move(MaxN.simulate(model, DEPTH, b1.getId()));
            // System.out.println(b1.getHeadPosition());
            if(b2.isAlive()) b2.move(MaxN.simulate(model, DEPTH, b2.getId()));

            long fin = System.nanoTime();

            System.out.println("Temps de calcul MinMax : " + (fin - debut) / 1_000_000 + " ms");

            model.checkCollisions();

            if(model.isGameOver()) {
                System.out.println("END GAME.");
            }
        }
    }
}
