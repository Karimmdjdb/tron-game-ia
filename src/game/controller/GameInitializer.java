package game.controller;

import java.util.List;
import java.util.Random;

import game.model.entities.Bot;
import game.model.platform.Platform;
import game.model.platform.Team;
import game.util.config.ConfigReader;
import game.util.config.Shared;
import game.util.strategies.MaxNStrategy;
import game.util.strategies.ParanoidStrategy;
import game.util.strategies.SosStrategy;
import game.util.strategies.BotStrategy;

public class GameInitializer {

    private static final String
        STRATEGY_TEAM1,
        STRATEGY_TEAM2;

    private static final Random random;

    static {
        STRATEGY_TEAM1 = ConfigReader.get("team_1_strat");
        STRATEGY_TEAM2 = ConfigReader.get("team_2_strat");
        random = new Random();
    }


   public static void init(Platform platform) {

    Team teamA = new Team();
    Team teamB = new Team();

    for (int i = 0; i < Shared.NB_PLAYERS; i++) {
        Bot bot = new Bot(platform.getRandomFreePosition(), platform, createStrategy(STRATEGY_TEAM1));
        teamA.addMember(bot);
    }

    for (int i = 0; i < Shared.NB_PLAYERS; i++) {
        Bot bot = new Bot(platform.getRandomFreePosition(), platform, createStrategy(STRATEGY_TEAM2));
        teamB.addMember(bot);
    }

    platform.setTeamA(teamA);
    platform.setTeamB(teamB);

    platform.setPlayerOrder();
}

// public static void prepareForSimulation(Platform platform, String strategy) {

//     Team teamA = new Team();
//     Team teamB = new Team();

//     for (int i = 0; i < NB_PLAYERS_TEAM1; i++) {
//         Bot bot = new Bot(platform.getRandomFreePosition(), platform, createStrategy(strategy));
//         teamA.addMember(bot);
//     }

//     for (int i = 0; i < NB_PLAYERS_TEAM2; i++) {
//         Bot bot = new Bot(platform.getRandomFreePosition(), platform, createStrategy("random"));
//         teamB.addMember(bot);
//     }

//     platform.setTeamA(teamA);
//     platform.setTeamB(teamB);

//     platform.setPlayerOrder();
// }

private static BotStrategy createStrategy(String strategyName) {
    switch (strategyName) {
        case "maxn": return new MaxNStrategy();
        case "paranoid": return new ParanoidStrategy();
        case "sos": return new SosStrategy();
        case "random": return List.of(new MaxNStrategy(), new ParanoidStrategy(), new SosStrategy()).get(random.nextInt(4));
        default: throw new IllegalArgumentException("Stratégie inconnue : " + strategyName);
    }
}
}