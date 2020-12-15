package aoc.y2020;

import java.util.HashMap;

import aoc.Input;

public class Day14 {
    public static void main(String[] args) {
        final String input = Input.inputFromFile(2020, 14);
        getNumber(input.split(","), 30000000L);
    }

    static void getNumber(String[] startingNums, long n){
        long i = 1L;
        long number = 0L;
        HashMap<Long, Long> mem1 = new HashMap<Long, Long>();
        HashMap<Long, Long> mem2 = new HashMap<Long, Long>();
        for(String str : startingNums) {
            mem2.put(Long.valueOf(str), i);
            i = i + 1L;
            number = Long.valueOf(str);
        }
        while(i <= n){
            long currentNum;
            if(mem1.getOrDefault(number, 0L) == 0L){
                currentNum = 0L;
            } else {
                currentNum = (i - 1L) - mem1.get(number);
            }
            if(mem2.containsKey(currentNum)) {
                mem1.put(currentNum, mem2.get(currentNum));
                mem2.put(currentNum, i);
            } else {
                mem2.put(currentNum, i);
            }
            number = currentNum;
            i = i + 1L;
        }
        System.out.println(number);
    }
}
