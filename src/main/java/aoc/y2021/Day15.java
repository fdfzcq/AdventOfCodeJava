package aoc.y2021;

import aoc.Coordinate;
import aoc.Input;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day15 {
    static final int BASE = 100;
    static DefaultDirectedWeightedGraph<Coordinate, DefaultWeightedEdge > graph = new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class);
    static HashMap<Coordinate, Integer> map;

    public static void main(String[] args) {
        final String input = Input.inputFromFile(2021, 15);
        map = new HashMap<>();
        String[] strs = input.split("\n");
        for(int y = 0; y < BASE; y++){
            for(int x = 0; x < BASE; x++) {
                int c = Character.getNumericValue(strs[y].charAt(x));
                for(int i = 0; i < 5; i++){
                    for(int j = 0; j < 5; j++){
                        int v = c + i + j > 9 ? c + i + j - 9 : c + i + j;
                        map.put(new Coordinate(x + i * BASE, y + j * BASE), v);
                    }
                }
            }
        }
        for (Map.Entry<Coordinate, Integer> entry : map.entrySet()) {
            graph.addVertex(entry.getKey());
        }
        for (Map.Entry<Coordinate, Integer> entry : map.entrySet()) {
            int x = entry.getKey().x; int y = entry.getKey().y;
            add_edge(x, y, x + 1, y);
            add_edge(x, y, x - 1, y);
            add_edge(x, y, x, y + 1);
            add_edge(x, y, x, y - 1);
        }
        System.out.println(shortestPath());
    }

    public static void add_edge(int x1, int y1, int x2, int y2) {
        if(map.containsKey(new Coordinate(x2, y2))){
            DefaultWeightedEdge edge = graph.addEdge(new Coordinate(x1, y1), new Coordinate(x2, y2));
            if(edge != null) {
                graph.setEdgeWeight(edge, map.get(new Coordinate(x2, y2)));
            }
        }
    }

    public static int shortestPath()
    {
        final DijkstraShortestPath<Coordinate, DefaultWeightedEdge> pathFinder =
                new DijkstraShortestPath<>(graph);
        // , new Coordinate(0, 0), new Coordinate(5 * BASE - 1, 5 * BASE - 1)
        List<Coordinate> path = pathFinder.getPath(new Coordinate(0, 0), new Coordinate(5 * BASE - 1, 5 * BASE - 1)).getVertexList();
        return path.stream().map(c -> map.get(c)).collect(Collectors.summingInt(Integer::intValue)) - map.get(new Coordinate(0, 0));
    }
}
