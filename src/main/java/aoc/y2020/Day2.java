package aoc.y2020;

import java.util.Arrays;

import com.google.common.base.CharMatcher;

import aoc.Input;

public class Day2 {
    public static void main(String[] args) {
        final String input = Input.inputFromFile(2020, 2);
        long numOfValid = Arrays.asList(input.split("\n"))
                                .stream()
                                .map(Day2::parsePassword)
                                .filter(p -> p.isValidPart1())
                                .count();
        System.out.println(numOfValid);
    }

    static Password parsePassword(String line) {
        String[] instructions = line.split(":");
        String instruction = instructions[0];
        String passwd = instructions[1];

        String[] inst = instruction.split(" ");
        String range = inst[0];
        char letter = inst[1].charAt(0);

        String[] rangeNums = range.split("-");
        int lower = Integer.valueOf(rangeNums[0]);
        int higher = Integer.valueOf(rangeNums[1]);

        return new Password(letter, lower, higher, passwd);
    }

    public static class Password {
        char character;
        int lowerBound;
        int higherBound;
        String password;

        Password(char c, int lower, int higher, String passwd) {
            this.character = c;
            this.lowerBound = lower;
            this.higherBound = higher;
            this.password = passwd;
        }

        boolean isValidPart1() {
            int numOfChar = CharMatcher.is(this.character).countIn(this.password);
            return numOfChar <= this.higherBound && numOfChar >= this.lowerBound;
        }

        boolean isValidPart2() {
            boolean lowerMatches = getValue(this.password, this.lowerBound) == this.character;
            boolean higherMatches = getValue(this.password, this.higherBound) == this.character;
            return lowerMatches ^ higherMatches;
        }

        private int getValue(String passwd, int index) {
            return index < passwd.length() ? passwd.charAt(index) : -1;
        }
    }
}
