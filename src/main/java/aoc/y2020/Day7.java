package aoc.y2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.google.common.base.Objects;

import aoc.Input;

public class Day7 {
    public static void main(String[] args) {
        final String input = Input.inputFromFile(2020, 7);
        HashMap<Bag, ArrayList<Bag>> bags = new HashMap<Bag, ArrayList<Bag>>();
        for(String line : input.split("\n")){
            String[] rule = line.split(" contain ");
            String parent = rule[0].replace("bags", "").trim();
            String[] children = rule[1].split(",");
            Bag parentBag = new Bag(parent, 1);
            ArrayList<Bag> childrenBags = new ArrayList<Bag>();
            for(String child : children) {
                String childColor = child.replaceAll("[0-9]", "").replace(".", "").replace("bags", "").replace("bag", "").trim();
                String numStr = child.replaceAll("[^0-9]", "");
                if(!numStr.isEmpty()){
                    int number = Integer.valueOf(numStr);
                    Bag childBag = new Bag(childColor, number);
                    childrenBags.add(childBag);
                }
            }
            bags.put(parentBag, childrenBags);
        }
        part2(bags);
    }

    static void part1(HashMap<Bag, ArrayList<Bag>> bags) {
        System.out.println(countParents("shiny gold", bags));
    }

    static void part2(HashMap<Bag, ArrayList<Bag>> bags) {
        System.out.println(countChildren("shiny gold", bags));
    }

    static int countParents(String color, HashMap<Bag, ArrayList<Bag>> bags) {
        int numOfMatches = 0;
        for(Bag bag : bags.keySet()) {
            HashSet<Bag> visited = new HashSet<Bag>();
            if(containsBag(color, bags, bag, visited)) { numOfMatches ++; }
        }
        return numOfMatches;
    }

    static boolean containsBag(String color, HashMap<Bag, ArrayList<Bag>> bags, Bag bag, HashSet<Bag> visited) {
        boolean contains = false;
        if(!visited.contains(bag)){
            for(Bag child:bags.get(bag)) {
                if(child.color.equals(color)){ contains = true; }
                if(!visited.contains(child) && bags.containsKey(child)){
                    contains = contains || containsBag(color, bags, child, visited);
                }
            }
        }
        visited.add(bag);
        return contains;
    }

    static int countChildren(String color, HashMap<Bag, ArrayList<Bag>> bags) {
        return countChildren(new Bag(color, 1), bags, 0);
    }

    static int countChildren(Bag bag, HashMap<Bag, ArrayList<Bag>> bags, int numberOfBags) {
        int n = 0;
        for(Bag child: bags.get(bag)) {
            n += child.number * countChildren(child, bags, 1);
        }
        return n + numberOfBags;
    }

    static class Bag{
        String color;
        int number;

        Bag(String color, int number) {
            this.color = color;
            this.number = number;
        }

        @Override
        public boolean equals(Object obj) {
            return obj.getClass() == this.getClass() &&
                ((Bag)obj).color.equals(color);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(this.color);
        }
    }
}
