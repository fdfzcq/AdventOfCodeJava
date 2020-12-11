package aoc.y2020;

import aoc.Input;

import java.util.ArrayList;
import java.util.HashMap;

import aoc.Coordinate;

public class Day11 {
    final int floorCode = '.';
    final int emptyCode = 'L';
    final int seatedCode = '#';
    public static void main(String[] args) {
        final String input = Input.inputFromFile(2020, 11);
        HashMap<Coordinate, Character> seats = new HashMap<Coordinate, Character>();
        parseInputToSeats(input.split("\n"), seats);
        gom(seats, input.split("\n"));
    }

    static void parseInputToSeats(String[] lines, HashMap<Coordinate, Character> seats) {
        for(int y = 0; y < lines.length; y++){
            for(int x = 0; x < lines[0].length(); x++) {
                seats.put(new Coordinate(x, y), lines[y].charAt(x));
            }
        }
    }

    static void gom(HashMap<Coordinate, Character> seats, String[] lines) {
        int numOfSeats = 0;
        while(true) {
            ArrayList<Coordinate> changed = new ArrayList<Coordinate>();
            for(Coordinate coordinate : seats.keySet()) {
                if(seats.get(coordinate) == 'L' && numberOfAdjucentSeats(coordinate, seats, lines) == 0) {
                    changed.add(coordinate);
                    numOfSeats ++;
                }
                else if(seats.get(coordinate) == '#' && numberOfAdjucentSeats(coordinate, seats, lines) >= 5) {
                    changed.add(coordinate);
                    numOfSeats --;
                }
            }
            if(changed.isEmpty()) {
                System.out.println(numOfSeats);
                System.exit(0);;
            } else {
                for(Coordinate coordinate : changed) {
                    seats.put(coordinate, seats.get(coordinate) == '#' ? 'L' : '#');
                }
            }
        }
    }

    static int numberOfAdjucentSeats(Coordinate c, HashMap<Coordinate, Character> seats, String[] lines) {
        int num = 0;
        if(!hasEmptySeat(c, -1, -1, seats, lines)) { num++; }
        if(!hasEmptySeat(c, +1, +1, seats, lines)) { num++; }
        if(!hasEmptySeat(c, -1, +1, seats, lines)) { num++; }
        if(!hasEmptySeat(c, +1, -1, seats, lines)) { num++; }
        if(!hasEmptySeat(c, +0, -1, seats, lines)) { num++; }
        if(!hasEmptySeat(c, -1, +0, seats, lines)) { num++; }
        if(!hasEmptySeat(c, +1, +0, seats, lines)) { num++; }
        if(!hasEmptySeat(c, +0, +1, seats, lines)) { num++; }
        return num;
    }

    static boolean hasEmptySeat(Coordinate c, int xDir, int yDir, HashMap<Coordinate, Character> seats, String[] lines) {
        int x = c.x + xDir;
        int y = c.y + yDir;
        while(x >= 0 && y >= 0 && x < lines[0].length() && y < lines.length){
            if(seats.get(new Coordinate(x, y)) == 'L'){ return true; }
            if(seats.get(new Coordinate(x, y)) == '#'){ return false;}
            x += xDir;
            y += yDir;
        }
        return true;
    }
}
