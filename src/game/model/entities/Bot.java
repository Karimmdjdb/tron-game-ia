package game.model.entities;

import game.model.platform.Platform;
import game.model.platform.Position;
import game.util.strategies.BotStrategy;

public class Bot extends Bike {
    private BotStrategy strategy;

    public Bot(Position initial_position, Platform platform, BotStrategy strategy) {
        super(initial_position, platform);
        this.strategy = strategy;
    }

    public void play() {
        strategy.play(this);
    }
}
