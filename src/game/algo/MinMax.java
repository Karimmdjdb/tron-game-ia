package game.algo;
import java.util.HashSet;
import java.util.Set;

import game.model.entities.Bike;
import game.model.platform.Platform;
import game.model.platform.Position;

public class MinMax {
    public static class Node {
        public  Position p1,p2;
        public  Set<Position> visited;
        // public  Node n1,n2,n3,n4;
        // public int value = 0;

        private Node() {
        }

        private Node(Position p1,Position p2){
            this.p1 = p1;
            this.p2 = p2;
            visited = new HashSet<>();
        }

        private Node(Node other) {
            p1 = Position.from(other.p1.getCordX(), other.p1.getCordY());
            p2 = Position.from(other.p2.getCordX(), other.p2.getCordY());
            visited = new HashSet<>(other.visited);
        }

        public static Node createNode(Position p1,Position p2){
            return new Node(p1, p2);
        }

        public static Node copyNode(Node node) {
            return new Node(node);
        }

        public static Node convertPlatformToNode(Platform platform) {
            Node n = new Node();
            n.visited = new HashSet<>(platform.getVisitedPositions());
            n.p1 = platform.getBikes().get(0).getHeadPosition();
            n.p2 = platform.getBikes().get(1).getHeadPosition();
            return n;
        }

        public boolean isLeaf() {
            if(visited.contains(p1) || visited.contains(p2)){
                return true;
            }
            if(p1.getCordX() < 0 || p1.getCordY() < 0 || p1.getCordX() >= SIZE || p1.getCordY() >= SIZE) return true;
            if(p2.getCordX() < 0 || p2.getCordY() < 0 || p2.getCordX() >= SIZE || p2.getCordY() >= SIZE) return true;
            return false;
        }
    }

    private static final int SIZE = Platform.SIZE;

    public static Bike.Direction minmax(Platform platform, int depth, boolean maximizing_player) {
        int best = minmax(Node.convertPlatformToNode(platform), depth, maximizing_player, true);
        Bike.Direction best_direction;
        switch (best) {
            case 0:
                best_direction = Bike.Direction.LEFT;
                break;
            case 1:
                best_direction = Bike.Direction.UP;
                break;
            case 2:
                best_direction = Bike.Direction.RIGHT;
                break;
            case 4:
                best_direction = Bike.Direction.DOWN;
                break;
            default:
                best_direction = Bike.Direction.DOWN;
                break;
        }
        return best_direction;
    }
    private static int minmax(Node node, int depth, boolean maximizing_player, boolean first_call) {

        // cas de base
        if(node.isLeaf() || depth == 0) {
            return maximizing_player ? 1 : -1;
        }

        /// création des noeuds qui corréspondent aux 4 états suivant du monde (un état pour chaque déplacement)
        // duplication du noeud initial
        Node n1 = Node.copyNode(node);
        Node n2 = Node.copyNode(node);
        Node n3 = Node.copyNode(node);
        Node n4 = Node.copyNode(node);
        // choix du joueur qui va se déplacer
        Position p = maximizing_player ? node.p1 : node.p2;
        // ajout de l'ancienne position du joueur qui s'est deplacé à la liste des positions visitées
        n1.visited.add(p);
        n2.visited.add(p);
        n3.visited.add(p);
        n4.visited.add(p);
        // déplacement le joueur
        if(maximizing_player) { // si c'est le joueur 1
            n1.p1 = Position.from(p.getCordX()-1, p.getCordY());
            n2.p1 = Position.from(p.getCordX(), p.getCordY()-1);
            n3.p1 = Position.from(p.getCordX()+1, p.getCordY());
            n4.p1 = Position.from(p.getCordX(), p.getCordY()+1);
        } else { // si c'est le joueur 2
            n1.p2 = Position.from(p.getCordX()-1, p.getCordY());
            n2.p2 = Position.from(p.getCordX(), p.getCordY()-1);
            n3.p2 = Position.from(p.getCordX()+1, p.getCordY());
            n4.p2 = Position.from(p.getCordX(), p.getCordY()+1);
        }

        // appel récursif
        int v1 = minmax(n1, depth-1, !maximizing_player, false);
        int v2 = minmax(n2, depth-1, !maximizing_player, false);
        int v3 = minmax(n3, depth-1, !maximizing_player, false);
        int v4 = minmax(n4, depth-1, !maximizing_player, false);

        // maximisation ou minimisation en fonction du joueur actuel
        int best;
        if(maximizing_player) { // si c'est le joueur 1
            best = Math.max(v1, Math.max(v2, Math.max(v3, v4)));
        } else { // si c'est le joueur 2
            best = Math.min(v1, Math.min(v2, Math.min(v3, v4)));
        }

        // si c'est l'appel initial alors le meilleur coup est retourné (sous forme d'entier)
        if(first_call) {
            if(best == v1) return 0;
            else if(best == v2) return 1;
            else if(best == v3) return 2;
            else return 4;
        }

        // sinon on retourne la meilleure valeur des noeuds enfants
        return best;
    }
}
