package game.controller;

import game.model.entities.Bike;
import game.model.entities.Bot;
import game.model.platform.Platform;

public class Controller {

    private Platform model;

    public Controller(Platform model) {
        this.model = model;
    }

    public void update() {
        if(!model.isGameOver()) {

            // début du timer
            long debut = System.nanoTime();

            // on fait jouer les bots
            for(Bike bike : model.getBikes()) {
                Bot bot = (Bot)bike;
                if(!bot.isAlive()) continue;
                bot.play();
            }

            // fin du timer
            long fin = System.nanoTime();
            // System.out.println("Temps de calcul du tour : " + (fin - debut) / 1_000_000 + " ms");

            // vérification des collisions des joueurs
            model.checkCollisions();

            if(model.isGameOver()) {
                System.out.println("END GAME.");
            }
        }
    }
}
