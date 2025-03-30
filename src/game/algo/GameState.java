package game.algo;

import java.util.*;
import game.model.platform.Direction;
import game.model.platform.Position;
import game.model.entities.Bike;
import game.model.platform.Platform;

public class GameState {
    
    // Position actuelle de chaque joueur (player_id -> Position)
    private Map<Integer, Position> players;
    // Ensemble des positions visitées (global)
    private Set<Position> visited;
    // Historique par joueur pour le backtracking
    private Map<Integer, Stack<Position>> history;
    private final int SIZE;
    
    public GameState(Platform platform) {
        players = new HashMap<>();
        visited = new HashSet<>();
        history = new HashMap<>();
        SIZE = platform.getSize();
        // Pour chaque Bike, on enregistre sa position actuelle et ses cases déjà visitées
        for (Bike bike : platform.getBikes()) {
            players.put(bike.getId(), bike.getHeadPosition());
            visited.addAll(bike.getStreakPositions());
            history.put(bike.getId(), new Stack<>());
        }
    }
    
    /**
     * Retourne la liste des directions légales pour le joueur d'identifiant player_id,
     * en fonction de sa position actuelle et des cases déjà visitées.
     */
    public List<Direction> legalMoves(int player_id) {
        List<Direction> moves = new ArrayList<>();
        Position current = players.get(player_id);
        for (Direction d : Direction.values()) {
            Position newPos = getNextPosition(current, d);
            if (isValidPosition(newPos)) {
                moves.add(d);
            }
        }
        return moves;
    }
    
    /**
     * Simule le mouvement du joueur player_id dans la direction d.
     * La position courante est sauvegardée dans l'historique et marquée comme visitée.
     */
    public void simulateMove(int player_id, Direction d) {
        Position current = players.get(player_id);
        // Sauvegarde la position courante
        history.get(player_id).push(current);
        // Marque la position comme visitée (on suppose que, dans une simulation, on peut "libérer" cette case lors du backtracking)
        visited.add(current);
        // Met à jour la position
        Position newPos = getNextPosition(current, d);
        players.put(player_id, newPos);
    }
    
    /**
     * Annule le dernier mouvement du joueur player_id.
     * La position précédente est retirée de l'historique et la case visitée est "défaite".
     */
    public void goBack(int player_id) {
        Stack<Position> hist = history.get(player_id);
        if (!hist.isEmpty()) {
            Position prev = hist.pop();
            // On retire la position précédemment ajoutée à visited
            visited.remove(prev);
            players.put(player_id, prev);
        }
    }
    
    /**
     * Retourne true si aucun joueur ne peut effectuer de mouvement légal.
     * On considère ici l'état terminal lorsque tous les joueurs n'ont plus de coup.
     */
    public boolean isTerminal() {
        for (Integer playerId : players.keySet()) {
            if (canPlayerMove(playerId)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Un joueur peut bouger si sa position est valide.
     */
    public boolean canPlayerMove(int player_id) {
        return isValidPosition(players.get(player_id));
    }
    
    /**
     * Retourne true si le joueur player_id est hors du plateau.
     */
    public boolean isPlayerOut(int player_id) {
        Position pos = players.get(player_id);
        return pos.getCordX() < 0 || pos.getCordY() < 0 || pos.getCordX() >= SIZE || pos.getCordY() >= SIZE;
    }
    
    /**
     * Évalue l'état courant en renvoyant une Map associant à chaque player_id un score.
     * Ici, si la position d'un joueur est invalide ou hors du plateau, le score est -1.
     * Sinon, on combine plusieurs critères (distance au mur, liberté d'action, proximité des autres).
     */
    public Map<Integer, Integer> evaluate() {
        Map<Integer, Integer> scores = new HashMap<>();
        for (Map.Entry<Integer, Position> entry : players.entrySet()) {
            int id = entry.getKey();
            Position pos = entry.getValue();
            int score;
            if (!isValidPosition(pos) || isPlayerOut(id)) {
                score = -1;
            } else {
                int s1 = evaluateDistanceToWall(id);
                int s2 = evaluateLiberty(id);
                int s3 = evaluateDistanceWithOthers(id);
                score = s1 + s2 + s3;
            }
            scores.put(id, score);
        }
        return scores;
    }
    
    /**
     * Calcule la distance minimale du joueur aux bords du plateau.
     */
    public int evaluateDistanceToWall(int player_id) {
        Position pos = players.get(player_id);
        int x = pos.getCordX(), y = pos.getCordY();
        int dist = Math.min(Math.min(x, y), Math.min(SIZE - 1 - x, SIZE - 1 - y));
        return dist;
    }
    
    /**
     * Évalue le nombre de cases accessibles à partir de la position du joueur (liberté de mouvement),
     * en effectuant une recherche en largeur limitée à un certain nombre de niveaux.
     */
    public int evaluateLiberty(int player_id) {
        Set<Position> seen = new HashSet<>();
        Queue<Position> queue = new LinkedList<>();
        Position start = players.get(player_id);
        queue.add(start);
        seen.add(start);
        int area = 0;
        int depth = 5;
        while (!queue.isEmpty() && depth > 0) {
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                Position current = queue.poll();
                area++;
                for (Direction d : Direction.values()) {
                    Position neighbor = getNextPosition(current, d);
                    if (isValidPosition(neighbor) && !seen.contains(neighbor)) {
                        seen.add(neighbor);
                        queue.add(neighbor);
                    }
                }
            }
            depth--;
        }
        return area;
    }
    
    /**
     * Calcule la distance minimale (Euclidienne) entre le joueur et les autres joueurs.
     */
    public int evaluateDistanceWithOthers(int player_id) {
        Position p1 = players.get(player_id);
        int minDist = Integer.MAX_VALUE;
        for (Map.Entry<Integer, Position> entry : players.entrySet()) {
            if (entry.getKey() == player_id) continue;
            Position p2 = entry.getValue();
            int dist = (int) Math.sqrt(Math.pow(p2.getCordX() - p1.getCordX(), 2) +
                                        Math.pow(p2.getCordY() - p1.getCordY(), 2));
            minDist = Math.min(minDist, dist);
        }
        return minDist;
    }
    
    /**
     * Retourne la position voisine à partir d'une position donnée et d'une direction.
     */
    public Position getNextPosition(Position pos, Direction d) {
        int x = pos.getCordX(), y = pos.getCordY();
        switch(d) {
            case LEFT: return Position.from(x - 1, y);
            case RIGHT: return Position.from(x + 1, y);
            case UP: return Position.from(x, y - 1);
            case DOWN: return Position.from(x, y + 1);
            default: return pos;
        }
    }
    
    /**
     * Une position est valide si elle est dans le plateau et n'a pas encore été visitée.
     */
    public boolean isValidPosition(Position pos) {
        return pos.getCordX() >= 0 && pos.getCordX() < SIZE &&
               pos.getCordY() >= 0 && pos.getCordY() < SIZE &&
               !visited.contains(pos);
    }
}
