package aoc.y2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import aoc.Coordinate;
import aoc.Input;

public class Day16 {
    public static void main(String[] args) {
        final String input = Input.inputFromFile(2020, 16);
        process(input.split("\\n\\n"));
    }

    static void process(String[] sections) {
        String rulesStr = sections[0];
        String myTicketSection = sections[1];
        String nearbyTicketStr = sections[2];
        HashMap<String, ArrayList<Coordinate>> rules = new HashMap<>();
        for(String ruleStr : rulesStr.split("\n")){
            String[] ruleArr = ruleStr.split(":\\s|\\sor\\s");
            String ruleName = ruleArr[0];
            ArrayList<Coordinate> ranges = new ArrayList<>();
            for(int i = 1; i < ruleArr.length; i++){
                String ruleRangeStr = ruleArr[i];
                String[] ruleRangeArr = ruleRangeStr.split("-");
                int low = Integer.valueOf(ruleRangeArr[0]);
                int high = Integer.valueOf(ruleRangeArr[1]);
                ranges.add(new Coordinate(low, high));
            }
            rules.put(ruleName, ranges);
        }
        String[] myTicketArr = myTicketSection.split("\n");
        ArrayList<ArrayList<Integer>> tickets = new ArrayList<>();
        ArrayList<Integer> myTicket = new ArrayList<>();
        for(String s : myTicketArr[1].split(",")){
            myTicket.add(Integer.valueOf(s));
        }
        tickets.add(myTicket);
        String[] nearbyTicketArr = nearbyTicketStr.split("\n");
        for(int i = 1; i < nearbyTicketArr.length; i++){
            ArrayList<Integer> ticket = new ArrayList<>();
            boolean valid = true;
            for(String numStr : nearbyTicketArr[i].split(",")){
                int num = Integer.valueOf(numStr);
                boolean v = rules.values().stream().anyMatch(
                    ranges -> ranges.stream().anyMatch(r -> num >= r.x && num <= r.y));
                valid = v && valid;
                ticket.add(num);
            }
            if(valid) { tickets.add(ticket); }
        }
        part2(rules, tickets);
    }

    static void part1(HashMap<String, ArrayList<Coordinate>> rules, String nearbyTicketStr) {
        String[] nearbyTicketArr = nearbyTicketStr.split("\n");
        long errorRate = 0L;
        for(int i = 1; i < nearbyTicketArr.length; i++){
            for(String numStr : nearbyTicketArr[i].split(",")){
                int num = Integer.valueOf(numStr);
                boolean valid = rules.values().stream().anyMatch(
                    ranges -> ranges.stream().anyMatch(r -> num >= r.x && num <= r.y));
                if(!valid) { errorRate = errorRate + (long)num; }
            }
        }
        System.out.println(errorRate);
    }

    static void part2(HashMap<String, ArrayList<Coordinate>> rules, ArrayList<ArrayList<Integer>> tickets) {
        String[] ruleMap = new String[rules.size()];
        while(true){
            for(String rule : Set.copyOf(rules.keySet())){
                ArrayList<Coordinate> ranges = rules.get(rule);
                int numOfMatched = 0;
                int matched = 0;
                for(int i = 0; i < tickets.get(0).size(); i++){
                    if(ruleMap[i] != null){ continue; }
                    final int idx = i;
                    Boolean matches = tickets.stream().allMatch(t -> ranges.stream().anyMatch(r -> t.get(idx) >= r.x && t.get(idx) <= r.y));
                    if(matches){
                        System.out.println(rule + " " + idx);
                        numOfMatched ++;
                        matched = idx;
                    }
                }
                if(numOfMatched == 1) {
                    ruleMap[matched] = rule;
                    rules.remove(rule);
                }
            }
            if(rules.isEmpty()) { break; }
        }
        for(int i = 0; i < ruleMap.length; i++){
            System.out.println(ruleMap[i] + " " + tickets.get(0).get(i));
        }
    }
}
