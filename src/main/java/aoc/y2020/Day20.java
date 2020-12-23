package aoc.y2020;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Objects;

import aoc.Input;

public class Day20 {
    public static void main(String[] args) {
        final String input = Input.inputFromFile(2020, 20);
        part2(input.split("\\n\\n"));
    }

    static void part2(String[] sections) {
        HashMap<Integer, List<String>> tiles = new HashMap<>();
        for(String section : sections) {
            String[] lines = section.split("\n");
            String originalTile = lines[1];
            for(int i = 2; i < lines.length; i++){
                originalTile = originalTile + "\n" + lines[i];
            }
            int id = Integer.valueOf(lines[0].split(" |:")[1]);
            List<String> possibleTile = new ArrayList<>();
            possibleTile.add(originalTile);
            possibleTile.add(flip(originalTile));
            for(int t = 1; t < 4; t++) {
                possibleTile.add(rotate(originalTile, t));
                possibleTile.add(flip(rotate(originalTile, t)));
            }
            tiles.put(id, possibleTile);
        }
        String l = rotate("##...####.\n........#.\n........#.\n..#...##.#\n.........#\n#.#.#.#..#\n.#.....#.#\n.....##.#.\n##.......#\n..#.##....", 2);
        String[] map = new String[12];
        tiles.remove(1549);
        for(int i = 0; i < 12; i++) {
            while(true) {
                String[] lines = l.split("\n");
                String southSide = lines[lines.length - 1];
                String tileToAdd = findSouthTile(southSide, tiles);
                if(tileToAdd.equals("")) {break;};
                l = l + "\n" + tileToAdd;
            }
            map[i] = l;
            StringBuilder eastSide = new StringBuilder();
            String[] lines = l.split("\n");
            for(int m = 0 ; m < 10 ; m++){
                eastSide.append(lines[m].charAt(9));
            }
            l = findEastTile(eastSide.toString(), tiles);
        }
        StringBuilder mapStr = new StringBuilder();
        for(int i = 0; i < 121; i++) {
            if(i % 10 != 0 && i % 10 != 9) {
                for(String str : map) {
                    mapStr.append(str.split("\n")[i].substring(1, 9));
                }
                mapStr.append("\n");
            }
        }
        String mpStr = mapStr.toString();
        System.out.println(mpStr.length());
        int numOfMonsters = 0;
        for(String str : new String[]{ mpStr, flip(mpStr),
                                       rotate(mpStr, 1), flip(rotate(mpStr, 1)),
                                       rotate(mpStr, 2), flip(rotate(mpStr, 2)),
                                       rotate(mpStr, 3), flip(rotate(mpStr, 3))}) {
            String[] arr = str.split("\n");
            for(int y = 1; y < arr.length - 1; y++) {
                for(int x = 0; x < arr[0].length() - 19; x++) {
                    if(arr[y].charAt(x) == '#' &&
                       arr[y + 1].charAt(x + 1) == '#' &&
                       arr[y + 1].charAt(x + 4) == '#' &&
                       arr[y].charAt(x + 5) == '#' &&
                       arr[y].charAt(x + 6) == '#' &&
                       arr[y + 1].charAt(x + 7) == '#' &&
                       arr[y + 1].charAt(x + 10) == '#' &&
                       arr[y].charAt(x + 11) == '#' &&
                       arr[y].charAt(x + 12) == '#' &&
                       arr[y + 1].charAt(x + 13) == '#' &&
                       arr[y + 1].charAt(x + 16) == '#' &&
                       arr[y].charAt(x + 17) == '#' &&
                       arr[y].charAt(x + 18) == '#' &&
                       arr[y].charAt(x + 19) == '#' &&
                       arr[y - 1].charAt(x + 18) == '#') {
                           numOfMonsters ++;
                       }
                }
            }
        }
        if(numOfMonsters != 0) {
            System.out.println(mpStr.chars().filter(c -> c == '#').count() - 15 * numOfMonsters);
        }
    }

    static String findSouthTile(String side, HashMap<Integer, List<String>> tiles) {
        String res = "";
        for(Map.Entry<Integer, List<String>> entry : tiles.entrySet()) {
            int id = entry.getKey();
            List<String> possibleTiles = entry.getValue();
            for(String tile : possibleTiles) {
                if(side.equals(tile.split("\n")[0])) {
                    tiles.remove(id);
                    return tile;
                }
            }
        }
        return res;
    }

    static String findEastTile(String side, HashMap<Integer, List<String>> tiles) {
        String res = "";
        for(Map.Entry<Integer, List<String>> entry : tiles.entrySet()) {
            int id = entry.getKey();
            List<String> possibleTiles = entry.getValue();
            for(String tile : possibleTiles) {
                StringBuilder str = new StringBuilder();
                String[] lines = tile.split("\n");
                for(int i = 0; i < 10; i++) {
                    str.append(lines[i].charAt(0));
                }
                if(str.toString().equals(side)) {
                    tiles.remove(id);
                    return tile;
                }
            }
        }
        return res;
    }

    static String flip(String str) {
        StringBuilder newStr = new StringBuilder();
        for(String line : str.split("\n")) {
            for(int i = 0; i < line.length(); i++) {
                newStr.append(line.charAt(line.length() - 1 - i));
            }
            newStr.append("\n");
        }
        return newStr.toString().trim();
    }

