package game.model.entities;

import game.model.platform.Platform;
import game.model.platform.Position;
import game.util.strategies.BotStrategy;
import game.util.strategies.MaxNStrategy;
import game.util.strategies.ParanoidStrategy;
import game.util.strategies.SosStrategy;

public class Bot extends Bike {
    private BotStrategy strategy;
    private String strategy_name;

    public Bot(Position initial_position, Platform platform, BotStrategy strategy) {
        super(initial_position, platform);
        this.strategy = strategy;
        if(strategy instanceof MaxNStrategy) strategy_name = "MaxN";
        if(strategy instanceof ParanoidStrategy) strategy_name = "Paranoïd";
        if(strategy instanceof SosStrategy) strategy_name = "Sos";
    }

    public void play() {
        strategy.play(this);
    }

    public String getStrategyName() {
        return strategy_name;
    }
}
