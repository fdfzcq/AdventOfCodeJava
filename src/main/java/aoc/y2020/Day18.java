package aoc.y2020;

import java.util.ArrayList;
import java.util.List;

import aoc.Input;

public class Day18 {
    public static void main(String[] args) {
        final String input = Input.inputFromFile(2020, 18);
        part2(input.split("\n"));
    }

    static void part1(String[] lines) {
        int[] pointer = new int[1]; // acc[0] is pointer
        long sum = 0;
        for(String line: lines) {
            pointer[0] = 0;
            sum += calc(line, pointer, -1L);
        }
        System.out.println(sum);
    }

    static long calc(String line, int[] pointerArr, long sum) {
        char op = '0';
        while(true) {
            int pointer = pointerArr[0];
            if(pointer >= line.length()){
                break;
            }
            char c = line.charAt(pointer);
            if (c == ' '){
                pointer++;
            }
            else if (c == '+' || c == '*'){
                op = c;
                pointer++;
            }
            else if (c == '(') {
                pointerArr[0] = pointer + 1;
                long sub = calc(line, pointerArr, -1L);
                sum = sum == -1L ? sub : doCalc(op, sum, sub);
                pointer = pointerArr[0] + 1;
            }
            else if (c == ')') {
                break;
            }
            else {
                int n = c - '0';
                sum = sum == -1L ? n : doCalc(op, sum, n);
                pointer ++;
            }
            pointerArr[0] = pointer;
        }
        return sum;
    }

    static long doCalc(char op, long sum, long sub) {
        if(op == '+'){
            return sum + sub;
        } else {
            return sum * sub;
        }
    }

    static void part2(String[] lines) {
        int[] pointer = new int[1]; // acc[0] is pointer
        long sum = 0;
        for(String line: lines) {
            pointer[0] = 0;
            sum += calc2(line, pointer, 0L);
        }
        System.out.println(sum);
    }

    static long calc2(String line, int[] pointerArr, long sum) {
        char op = '+';
        List<Long> nums = new ArrayList<>();
        while(true) {
            int pointer = pointerArr[0];
            if(pointer >= line.length()){
                break;
            }
            char c = line.charAt(pointer);
            if (c == ' '){
                pointer++;
            }
            else if (c == '+' || c == '*'){
                op = c;
                pointer++;
            }
            else if (c == '(') {
                pointerArr[0] = pointer + 1;
                long sub = calc2(line, pointerArr, 0L);
                if(op == '+') {
                    sum = sum + sub;
                } else {
                    nums.add(sum);
                    sum = (long)sub;
                }
                pointer = pointerArr[0] + 1;
            }
            else if (c == ')') {
                break;
            }
            else {
                int n = c - '0';
                if(op == '+') {
                    sum = sum + n;
                } else {
                    nums.add(sum);
                    sum = (long)n;
                }
                pointer ++;
            }
            pointerArr[0] = pointer;
        }
        long s = 1L;
        for(long l:nums) { s = s * l; }
        return s * sum;
    }
}
