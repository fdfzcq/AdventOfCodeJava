package aoc.y2019;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

import aoc.Input;

public class Day20 {
    static HashMap<Integer, Integer> map = new HashMap<>();
    static Queue<DFS> dfsQueue = new LinkedList<>();
    static HashSet<Integer> prevs = new HashSet<>();
    static HashMap<Coordinate, Coordinate> jumpMap = new HashMap<>();
    static Coordinate start;
    static Coordinate end;

    final static char startChar = '1';
    final static char endChar = 'M';

    public static void main(final String args[]) {
        final String input = Input.inputFromFile(2019, 20);
        populateMap(input);
        dfsQueue.add(new DFS(start));
        processQueue();
    }

    public static void processQueue() {
        System.out.println("start processing");
        int steps = 0;
        int x = 0;
        int y = 0;
        int depth = 0;
        while (x != end.x || y != end.y || depth != 0) {
            DFS d = dfsQueue.poll();
            if (d.steps != steps) {
                System.out.println("steps: " + steps);
                //System.out.println("queue size: " + dfsQueue.size());
                steps = d.steps;
            }
            x = d.location.x;
            y = d.location.y;
            depth = d.depth;
            dfs(d);
        }
        System.out.println("answer: " + steps);
    }

    public static int pair(int a, int b) {
        return (a + b) * (a + b + 1)/2 + b;
    }

    public static void dfs(DFS dfs) {
        Coordinate location = dfs.location;
        int val = map.containsKey(location.hashCode()) ? map.get(location.hashCode()) : -1;
        //System.out.println("x: " + location.x + " y: " + location.y + " depth: " + dfs.depth +
        //    " val: " + val);
        if (dfs.depth < 0 || dfs.depth > 100) {
            // do nothing
        }
        else if (val != -1) {
            prevs.add(pair(dfs.depth, location.hashCode()));
            dfs.addStep();
            traverse(dfs, location);
        }
    }
    
    public static void traverse (DFS dfs, Coordinate location) {
        Coordinate[] locs = { new Coordinate(location.x - 1, location.y)
            , new Coordinate(location.x + 1, location.y)
            , new Coordinate(location.x, location.y - 1)
            , new Coordinate(location.x, location.y + 1) };
        for (Coordinate coordinate : locs) {
            if (isReachable(coordinate, prevs, dfs.depth)) {
                DFS dfs2 = new DFS(coordinate, dfs);
                dfsQueue.add(dfs2);
            }
        }
        if (jumpMap.containsKey(location)) {
            Coordinate portLoc = jumpMap.get(location);
            int x = portLoc.x;
            int y = portLoc.y;
            if (x == 0 || y == 0 || x == 118 || y == 118) {
                dfs.increaseDepth();
            } else {
                dfs.decreaseDepth();
            }
            if (isReachable(portLoc, prevs, dfs.depth)){
                dfsQueue.add(new DFS(portLoc, dfs));
            }
        }
    }

    public static boolean isReachable(Coordinate coordinate, HashSet<Integer> prevs, int depth) {
        return (map.containsKey(coordinate.hashCode())) &&
            (!prevs.contains(pair(depth, coordinate.hashCode()))) &&
            coordinate.x >= 0 && coordinate.y >= 0;
    }

    static public class DFS {
        Coordinate location;
        int steps;
        int depth;

        public DFS(Coordinate location) {
            this.location = location;
            this.steps = 0;
            this.depth = 0;
        }

        public void addStep() {
            this.steps++;
        }

        public void increaseDepth() {
            this.depth++;
        }

        public void decreaseDepth() {
            this.depth--;
        }

        public DFS(Coordinate location, DFS dfs) {
            this.location = location;
            this.steps = dfs.steps;
            this.depth = dfs.depth;
        }
    }
    // parsing

    public static void populateMap(String input) {
        String[] lines = input.split("\n");
        int y = 0;
        int portalN = 1;
        HashMap<Integer, Coordinate> tempMap = new HashMap<>();
        for (String line : lines) {
            int x = 0;
            char[] chrs = line.toCharArray();
            for (char c : chrs) {
                if (c == '.') {
                    int key = new Coordinate(x, y).hashCode();
                    map.put(key, 0);
                } else if (c == startChar) {
                    int key = new Coordinate(x, y).hashCode();
                    start = new Coordinate(x, y);
                    map.put(key, 0);
                } else if (c == endChar) {
                    int key = new Coordinate(x, y).hashCode();
                    end = new Coordinate(x, y);
                    map.put(key, 0);
                }
                else if (c != '#' && c != ' ') {
                    Coordinate loc = new Coordinate(x, y);
                    int key = loc.hashCode();
                    if (tempMap.containsKey((int) c)) {
                        Coordinate c1 = tempMap.get((int) c);
                        jumpMap.put(loc, c1);
                        jumpMap.put(c1, loc);
                    } else {
                        tempMap.put((int) c, loc);
                    }
                    map.put(key, portalN);
                    portalN ++;
                }
                x++;
            }
            y++;
        }
    }

    static public class Coordinate {
        int x;
        int y;
        int depth;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
            this.depth = 0;
        }

        @Override
        public int hashCode() {
            return (x + y)*(x + y + 1)/2 + y;
        }

        @Override
        public boolean equals(Object obj) {
            Coordinate co = (Coordinate) obj;
            if (this.x == co.x && this.y == co.y) {
                return true;
            }
            return false;
        }
    }

}