package game.exec;

import game.algo.MinMax;
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

        // MinMax alg = new MinMax(p);
        // System.out.println(alg.root.value);
        System.out.println(Integer.MAX_VALUE);
        System.out.println("everything ok !");
    }

    public static void printPos(Position p) {
        System.out.println(String.format("(%d, %d)", p.getCordX(), p.getCordY()));
    }
}
