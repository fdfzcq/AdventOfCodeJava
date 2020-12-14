package aoc.y2020;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.stream.Collectors;

import aoc.Input;

public class Day14 {
    public static void main(String[] args) {
        final String input = Input.inputFromFile(2020, 14);
        part2(input.split("\n"));
    }

    static void part1(String[] lines) {
        HashMap<Integer, BitSet> memMap = new HashMap<Integer, BitSet>();
        HashMap<Integer, Boolean> mask = new HashMap<Integer, Boolean>();
        for(String line : lines){
            if(line.contains("mask")){
                String maskStr = line.split(" = ")[1];
                mask = new HashMap<Integer, Boolean>();
                for(int i = 0; i < maskStr.length(); i++){
                    if(maskStr.charAt(i) != 'X'){
                        mask.put(maskStr.length() - 1 - i, maskStr.charAt(i) == '0' ? false : true);
                    }
                }
            } else {
                String[] instruction = line.split(" = ");
                int address = Integer.valueOf(instruction[0].replaceAll("[^0-9]", ""));
                long value = Long.valueOf(instruction[1]);
                BitSet bs = BitSet.valueOf(new long[]{value});
                for(int k : mask.keySet()){
                    bs.set(k, mask.get(k));
                }
                memMap.put(address, bs);
            }
        }
        Long sum = memMap.values().stream().map(
                            b -> b.stream()
                                  .takeWhile(i -> i < 36)
                                  .mapToLong(i -> 1L << i)
                                  .reduce(0, (m, n) -> m | n)
                            ).collect(Collectors.summingLong(Long::longValue));
        System.out.println(sum);
    }

    static void part2(String[] lines) {
        HashMap<Long, Long> memMap = new HashMap<Long, Long>();
        HashMap<Integer, Boolean> mask = new HashMap<Integer, Boolean>();
        for(String line : lines){
            if(line.contains("mask")){
                String maskStr = line.split(" = ")[1];
                mask = new HashMap<Integer, Boolean>();
                for(int i = 0; i < maskStr.length(); i++){
                    if(maskStr.charAt(i) != 'X'){
                        mask.put(maskStr.length() - 1 - i, maskStr.charAt(i) == '0' ? false : true);
                    }
                }
            } else {
                String[] instruction = line.split(" = ");
                long address = Long.valueOf(instruction[0].replaceAll("[^0-9]", ""));
                long value = Long.valueOf(instruction[1]);
                BitSet bs = BitSet.valueOf(new long[]{address});
                for(long possibleAddress : getAllPossibleAddress(bs, mask)){
                    memMap.put(possibleAddress, value);
                }
            }
        }
        Long sum = memMap.values().stream().collect(Collectors.summingLong(Long::longValue));
        System.out.println(sum);
    }

    static ArrayList<Long> getAllPossibleAddress(BitSet address, HashMap<Integer, Boolean> mask) {
        ArrayList<Long> addresses = new ArrayList<Long>();
        findPossibleAddresses(addresses, address, mask, 0, 0L);
        return addresses;
    }

    static void findPossibleAddresses(ArrayList<Long> addresses, BitSet address, HashMap<Integer, Boolean> mask, int idx, long base) {
        if(idx >= 36) { addresses.add(base); }
        else if(mask.containsKey(idx)) {
            long i = address.get(idx) || mask.get(idx) ? 1L : 0L;
            findPossibleAddresses(addresses, address, mask, idx + 1, base | i << idx);
        } else {
            findPossibleAddresses(addresses, address, mask, idx + 1, base);
            findPossibleAddresses(addresses, address, mask, idx + 1, base | (1L << idx));
        }
    }
}
