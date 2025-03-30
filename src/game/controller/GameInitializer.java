package game.controller;

import game.model.entities.Bot;
import game.model.platform.Platform;
import game.model.platform.Team;
import game.util.config.ConfigReader;
import game.util.strategies.MaxNStrategy;
import game.util.strategies.ParanoidStrategy;
import game.util.strategies.SosStrategy;
import game.util.strategies.BotStrategy;

public class GameInitializer {

    private static final int
        NB_PLAYERS_TEAM1,
        NB_PLAYERS_TEAM2;

    private static final String
        STRATEGY_TEAM1,
        STRATEGY_TEAM2;

    static {
        NB_PLAYERS_TEAM1 = Integer.parseInt(ConfigReader.get("team_1_nb_players"));
        NB_PLAYERS_TEAM2 = Integer.parseInt(ConfigReader.get("team_2_nb_players"));
        STRATEGY_TEAM1 = ConfigReader.get("team_1_strat");
        STRATEGY_TEAM2 = ConfigReader.get("team_2_strat");
    }

   public static void init() {
    Platform platform = Platform.getInstance();

    Team teamA = new Team();
    Team teamB = new Team();

    for (int i = 0; i < NB_PLAYERS_TEAM1; i++) {
        Bot bot = new Bot(platform.getRandomFreePosition(), createStrategy(STRATEGY_TEAM1));
        teamA.addMember(bot);
    }

    for (int i = 0; i < NB_PLAYERS_TEAM2; i++) {
        Bot bot = new Bot(platform.getRandomFreePosition(), createStrategy(STRATEGY_TEAM2));
        teamB.addMember(bot);
    }

    platform.setTeamA(teamA);
    platform.setTeamB(teamB);

    platform.setPlayerOrder();
}

private static BotStrategy createStrategy(String strategyName) {
    switch (strategyName) {
        case "maxn": return new MaxNStrategy();
        case "paranoid": return new ParanoidStrategy();
        case "sos": return new SosStrategy();
        case "random": return new MaxNStrategy();
        default: throw new IllegalArgumentException("Stratégie inconnue : " + strategyName);
    }
}
}