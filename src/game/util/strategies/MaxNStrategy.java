package game.util.strategies;

import game.model.platform.Platform;
import game.model.entities.Bot;
import game.algo.MaxN;

public class MaxNStrategy implements BotStrategy {
    private static final int DEPTH = 6;
    @Override
    public void play(Bot bot) {
        bot.move(MaxN.simulate(Platform.getInstance(), DEPTH, bot.getId()));
    }
}
