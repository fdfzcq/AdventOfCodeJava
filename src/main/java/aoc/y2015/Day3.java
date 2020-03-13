package aoc.y2015;

import aoc.Input;

import java.util.HashSet;

import aoc.Coordinate;

public class Day3 {
    public static void main(String[] args) {
        String input = Input.inputFromFile(2015, 3);
        HashSet<Coordinate> houses = new HashSet<>();
        processInput(houses, input);
        res(houses);
    }

    public static void res(HashSet<Coordinate> houses) {
        System.out.println("result: " + houses.size());
    }

    public static void processInput(HashSet<Coordinate> houses, String input) {
        char[] charList = input.toCharArray();
        int xSanta = 0;
        int ySanta = 0;
        int xRobot = 0;
        int yRobot = 0;
        houses.add(new Coordinate(0, 0));
        boolean isSanta = true;
        for (char c : charList) {
            int x = isSanta ? xSanta : xRobot;
            int y = isSanta ? ySanta : yRobot;
            switch(c) {
                case '<':
                    x = x - 1;
                    break;
                case '>':
                    x = x + 1;
                    break;
                case '^':
                    y = y - 1;
                    break;
                case 'v':
                    y = y + 1;
                    break;
            }
            houses.add(new Coordinate(x, y));
            if(isSanta) {
                xSanta = x;
                ySanta = y;
                isSanta = false;
            } else {
                xRobot = x;
                yRobot = y;
                isSanta = true;
            }
        }
    }
}