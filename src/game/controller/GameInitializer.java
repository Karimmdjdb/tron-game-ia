package game.controller;

import game.model.entities.Bot;
import game.model.platform.Platform;
import game.model.platform.Team;
import game.util.config.ConfigReader;
import game.util.strategies.MaxNStrategy;
import game.util.strategies.ParanoidStrategy;
import game.util.strategies.BotStrategy;
import game.view.*;

public class GameInitializer {

    private static final int
        NB_PLAYERS_TEAM1,
        NB_PLAYERS_TEAM2;

    static {
        NB_PLAYERS_TEAM1 = Integer.parseInt(ConfigReader.get("team_1_nb_players"));
        NB_PLAYERS_TEAM2 = Integer.parseInt(ConfigReader.get("team_2_nb_players"));
    }

   public static void init(String teamAStrategy, String teamBStrategy) {
    Platform platform = Platform.getInstance();

    Team teamA = new Team();
    Team teamB = new Team();

    for (int i = 0; i < NB_PLAYERS_TEAM1; i++) {
        Bot bot = new Bot(platform.getRandomFreePosition(), createStrategy(teamAStrategy));
        teamA.addMember(bot);
    }

    for (int i = 0; i < NB_PLAYERS_TEAM2; i++) {
        Bot bot = new Bot(platform.getRandomFreePosition(), createStrategy(teamBStrategy));
        teamB.addMember(bot);
    }

    platform.setTeamA(teamA);
    platform.setTeamB(teamB);
}

private static BotStrategy createStrategy(String strategyName) {
    switch (strategyName) {
        case "MaxNStrategy": return new MaxNStrategy();
        case "ParanoidStrategy": return new ParanoidStrategy();
        case "RandomStrategy": return new MaxNStrategy();
        default: throw new IllegalArgumentException("Stratégie inconnue : " + strategyName);
    }
}
}