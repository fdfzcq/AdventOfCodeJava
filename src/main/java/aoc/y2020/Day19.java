package aoc.y2020;

import java.util.HashMap;

import aoc.Input;

public class Day19 {
    public static void main(String[] args) {
        final String input = Input.inputFromFile(2020, 19);
        part2(input.split("\\n\\n"));
    }

    static void part1(String[] sections) {
        HashMap<String, String> rules = new HashMap<>();
        for(String ruleStr : sections[0].split("\n")) {
            String[] ruleArr = ruleStr.split(": ");
            rules.put(ruleArr[0], ruleArr[1]);
        }
        int n = 0;
        for(String str : sections[1].split("\n")) {
            if(matchesRule(str, "0", rules, 0) == str.length()) {
                n ++;
            }
        }
        System.out.println(n);
    }

    static void part2(String[] sections) {
        HashMap<String, String> rules = new HashMap<>();
        for(String ruleStr : sections[0].split("\n")) {
            String[] ruleArr = ruleStr.split(": ");
            rules.put(ruleArr[0], ruleArr[1]);
        }
        int n = 0;
        for(String str : sections[1].split("\n")) {
            int i = 0;
            int numOf42 = 0;
            int numOf31 = 0;
            while(true) {
                int res = matchesRule(str, "42", rules, i);
                if(res < 0 || res >= str.length()) {
                    break;
                } else {
                    i = res;
                    numOf42 ++;
                }
            }
            if(i != str.length() && i != 0) {
                while(true) {
                    int res = matchesRule(str, "31", rules, i);
                    if(res < 0) {
                        break;
                    } else {
                        i = res;
                        numOf31 ++;
                    }
                }
            }
            if(i == str.length() && numOf42 > numOf31 && numOf42 >= 2 && numOf31 >= 1){
                System.out.println(str);
                n ++;
            }
        }
        System.out.println(n);
    }

    // aaaabbb
    static int matchesRule(String str, String ruleName, HashMap<String, String> rules, int pointer) {
        String rule = ruleName.contains("a") ? "a" : ruleName.contains("b") ? "b" : rules.get(ruleName);
        //System.out.println("name: " + ruleName + " rule: " + rule + " pointer: " + pointer);
        if(pointer >= str.length()) { return -1; }
        if(rule.equals("a") || rule.equals("b")) {
            if(str.charAt(pointer) == rule.charAt(0)){
                return pointer + 1;
            } else {
                return -1;
            }
        } else {
            boolean matched = false;
            int p = pointer;
            for(String ruleSet : rule.split(" \\| ")) {
                String[] rArr = ruleSet.split(" ");
                boolean matched0 = true;
                pointer = p;
                for(String r : rArr) {
                    int newPointer = matchesRule(str, r, rules, pointer);
                    if (newPointer >= 0) { pointer = newPointer; }
                    else { matched0 = false; break; }
                }
                if(matched0) { matched = true; break; }
            }
            if(!matched) { return -1; }
        }
        //System.out.println("returning: " + pointer);
        return pointer;
    }
}
