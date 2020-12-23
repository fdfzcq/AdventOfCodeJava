package aoc.y2020;

import java.util.HashMap;

import aoc.Input;

public class Day23 {
    public static void main(String[] args) {
        //final String input = Input.inputFromFile(2020, 23);
        part2("583976241");
    }

    static void part1(String str) {
        HashMap<Integer, Node> nodesMap = new HashMap<>();
        Node node = null;
        Node firstNode = null;
        for(char c : str.toCharArray()) {
            int n = c - '0';
            Node newNode = new Node(n);
            if(node != null) { node.next = newNode; }
            else { firstNode = newNode; }
            node = newNode;
            nodesMap.put(n, node);
        }
        node.next = firstNode;
        cupgame(firstNode, nodesMap, 100);
    }

    static void cupgame(Node currentNode, HashMap<Integer, Node> nodesMap, int t) {
        while(true) {
            Node pickUp1 = currentNode.next;
            Node pickUp2 = currentNode.next.next;
            Node pickUp3 = currentNode.next.next.next;
            Node destination = null;
            int destinationNum = currentNode.number - 1 == 0 ? 1000000 : currentNode.number - 1;
            while(true) {
                if(destinationNum == pickUp1.number || destinationNum == pickUp2.number || destinationNum == pickUp3.number) {
                    if(destinationNum > 1) { destinationNum --; }
                    else { destinationNum = 1000000; }
                } else {
                    destination = nodesMap.get(destinationNum);
                    break;
                }
            }
            // move pickups
            Node destNext = destination.next;
            currentNode.next = pickUp3.next;
            destination.next = pickUp1;
            pickUp3.next = destNext;
            t--;
            if(t == 0){
                Node n = nodesMap.get(1).next;
                System.out.println(n.number + " " + n.next.number + " " + (long)n.number * n.next.number);
                break;
            }
            currentNode = currentNode.next;
        }
    }

    static void part2(String str) {
        HashMap<Integer, Node> nodesMap = new HashMap<>();
        Node node = null;
        Node firstNode = null;
        for(char c : str.toCharArray()) {
            int n = c - '0';
            Node newNode = new Node(n);
            if(node != null) { node.next = newNode; }
            else { firstNode = newNode; }
            node = newNode;
            nodesMap.put(n, node);
        }
        for(int i = 10; i <= 1000000; i++) {
            Node newNode = new Node(i);
            node.next = newNode;
            node = newNode;
            nodesMap.put(i, node);
        }
        node.next = firstNode;
        cupgame(firstNode, nodesMap, 10000000);
    }
}
