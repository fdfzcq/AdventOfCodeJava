package aoc.y2020;

import java.util.HashSet;

import aoc.Input;

public class Day5 {
    public static void main(String[] args) {
        final String input = Input.inputFromFile(2020, 5);
        //part1(input.split("\n", -1));
        part2(input.split("\n"));
    }

    static void part1(String[] lines) {
        int highest = Integer.MIN_VALUE;
        for (String instructions : lines) {
            int row = findCoordinate(instructions, 0, 127, 0, 6);
            int column = findCoordinate(instructions, 0, 7, 7, 9);
            int seatId = row * 8 + column;
            highest = seatId > highest ? seatId : highest;
        }
        System.out.println(highest);
    }

    static void part2(String[] lines) {
        int[][] plane = new int[128][8];
        HashSet<Integer> seatIds = new HashSet<Integer>();
        for (String instructions : lines) {
            int row = findCoordinate(instructions, 0, 127, 0, 6);
            int column = findCoordinate(instructions, 0, 7, 7, 9);
            plane[row][column] = 1;
            seatIds.add(row * 8 + column);
        }
        System.out.println(findSeat(plane, seatIds));
    }

    static int findSeat(int[][] plane, HashSet<Integer> seatIds) {
        for(int row = 1; row < plane.length - 1; row ++){
            for(int column = 0; column < plane[0].length; column ++){
                int seatId = row * 8 + column;
                if(plane[row][column] == 0 && seatIds.contains(seatId + 1) && seatIds.contains(seatId - 1)){
                    return seatId;
                }
            }
        }
        return 0;
    }

    static int findCoordinate(String instructions, int start, int end, int charIdx, int lastIdx) {
        if(charIdx > lastIdx) { return start; }
        char instruction = instructions.charAt(charIdx);
        if(instruction == 'F' || instruction == 'L') {
            int newEnd = (start + end) / 2;
            return findCoordinate(instructions, start, newEnd, charIdx + 1, lastIdx);
        } else {
            int newStart = (int)Math.ceil((start + end) / 2.0);
            return findCoordinate(instructions, newStart, end, charIdx + 1, lastIdx);
        }
    }
}
