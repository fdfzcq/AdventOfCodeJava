package aoc.y2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import aoc.Coordinate;
import aoc.Input;

public class Day17 {
    public static void main(String[] args) {
        final String input = Input.inputFromFile(2020, 17);
        HashSet<Coordinate> cubes = new HashSet<>();
        parseInput(input.split("\n"), cubes);
        runCycle(cubes, 6);
    }

    static void parseInput(String[] lines, HashSet<Coordinate> cubes) {
        for(int y = 0; y < lines.length; y++){
            for(int x = 0; x < lines[0].length(); x++){
                if(lines[y].charAt(x) == '#') { cubes.add(new Coordinate(x, y, 0, 0)); };
            }
        }
    }

    static void runCycle(HashSet<Coordinate> cubes, int numOfCycles){
        while(numOfCycles > 0) {
            HashMap<Coordinate, Integer> newCubes = new HashMap<Coordinate, Integer>();
            HashSet<Coordinate> newSet = new HashSet<Coordinate>();
            for(Coordinate cube:cubes) {
                processNeighbours(newCubes, cubes, cube);
            }
            for(Map.Entry<Coordinate, Integer> m: newCubes.entrySet()){
                if(m.getValue() == 1) { newSet.add(m.getKey()); }
            }
            cubes = newSet;
            numOfCycles --;
            System.out.println(cubes.size());
        }
    }

    static void processNeighbours(HashMap<Coordinate, Integer> newCubes, HashSet<Coordinate> cubes, Coordinate currentCube){
        ArrayList<Coordinate> neighbours = getNeighbours(currentCube);
        for(Coordinate c:neighbours){
            if(!newCubes.containsKey(c)){
                int numOfCubes = 0;
                for(Coordinate neighbour:getNeighbours(c)){
                    if(cubes.contains(neighbour)){
                        numOfCubes ++;
                    }
                }
                if(cubes.contains(c) && (numOfCubes == 2 || numOfCubes == 3)){
                    newCubes.put(c, 1);
                } else if (cubes.contains(c)){
                    newCubes.put(c, 0);
                } else if (numOfCubes == 3) {
                    newCubes.put(c, 1);
                } else {
                    newCubes.put(c, 0);
                }
            }
        }
    }

    static ArrayList<Coordinate> getNeighbours(Coordinate cube) {
        int[] changes = {-1, 0, 1};
        int newX = cube.x; int newY = cube.y; int newZ = cube.z; int newW = cube.w;
        ArrayList<Coordinate> neighbours = new ArrayList<>();
        for(int changeX: changes){
            newX = changeX + cube.x;
            for(int changeY: changes) {
                newY = changeY + cube.y;
                for(int changeZ: changes){
                    newZ = changeZ + cube.z;
                    for(int changeW: changes) {
                        newW = changeW + cube.w;
                        if(!(newX == cube.x && newY == cube.y && newZ == cube.z && newW == cube.w)){
                            neighbours.add(new Coordinate(newX, newY, newZ, newW));
                        }
                    }
                }
            }
        }
        return neighbours;
    }
}
