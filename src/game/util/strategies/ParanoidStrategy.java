package game.util.strategies;

import game.algo.Paranoid;
import game.model.entities.Bot;

public class ParanoidStrategy implements BotStrategy {
    private static final int DEPTH = Integer.parseInt(game.util.config.ConfigReader.get("depth"));
    @Override
    public void play(Bot bot) {
        bot.move(Paranoid.search(bot.getPlatform(), DEPTH, bot.getId()));
    }
}
