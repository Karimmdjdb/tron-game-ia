package test;

import game.model.platform.*;
import game.model.entities.*;

public class Debug {
    public static void main(String[] args) {
        Platform platform = Platform.getInstance();
        Bike b1 = new Bike(platform.getRandomFreePosition());
        platform.setEntity(b1);
        Bike b2 = new Bike(platform.getRandomFreePosition());
        platform.setEntity(b2);
        System.out.println("Bike 1 : x=" + b1.getCordX() + " y=" + b1.getCordY());
        System.out.println("Bike 2 : x=" + b2.getCordX() + " y=" + b2.getCordY());
        System.out.println("Everything OK !");
    }
}