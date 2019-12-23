package aoc.y2019;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

import aoc.Input;

public class Day18 {
    static HashMap<Integer, Integer> map = new HashMap<>();
    static Queue<DFS> dfsQueue = new LinkedList<>();
    static HashMap<Integer, HashSet<Integer>> keyPrevs = new HashMap<>();
    static Coordinate start;

    public static void main(final String args[]) {
        final String input = Input.inputFromFile(2019, 18);
        populateMap(input);
        //removeDeadEnds();
        dfsQueue.add(new DFS(start));
        processQueue();
    }

    public static void processQueue() {
        System.out.println("start processing");
        int steps = 0;
        int keys = 0;
        while(keys != 15){
            DFS d = dfsQueue.poll();
            if(d.steps != steps) {
                System.out.println("steps: " + steps);
                System.out.println("keys: " + keys);
                System.out.println("queue length: " + dfsQueue.size());
                steps = d.steps;
            }
            keys = d.fetchedKeys;
            dfs(d);
        }
    }

    public static void dfs(DFS dfs){
        Coordinate location = dfs.location;
        int val = map.containsKey(location.toKey()) ? map.get(location.toKey()) : -1;
        HashSet<Integer> prevs =
            keyPrevs.containsKey(dfs.fetchedKeys) ? keyPrevs.get(dfs.fetchedKeys) : new HashSet<>();
        if (val == 0) {
            prevs.add(location.toKey());
            keyPrevs.put(dfs.fetchedKeys, prevs);
            dfs.addStep();
            continueTraverse(dfs);
        } else if (val > 96) {
            if ((dfs.fetchedKeys & (1 << val - 97)) != 0) {
                prevs.add(location.toKey());
                keyPrevs.put(dfs.fetchedKeys, prevs);
                dfs.addStep(); 
                continueTraverse(dfs);
            } else {
                dfs.addKey(val);
                dfs.addStep();
                continueTraverse(dfs);
            }
        } else if (val > 64) {
            //if ((dfs.fetchedKeys & (1 << val - 65)) == (1 << val - 65)) {
                prevs.add(location.toKey());
                keyPrevs.put(dfs.fetchedKeys, prevs);
                dfs.addStep(); 
                continueTraverse(dfs);
            //}
        }
    }

    public static void continueTraverse(DFS dfs) {
        Coordinate location = dfs.location;
        Coordinate[] locs = { new Coordinate(location.x - 1, location.y)
                            , new Coordinate(location.x + 1, location.y)
                            , new Coordinate(location.x, location.y - 1)
                            , new Coordinate(location.x, location.y + 1)};
        HashSet<Integer> prevs =
            keyPrevs.containsKey(dfs.fetchedKeys) ? keyPrevs.get(dfs.fetchedKeys) : new HashSet<>();
        for (Coordinate coordinate : locs) {
            if (isReachable(coordinate, prevs)) {
                DFS dfs2 = new DFS(coordinate, dfs);
                dfsQueue.add(dfs2);
            }
        }
    }

    public static boolean isReachable(Coordinate coordinate, HashSet<Integer> prevs) {
        return (map.containsKey(coordinate.toKey())) && (!prevs.contains(coordinate.toKey()));
    }

    static public class DFS {
        Coordinate location;
        Integer fetchedKeys;
        int steps;

        public DFS(Coordinate location){
            fetchedKeys = 0;
            this.location = location;
            this.steps = 0;
        }

        public void addStep(){
            this.steps++;
        }

        public void addKey(int key){
            this.fetchedKeys = this.fetchedKeys | (1 << (key - 97));
        }

        public DFS(Coordinate location, DFS dfs) {
            this.location = location;
            this.fetchedKeys = dfs.fetchedKeys;
            this.steps = dfs.steps;
        }
    }
    // parsing

    public static void populateMap (String input) {
        String[] lines = input.split(";");
        int y = 0;
        for (String line : lines) {
            int x = 0;
            char[] chrs = line.toCharArray();
            for (char c : chrs) {
                if (c == '.') {
                    int key = new Coordinate(x, y).toKey();
                    map.put(key, 0);
                }
                else if (c == '@') {
                    int key = new Coordinate(x, y).toKey();
                    start = new Coordinate(x, y);
                    map.put(key, 0);
                }
                else if (c != '#') {
                    int key = new Coordinate(x, y).toKey();
                    map.put(key, (int) c);
                }
                x++;
            }
            y++;
        }
    }

    static public class Coordinate {
        int x;
        int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int toKey() {
            return (x * 10000) + y;
        }
    }

}