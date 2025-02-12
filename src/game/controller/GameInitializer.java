package game.controller;

import game.model.entities.Bot;
import game.model.platform.Platform;
import game.model.platform.Team;
import game.util.config.ConfigReader;
import game.util.strategies.MaxNStrategy;
import game.util.strategies.ParanoidStrategy;

public class GameInitializer {

    private static final int
        NB_PLAYERS_TEAM1,
        NB_PLAYERS_TEAM2;

    static {
        NB_PLAYERS_TEAM1 = Integer.parseInt(ConfigReader.get("team_1_nb_players"));
        NB_PLAYERS_TEAM2 = Integer.parseInt(ConfigReader.get("team_2_nb_players"));
    }

    public static void init() {

        // platforme de jeu
        Platform platform = Platform.getInstance();

        // création des équipes
        Team team_A = new Team();
        Team team_B = new Team();

        // création des joueurs (bots)
        for(int i=0; i<NB_PLAYERS_TEAM1; i++) {
            Bot bot = new Bot(platform.getRandomFreePosition(), new ParanoidStrategy());
            team_A.addMember(bot);
        }
        for(int i=0; i<NB_PLAYERS_TEAM2; i++) {
            Bot bot = new Bot(platform.getRandomFreePosition(), new ParanoidStrategy());
            team_B.addMember(bot);
        }

        platform.setTeamA(team_A);
        platform.setTeamB(team_B);

    }
}
