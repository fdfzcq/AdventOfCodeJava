package aoc.y2020;

import org.apache.commons.lang3.ArrayUtils;

import aoc.Coordinate;
import aoc.Input;

public class Day12 {
    final static char[] directions = {'W', 'N', 'E', 'S'};

    public static void main(String[] args) {
        final String input = Input.inputFromFile(2020, 12);
        part2(input.split("\n"));
    }

    static void part1(String[] lines) {
        char direction = 'E';
        Coordinate location = new Coordinate(0, 0);
        for(String line : lines) {
            char instruction = line.charAt(0);
            int distanceOrDegree = Integer.valueOf(line.substring(1));
            direction = maybeChangeDirection(instruction, direction, distanceOrDegree);
            if(instruction == 'F') { move(direction, distanceOrDegree, location); }
            else if(instruction != 'L' && instruction != 'R') { move(instruction, distanceOrDegree, location); }
        }
        System.out.println(Math.abs(location.x) + Math.abs(location.y));
    }

    static char maybeChangeDirection(char instruction, char currentDirection, int degree){
        int idxChange = instruction == 'R' ?  degree/90 : instruction == 'L' ? -(degree/90) : 0;
        int currentIdx = ArrayUtils.indexOf(directions, currentDirection);
        int idx = currentIdx + idxChange;
        while(idx < 0) { idx += 4; }
        if(idx > 3) { idx = idx % 4; }
        return directions[idx];
    }

    static void move(char direction, int distance, Coordinate location) {
        switch (direction) {
            case 'E':
                location.x = location.x + distance;
                break;
            case 'N':
                location.y = location.y + distance;
                break;
            case 'W':
                location.x = location.x - distance;
                break;
            case 'S':
                location.y = location.y - distance;
                break;
        }
    }

    static void part2(String[] lines) {
        Coordinate direction = new Coordinate(10, 1);
        Coordinate location = new Coordinate(0, 0);
        for(String line : lines) {
            char instruction = line.charAt(0);
            int distanceOrDegree = Integer.valueOf(line.substring(1));
            maybeChangeWayPoint(instruction, direction, distanceOrDegree);
            if(instruction == 'F') {
                location.x += distanceOrDegree * direction.x;
                location.y += distanceOrDegree * direction.y;
            }
        }
        System.out.println(Math.abs(location.x) + Math.abs(location.y));
    }

    static void maybeChangeWayPoint(char instruction, Coordinate direction, int degreeOrDistance) {
        if(instruction == 'R' || instruction == 'L' ){
            int x = direction.x;
            int y = direction.y;
            char dir1 = direction.x >= 0 ? 'E' : 'W';
            char dir2 = direction.y >= 0 ? 'N' : 'S';
            dir1 = maybeChangeDirection(instruction, dir1, degreeOrDistance);
            dir2 = maybeChangeDirection(instruction, dir2, degreeOrDistance);
            switch(dir1) {
                case 'N': direction.y = Math.abs(x); break;
                case 'S': direction.y = -Math.abs(x); break;
                case 'E': direction.x = Math.abs(x); break;
                case 'W': direction.x = -Math.abs(x); break;
            }
            switch(dir2) {
                case 'N': direction.y = Math.abs(y); break;
                case 'S': direction.y = -Math.abs(y); break;
                case 'E': direction.x = Math.abs(y); break;
                case 'W': direction.x = -Math.abs(y); break;
            }
        } else if (instruction != 'F') {
            move(instruction, degreeOrDistance, direction);
        }
    } 
}
