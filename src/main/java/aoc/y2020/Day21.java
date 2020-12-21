package aoc.y2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import aoc.Input;

public class Day21 {
    public static void main(String[] args) {
        final String input = Input.inputFromFile(2020, 21);
        process(input.split("\n"));
    }

    static void process(String[] lines) {
        HashMap<String, List<String>> allergens = new HashMap<>();
        HashMap<String, String> ingreAll = new HashMap<>();
        HashMap<String, Integer> ingredients = new HashMap<>();
        int p = 0;
        while(true) {
            for(String line : lines) {
                String[] ingreAndAllergens = line.split(" \\(contains ");
                List<String> algs = Arrays.asList(ingreAndAllergens[1].substring(0, ingreAndAllergens[1].length() - 1).split(", "));
                List<String> ingrs = Arrays.asList(ingreAndAllergens[0].split(" "));
                for(String al : algs) {
                    if(!allergens.containsKey(al)) {
                        allergens.put(al, ingrs);
                    } else {
                        List<String> possibleIngrs = allergens.get(al);
                        List<String> newIngrs = new ArrayList<>();
                        for(String i : possibleIngrs) {
                            if(ingrs.contains(i) && !ingreAll.containsKey(i)){
                                newIngrs.add(i);
                            }
                        }
                        if(!newIngrs.isEmpty()) { allergens.put(al, newIngrs); }
                        if(newIngrs.size() == 1) { System.out.println(al + " " + newIngrs.get(0)); ingreAll.put(newIngrs.get(0), al); }
                    }
                }
                for(String igr : ingreAndAllergens[0].split(" ")) {
                    if(p == 0){
                        ingredients.put(igr, ingredients.getOrDefault(igr, 0) + 1);
                    }
                }
            }
            if(ingreAll.size() == p) { break; }
            p = ingreAll.size();
        }

        // part1
        /* int n = 0;
        for(String i : ingredients.keySet()) {
            if(!ingreAll.containsKey(i)) {
                n += ingredients.get(i);
            }
        }
        System.out.println(n); */

        // part2
        String str = "";
        List<String> algs = ingreAll.values().stream().collect(Collectors.toList());
        Collections.sort(algs);
        for(String a : algs) {
            str = str.equals("") ? allergens.get(a).get(0) : str + "," + allergens.get(a).get(0);
        }
        System.out.println(str);
    }
}
