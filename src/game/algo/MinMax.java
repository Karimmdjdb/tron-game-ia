package game.algo;
import java.util.HashSet;
import java.util.Set;

import game.model.platform.Platform;
import game.model.platform.Position;

public class MinMax {
    public static class Node {
        public  Position p1,p2;
        public  Set<Position> visited; 
        public  Node n1,n2,n3,n4;
        public int win = 0;
        
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
    }

    public Node root;
    public MinMax(Platform platfrom){
        Position p1 = platfrom.getBikes().get(0).getHeadPosition();
        Position p2 = platfrom.getBikes().get(1).getHeadPosition();
        root = Node.createNode(p1, p2);
        buildTree1(root);
    }

    public boolean isLeaf(Node node){
        if(node.visited.contains(node.p2) || node.visited.contains(node.p2)){
            return true;
        }
        if(node.p1.getCordX() < 0 || node.p1.getCordY() < 0 || node.p1.getCordX() >= 30 || node.p1.getCordY() >= 30) return true;
        if(node.p2.getCordX() < 0 || node.p2.getCordY() < 0 || node.p2.getCordX() >= 30 || node.p2.getCordY() >= 30) return true;
        return false;
    }

    public void buildTree1(Node root){
        if(isLeaf(root)) {
            root.win = 1;
            return;
        }

        Node n1 = Node.copyNode(root);
        Node n2 = Node.copyNode(root);
        Node n3 = Node.copyNode(root);
        Node n4 = Node.copyNode(root);

        Position p1 = root.p2;

        n1.visited.add(p1);
        n1.p2 = Position.from(p1.getCordX()-1, p1.getCordY());
        buildTree2(n1);
        
        n2.visited.add(p1);
        n2.p2 = Position.from(p1.getCordX(), p1.getCordY()-1);
        buildTree2(n2);

        n3.visited.add(p1);
        n3.p2 = Position.from(p1.getCordX()+1, p1.getCordY());
        buildTree2(n3);

        n4.visited.add(p1);
        n4.p2 = Position.from(p1.getCordX(), p1.getCordY()+1);
        buildTree2(n4);

        root.win = Math.max(n1.win, Math.max(n2.win, Math.max(n3.win, n4.win)));
        }

    public void buildTree2(Node root){
        if(isLeaf(root)) {
            root.win = -1;
            return;
        }
        Node n1 = Node.copyNode(root);
        Node n2 = Node.copyNode(root);
        Node n3 = Node.copyNode(root);
        Node n4 = Node.copyNode(root);

        Position p2 = root.p2;

        n1.visited.add(p2);
        n1.p2 = Position.from(p2.getCordX()-1, p2.getCordY());
        buildTree1(n1);
        
        n2.visited.add(p2);
        n2.p2 = Position.from(p2.getCordX(), p2.getCordY()-1);
        buildTree1(n2);

        n3.visited.add(p2);
        n3.p2 = Position.from(p2.getCordX()+1, p2.getCordY());
        buildTree1(n3);

        n4.visited.add(p2);
        n4.p2 = Position.from(p2.getCordX(), p2.getCordY()+1);
        buildTree1(n4);

        root.win = Math.min(n1.win, Math.min(n2.win, Math.min(n3.win, n4.win)));

    }
}
