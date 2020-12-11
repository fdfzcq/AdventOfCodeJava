package aoc.y2020;

import java.util.LinkedList;
import java.util.Queue;

import aoc.Input;

public class Day9 {
    public static void main(String[] args) {
        final String input = Input.inputFromFile(2020, 9);
        part2(input.split("\n"), 127);
    }

    static void part1(String[] lines) {
        Queue<Long> numbers = new LinkedList<Long>();
        int i = 0;
        for(String line : lines){
            long n = Long.valueOf(line);
            if(i >= 25) {
                boolean valid = false;
                for(long a : numbers) {
                    if(numbers.contains(n - a)) {
                        valid = true;
                        break;
                    }
                }
                if(!valid) {
                    System.out.println(n);
                    break;
                }
                numbers.remove();
                numbers.add(n);
            } else {
                numbers.add(n);
            }
            i++;
        }
    }

    static void part2(String[] lines, long sum) {
        long[] allNumbers = new long[lines.length];
        for(int i = 0; i < lines.length; i++) {
            allNumbers[i] = Long.valueOf(lines[i]);
        }
        findEncryptionWeakness(allNumbers);
    }

    static void findEncryptionWeakness(long[] allNumbers){
        int sum = 57195069;
        int start = 0;
        int end = 1;
        long acc = allNumbers[0] + allNumbers[1];
        while(true) {
            if(acc == sum) {
                long min = Long.MAX_VALUE;
                long max = Long.MIN_VALUE;
                for(int i = start; i <= end; i++){
                    min = Math.min(min, allNumbers[i]);
                    max = Math.max(max, allNumbers[i]);
                }
                System.out.println(min + max);
                break;
            }
            if (acc < sum) {
                end ++;
                acc += allNumbers[end + 1];
            }
            if (acc > sum) {
                start ++;
                end = start + 1;
                acc = allNumbers[start + 1] + allNumbers[start + 2];
            }
        }
    }

    static void calcResult(Queue<Long> numbers) {
        long min = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;
        for(long n : numbers) {
            min = Math.min(min, n);
            max = Math.max(max, n);
        }
        System.out.println(min + " " + max + " " + (min + max));
    }
}
