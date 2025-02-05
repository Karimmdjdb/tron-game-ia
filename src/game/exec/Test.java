package game.exec;

import game.controller.Controller;
import game.model.entities.Bike;
import game.model.platform.Platform;
import game.model.platform.Position;

public class Test {
    public static void main(String[] args) {

        Platform p = Platform.getInstance();
        Bike b1 = new Bike(p.getRandomFreePosition());
        Bike b2 = new Bike(p.getRandomFreePosition());
        p.addBike(b1);
        p.addBike(b2);

        printPos(b1.getHeadPosition());
        printPos(b2.getHeadPosition());

        Controller ctrl = new Controller(p);
        while(true) {
            try {
                ctrl.update();
                System.out.println("p1 : " + b1.getHeadPosition());
                System.out.println("p2 : " + b2.getHeadPosition());
                Thread.sleep(41);
            } catch(Exception e) {

            }
        }
    }

    public static void printPos(Position p) {
        System.out.println(String.format("(%d, %d)", p.getCordX(), p.getCordY()));
    }
}
