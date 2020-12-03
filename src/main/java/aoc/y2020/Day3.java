package aoc.y2020;

import aoc.Input;

public class Day3 {
    public static void main(String[] args) {
        final String input = Input.inputFromFile(2020, 3);
        //part1(input.split("\n", -1));
        part2(input.split("\n", -1));
    }

    static long part1(String[] lines, int right, int down) {
        int numOfTrees = 0;
        int lineIndex = 0;
        int location = 0;
        while(lineIndex + down < lines.length) {
            char[] line2 = lines[lineIndex + down].toCharArray();
            numOfTrees = line2[(location + right) % line2.length] == '#' ? numOfTrees + 1 : numOfTrees;
            location = location + right;
            lineIndex += down;
        }
        return (long)numOfTrees;
    }

    static void part2(String[] lines) {
        System.out.println(part1(lines, 1, 1) * part1(lines, 3, 1) * part1(lines, 5, 1) * part1(lines, 7, 1) * part1(lines, 1, 2));
    }
}
