package game.util.strategies;

import game.model.entities.Bot;
import game.algo.MaxN;

public class MaxNStrategy implements BotStrategy {
    private static final int DEPTH = Integer.parseInt(game.util.config.ConfigReader.get("depth"));
    @Override
    public void play(Bot bot) {
        bot.move(MaxN.search(bot.getPlatform(), DEPTH, bot.getId()));
    }
}
