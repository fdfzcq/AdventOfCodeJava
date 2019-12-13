package aoc.y2015;

import aoc.Input;

public class Day1 {
    public static void main(String[] args) {
        String input = "";
        int result = processInput(input);

        System.out.println(result);
    }

    public static int processInput(String input) {
        char[] charList = input.toCharArray();
        int result = 0;
        for(int i = 0; i < charList.length; i++) {
            int value = charList[i] == '(' ? 1 : -1;
            result += value;
            if (result == -1) {
                return i + 1;
            }
        };
        return result;
    }
}