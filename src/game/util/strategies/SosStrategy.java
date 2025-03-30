package game.util.strategies;

import game.algo.SOS;
import game.model.entities.Bot;

public class SosStrategy implements BotStrategy {
    private static final int DEPTH = 4;
    @Override
    public void play(Bot bot) {
        bot.move(SOS.search(bot.getPlatform(), DEPTH, bot.getId()));
    }
}
