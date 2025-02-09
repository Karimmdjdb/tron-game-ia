package game.exec;

import game.controller.Controller;
import game.model.entities.Bike;
import game.model.platform.Platform;
import game.model.platform.Position;

public class Test {
    public static void main(String[] args) {
        Bike b1 = new Bike(Position.from(0, 0));
        Bike b2 = new Bike(Position.from(0, 0));
        Bike b3 = new Bike(Position.from(0, 0));
        Bike b4 = new Bike(Position.from(0, 0));
        Bike b5 = new Bike(Position.from(0, 0));
        Bike b6 = new Bike(Position.from(0, 0));

        int t = 3;

        for(int i = 0; i<20; i++) {
            System.out.println(t);
            t = t%Bike.getPlayersNumber() + 1;
        }
    }

    public static void printPos(Position p) {
        System.out.println(String.format("(%d, %d)", p.getCordX(), p.getCordY()));
    }
}
