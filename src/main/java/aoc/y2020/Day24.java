package aoc.y2020;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import aoc.Coordinate;
import aoc.Input;

public class Day24 {
    final static String[] directions = new String[] { "e", "w", "se", "sw", "nw", "ne" };

    public static void main(String[] args) {
        final String input = Input.inputFromFile(2020, 24);
        part2(input.split("\n"));
    }

    private static void part2(String[] lines) {
        HashMap<Coordinate, Boolean> hexaGrid = new HashMap<>();
        part1(lines, hexaGrid);
        stepDays(hexaGrid, 100);
    }

    private static void stepDays(HashMap<Coordinate, Boolean> hexaGrid, int i) {
        while(i > 0) {
            Set<Coordinate> toFlip = new HashSet<>();
            for(Map.Entry<Coordinate, Boolean> tile : hexaGrid.entrySet()) {
                for(String d : directions) {
                    Coordinate c = move(d, tile.getKey());
                    if(shouldFlip(c, hexaGrid)) { toFlip.add(c); }
                }
                if(shouldFlip(tile.getKey(), hexaGrid)) { toFlip.add(tile.getKey()); }
            }
            for(Coordinate c : toFlip) {
                hexaGrid.put(c, !hexaGrid.getOrDefault(c, true));
            }
            countBlackTiles(hexaGrid);
            i--;
        }
    }

    static boolean shouldFlip(Coordinate c, HashMap<Coordinate, Boolean> hexaGrid) {
        if(!hexaGrid.getOrDefault(c, true)) {
            //black tile
            int n = 0;
            for(String d : directions) {
                Coordinate cm = move(d, c);
                if(!hexaGrid.getOrDefault(cm, true)) {
                    n ++;
                }
            }
            if(n == 0 || n > 2){
                return true;
            }
        }
        if(hexaGrid.getOrDefault(c, true)) {
            //white tile
            int n = 0;
            for(String d : directions) {
                Coordinate cm = move(d, c);
                if(!hexaGrid.getOrDefault(cm, true)) {
                    n ++;
                }
            }
            if(n == 2){
                return true;
            }
        }
        return false;
    }

    private static void countBlackTiles(HashMap<Coordinate, Boolean> hexaGrid) {
        int n = 0;
        for (Map.Entry<Coordinate, Boolean> entry : hexaGrid.entrySet()) {
            if (!entry.getValue()) {
                n++;
            }
        }
        System.out.println(n);
    }

    private static void part1(String[] lines, HashMap<Coordinate, Boolean> hexaGrid) {
        for (String line : lines) {
            flipTiles(line, hexaGrid, 0, new Coordinate(0, 0));
        }
        countBlackTiles(hexaGrid);
    }

    private static void flipTiles(String line, HashMap<Coordinate, Boolean> hexaGrid, int idx, Coordinate current) {
        if(idx >= line.length()) { hexaGrid.put(current, !hexaGrid.getOrDefault(current, true)); return; }
        for (String d : directions) {
            if(line.substring(idx, idx + d.length()).equals(d)){
                Coordinate newCoordinate = move(d, current);
                flipTiles(line, hexaGrid, idx + d.length(), newCoordinate);
                break;
            }
        }
    }

    private static Coordinate move(String d, Coordinate current) {
        if(d.equals("e")) {
            return new Coordinate(current.x - 1, current.y);
        }
        if(d.equals("w")) {
            return new Coordinate(current.x + 1, current.y);
        }
        if(d.equals("se")) {
            return new Coordinate(current.x - 1, current.y - 1);
        }
        if(d.equals("ne")) {
            return new Coordinate(current.x, current.y + 1);
        }
        if(d.equals("sw")) {
            return new Coordinate(current.x, current.y - 1);
        }
        if(d.equals("nw")) {
            return new Coordinate(current.x + 1, current.y + 1);
        }
        return null;
    }
}
