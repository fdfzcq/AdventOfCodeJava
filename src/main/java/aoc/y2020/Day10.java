package aoc.y2020;

import java.util.Arrays;

import aoc.Input;

public class Day10 {
    public static void main(String[] args) {
        final String input = Input.inputFromFile(2020, 10);
        int[] numbers = Arrays.asList(input.split("\n"))
                        .stream()
                        .map(Integer::valueOf)
                        .mapToInt(i->i)
                        .toArray();
        part2(numbers);
    }

    static void part1(int[] numbers) {
        Arrays.sort(numbers);
        int num = 0;
        int numOfOnes = 0;
        int numOfThrees = 1;
        for(int i = 0; i < numbers.length; i++){
            int diff = numbers[i] - num;
            numOfOnes = diff == 1 ? numOfOnes + 1: numOfOnes;
            numOfThrees = diff == 3 ? numOfThrees + 1: numOfThrees;
            num = numbers[i];
        }
        System.out.println(numOfOnes * numOfThrees);
    }

    static void part2(int[] numbers) {
        Arrays.sort(numbers);
        long[] mem = new long[numbers.length];
        System.out.println(calcArrangements(numbers, mem, numbers.length - 1));
    }

    static long calcArrangements(int[] nums, long[] mem, int index) {
        if(mem[index] != 0) { return mem[index]; }
        long i1 = index > 0 ? calcArrangements(nums, mem, index - 1) : 1;
        long i2 = index > 1 ? (nums[index] - nums[index - 2] <= 3 ? calcArrangements(nums, mem, index - 2) : 0L) : (index == 1 && nums[index] <= 3 ? 1 : 0);
        long i3 = index > 2 ? (nums[index] - nums[index - 3] <= 3 ? calcArrangements(nums, mem, index - 3) : 0L) : (index == 2 && nums[index] <= 3 ? 1 : 0);
        mem[index] = i1 + i2 + i3;
        return i1 + i2 + i3;
    }
}
