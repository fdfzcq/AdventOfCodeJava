package aoc.y2020;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import aoc.Input;

public class Day1 {
    public static void main(String[] args) {
        final String input = Input.inputFromFile(2020, 1);
        List<Integer> numbers = Arrays.asList(input.split("\n", -1))
                                      .stream()
                                      .map(Integer::valueOf)
                                      .collect(Collectors.toList());
        Collections.sort(numbers);
        part2(numbers);
    }

    static void part1(List<Integer> numbers) {
        for(int n: numbers) {
            int m = 2020 - n;
            int mIndex = Collections.binarySearch(numbers, m);
            if(mIndex >= 0) {
                m = numbers.get(mIndex);
                System.out.println(m * n);
                System.exit(0);
            }
        }
    }

    static void part2(List<Integer> numbers) {
        for(int i = 0; i < numbers.size(); i ++) {
            int n = numbers.get(i);
            int twoSum = 2020 - n;
            for(int j = 0; numbers.get(j) <= twoSum; j ++) {
                if (j != i) {
                    int m = numbers.get(j);
                    int t = 2020 - n - m;
                    int tIndex = Collections.binarySearch(numbers, t);
                    if (tIndex >= 0) {
                        System.out.println(m * n * t);
                        System.exit(0);
                    }
                }
            }
        }
    }
}
