package aoc.y2020;

public class Node {
    int number;
    Node left;
    Node right;

    Node(int n) {
        this.number = n;
    }

    void addNode(int n){
        Node node = this;
        while(true) {
            if(node.number <= n) {
                if(node.left == null){
                    node.left = new Node(n);
                    break;
                } else {
                    node = node.left;
                }
            }
            if(node.number > n) {
                if(node.right == null){
                    node.right = new Node(n);
                    break;
                } else {
                    node = node.right;
                }
            }
        }
    }
}
