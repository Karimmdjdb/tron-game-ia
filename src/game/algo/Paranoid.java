package game.algo;

import game.model.platform.Direction;
import game.model.platform.Platform;

public class Paranoid {

    public static Direction simulate(Platform platform, int depth, int player_id) {
        return (Direction)paranoid(new GameState(platform), depth, player_id, true);
    }

    public static Object paranoid(GameState state, int depth, int player_id, boolean is_first_call) {
        return Direction.LEFT;
    }


}
