package aoc.y2020;

import java.util.Arrays;

import aoc.Input;

public class Day1 {
    final int sum = 2020;
    public static void main(String[] args) {
        final String input = Input.inputFromFile(2020, 1);
        int[] numbers = Arrays.asList(input.split("\n"))
                              .stream()
                              .map(Integer::valueOf)
                              .mapToInt(i->i)
                             .toArray();
        Arrays.sort(numbers);;
        part2(numbers);
    }

    static void part1(int[] entries) {
        System.out.println(calcEntries(2, entries, 2020, 0));
    }

    static void part2(int[] entries) {
        System.out.println(calcEntries(3, entries, 2020, 0));
    }

    /**
    * Function to get products of given number of entries that sum up to a given value.
    * @param numOfEntries the number of entries left to be found.
    * @param entries array of given entries to search from.
    * @param sum the sum of entry set.
    * @param startIndex the current start index of search, indexes smaller than this value have already been searched.
    * @return the final product of entry set if entries were found, or 0 if no entry set meeting the criteria are found.
    */
    static int calcEntries(int numOfEntries, int[] entries, int sum, int startIndex) {
        if(numOfEntries < 1 || startIndex == entries.length || entries[startIndex] > sum){
            return 0;
        }
        if(numOfEntries == 1 && hasEntry(entries, sum, startIndex)){
            return sum;
        }
        int restProduct = calcEntries(numOfEntries - 1, entries, sum - entries[startIndex], startIndex + 1);
        if(restProduct != 0){
            return restProduct * entries[startIndex];
        } else {
            return calcEntries(numOfEntries, entries, sum, startIndex + 1);
        }
    }

    static boolean hasEntry(int[] entries, int sum, int startIndex) {
        return Arrays.binarySearch(entries, startIndex, entries.length - 1, sum) > 0;
    }
}
