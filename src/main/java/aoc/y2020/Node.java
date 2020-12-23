package aoc.y2020;

public class Node {
    int number;
    Node next;

    Node(int n) {
        this.number = n;
    }

    void nextNode(Node node){
        this.next = node;
    }
}
