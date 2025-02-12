package game.util.strategies;

import game.algo.Paranoid;
import game.model.entities.Bot;
import game.model.platform.Platform;

public class ParanoidStrategy implements BotStrategy {
    private static final int DEPTH = 6;
    @Override
    public void play(Bot bot) {
        bot.move(Paranoid.simulate(Platform.getInstance(), DEPTH, bot.getId()));
    }
}
