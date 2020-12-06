package aoc.y2020;

import java.util.Arrays;

import aoc.Input;

public class Day6 {
    public static void main(String[] args) {
        final String input = Input.inputFromFile(2020, 6);
        part2(input.split("\\n\\n"));
    }

    static void part1(String[] groups) {
        int sum = 0;
        int[] questions = new int[26];
        for(String group : groups) {
            for(char c : group.toCharArray()) {
                if(c != ' ' && c != '\n'){
                    questions[c - 'a'] = 1;
                }
            }
            sum += Arrays.stream(questions).sum();
        }
        System.out.println(sum);
    }

    static void part2(String[] groups) {
        int sum = 0;
        for(String group : groups) {
            int[] questions = new int[26];
            String[] passengers = group.split("\n");
            for(String passenger : passengers){
                for(char c:passenger.toCharArray()){
                    questions[c - 'a'] = questions[c - 'a'] + 1;
                }
            }
            for(int num: questions){
                if(num == passengers.length){
                    sum++;
                }
            }
        }
        System.out.println(sum);
    }
}
