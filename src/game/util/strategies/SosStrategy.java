package game.util.strategies;

import game.algo.SOS;
import game.model.entities.Bot;
import game.model.platform.Platform;

public class SosStrategy implements BotStrategy {
    private static final int DEPTH = 4;
    @Override
    public void play(Bot bot) {
        bot.move(SOS.search(Platform.getInstance(), DEPTH, bot.getId()));
    }
}
