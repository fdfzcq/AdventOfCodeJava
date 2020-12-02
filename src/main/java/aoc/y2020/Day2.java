package aoc.y2020;

import aoc.Input;

public class Day2 {
    public static void main(String[] args) {
        final String input = Input.inputFromFile(2020, 2);
        part2(input.split("\n", -1));
    }

    static void part1(String[] input) {
        int valid = 0;
        for(String line : input) {
            String[] instructions = line.split(":", -1);
            String instruction = instructions[0];
            String passwd = instructions[1];

            String[] inst = instruction.split(" ", 2);
            String range = inst[0];
            char letter = inst[1].charAt(0);

            String[] rangeNums = range.split("-", 2);
            int lower = Integer.valueOf(rangeNums[0]);
            int higher = Integer.valueOf(rangeNums[1]);

            int letterN = 0;
            for(char c: passwd.toCharArray()) {
                if(c == letter){
                    letterN++;
                }
            }
            if(letterN <= higher && letterN >= lower) {
                valid++;
            }
        }
        System.out.println(valid);
    }

    static void part2(String[] input) {
        int valid = 0;
        for(String line : input) {
            String[] instructions = line.split(":", -1);
            String instruction = instructions[0];
            char[] passwd = instructions[1].toCharArray();

            String[] inst = instruction.split(" ", 2);
            String range = inst[0];
            char letter = inst[1].charAt(0);

            String[] rangeNums = range.split("-", 2);
            int lower = Integer.valueOf(rangeNums[0]);
            int higher = Integer.valueOf(rangeNums[1]);

            boolean lowerMatches = getValue(passwd, lower) == letter;
            boolean higherMatches = getValue(passwd, higher) == letter;
            if((lowerMatches && !higherMatches) || (!lowerMatches && higherMatches)) {
                valid++;
            }
        }
        System.out.println(valid);
    }

    static int getValue(char[] passwd, int i) {
        if(i < passwd.length){
            return passwd[i];
        }
        return -1;
    }
}
