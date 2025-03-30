package game.util.config;

public class Shared {
    public static int SIZE;
    public static int DEPTH;
    public static int NB_PLAYERS;

    static {
        SIZE = Integer.parseInt(ConfigReader.get("platform_size"));
        DEPTH = Integer.parseInt(ConfigReader.get("depth"));
        NB_PLAYERS = Integer.parseInt(ConfigReader.get("nb_players_per_team"));
    }
}