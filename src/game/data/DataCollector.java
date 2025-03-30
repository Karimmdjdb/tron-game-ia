package game.data;

import java.util.HashMap;
import java.util.Map;

public class DataCollector {
    private static final Map<Integer, Map<Integer, Map<String, Integer>>> deltaTeam = new HashMap<>();
    private static final Map<Integer, Map<Integer, Map<String, Integer>>> deltaSize = new HashMap<>();

    public static void addDeltaTeam(int depth, int team, String algo) {
        if(!deltaTeam.containsKey(depth)) deltaTeam.put(depth, new HashMap<>());
        if(!deltaTeam.get(depth).containsKey(team)) deltaTeam.get(depth).put(team, new HashMap<>());
        Integer i = deltaTeam.get(depth).get(team).get(algo);
        deltaTeam.get(depth).get(team).put(algo,i != null ? i+1 : 1);
    }

    public static void addDeltaSize(int depth, int size, String algo) {
        if(!deltaSize.containsKey(depth)) deltaSize.put(depth, new HashMap<>());
        if(!deltaSize.get(depth).containsKey(size)) deltaSize.get(depth).put(size, new HashMap<>());
        Integer i = deltaSize.get(depth).get(size).get(algo);
        System.out.println(i);
        deltaSize.get(depth).get(size).put(algo,i != null ? i+1 : 1);
    }

    public static Map<Integer, Map<Integer, Map<String, Integer>>> getDeltaTeamResults() {
        return deltaTeam;
    }

    public static Map<Integer, Map<Integer, Map<String, Integer>>> getDeltaSizeResults() {
        return deltaSize;
    }
}