    static String rotate(String str, int n) {
        StringBuilder newStr = new StringBuilder();
        int w = str.split("\n").length;
        char[][] rotated = new char[w][w];
        while(n > 0) {
            String[] lines = str.split("\n");
            for(int y = 0; y < lines.length; y++){
                for(int x = 0; x < lines[0].length(); x++) {
                    rotated[x][w - 1 - y] = lines[y].charAt(x);
                }
            }
            n--;
            newStr = new StringBuilder();
            for(char[] ls : rotated) {
                for(char c: ls) {
                    newStr.append(c);
                }
                newStr.append("\n");
            }
            str = newStr.toString();
        }
        return newStr.toString().trim();
    }

    static void part1(String[] sections) {
        HashMap<BitSet, Set<Tile>> sideMap = new HashMap<>();
        HashMap<Integer, Tile> tiles = new HashMap<>();
        for(String section : sections) {
            parseTiles(section, tiles, sideMap);
        }
        findCorners(tiles, sideMap);
    }

    static int[] findCorners(HashMap<Integer, Tile> tiles, HashMap<BitSet, Set<Tile>> sideMap) {
        int[] corners = new int[4];
        int n = 0;
        for(Tile tile : tiles.values()) {
            // not flipped
            int nOfMatched = 0;
            for(BitSet s : new BitSet[]{tile.sideN, tile.sideS, tile.sideW, tile.sideE}){
                if(sideMap.get(s).size() == 2 ) {
                    nOfMatched ++;
                }
            }
            if(nOfMatched == 2){ corners[n] = tile.id; n++; continue; }
            // flipped
            nOfMatched = 0;
            for(BitSet sf : new BitSet[]{tile.sideNF, tile.sideSF, tile.sideWF, tile.sideEF}){
                if(sideMap.get(sf).size() < 2) { nOfMatched ++; }
            }
            if(nOfMatched == 2){ tile.flipped = true; corners[n] = tile.id; n++; }
        }
        return corners;
    }

    static boolean hasMatches(BitSet bs, HashMap<BitSet, List<Tile>> sideMap) {
        return sideMap.get(bs).size() > 1;
    }

    static void parseTiles(String section, HashMap<Integer, Tile> tiles, HashMap<BitSet, Set<Tile>> sideMap) {
        BitSet n = new BitSet(); BitSet s = new BitSet(); BitSet w = new BitSet(); BitSet e = new BitSet();
        BitSet nf = new BitSet(); BitSet sf = new BitSet(); BitSet wf = new BitSet(); BitSet ef = new BitSet();
        String[] tileArr = section.split("\n");
        int id = Integer.valueOf(tileArr[0].split(" |:")[1]);
        for(int i = 1; i < tileArr.length; i++) {
            if(i == 1) {
                for(int m = 0; m < tileArr[i].length(); m++){
                    if(tileArr[i].charAt(m) == '#') {
                        n.set(m); nf.set(tileArr[i].length() - 1 - m);
                    }
                }
            }
            if(i == tileArr.length - 1) {
                for(int m = 0; m < tileArr[i].length(); m++){
                    if(tileArr[i].charAt(m) == '#') {
                        s.set(m); sf.set(tileArr[i].length() - 1 - m);
                    }
                }
            }
            if(tileArr[i].charAt(0) == '#') {
                w.set(i - 1); wf.set(tileArr.length  - 1 - i);
            }
            if(tileArr[i].charAt(tileArr[0].length() - 1) == '#'){
                e.set(i - 1); ef.set(tileArr.length - 1 - i);
            }
        }
        Tile tile = new Tile(id, n, s, w, e, nf, sf, wf, ef, tileArr);
        updateSideMap(n, sideMap, tile); updateSideMap(s, sideMap, tile); updateSideMap(w, sideMap, tile); updateSideMap(e, sideMap, tile);
        updateSideMap(nf, sideMap, tile); updateSideMap(sf, sideMap, tile); updateSideMap(wf, sideMap, tile); updateSideMap(ef, sideMap, tile);
        tiles.put(id, tile);
    }

    static void updateSideMap(BitSet bs, HashMap<BitSet, Set<Tile>> sideMap, Tile tile) {
        Set<Tile> tiles = sideMap.getOrDefault(bs, new HashSet<Tile>());
        tiles.add(tile);
        sideMap.put(bs, tiles);
    }

    static class Tile {
        BitSet sideN; BitSet sideS; BitSet sideW; BitSet sideE;
        BitSet sideNF; BitSet sideSF; BitSet sideWF; BitSet sideEF;
        Tile north; Tile south; Tile west; Tile east;
        String[] tileArr;
        int id;
        boolean flipped = false;
        int rotated = 0;
        
        Tile(int id) {
            this.id = id;
        }

        Tile(int id, BitSet n, BitSet s, BitSet w, BitSet e, BitSet nf, BitSet sf, BitSet wf, BitSet ef, String[] tileArr) {
            this.id = id;
            this.sideN = n; this.sideE = e; this.sideS = s; this.sideW = w;
            this.sideNF = nf; this.sideEF = ef; this.sideSF = sf; this.sideWF = wf;
        }

        @Override
        public boolean equals(Object obj) {
            return obj.getClass() == this.getClass() &&
                ((Tile)obj).id == this.id;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(this.id);
        }
    }
}
