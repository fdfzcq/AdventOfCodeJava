package aoc.y2020;

import aoc.Input;

public class Day8 {
    public static void main(String[] args) {
        final String input = Input.inputFromFile(2020, 8);
        part2(input.split("\n"));
    }

    static void part1(String[] instructions) {
        int acc = 0;
        boolean[] visited = new boolean[instructions.length];
        System.out.println(executeInstructions(acc, instructions, 0, visited));
    }

    static int executeInstructions(int acc, String[] instructions, int index, boolean[] visited){
        if(visited[index]) { return acc; }
        visited[index] = true;
        String instruction = instructions[index];
        if(instruction.contains("nop")){ return executeInstructions(acc, instructions, index + 1, visited); }
        String[] inst = instruction.split(" ");
        String op = inst[0];
        int value = Integer.valueOf(inst[1]);
        if(op.equals("acc")) {
            return executeInstructions(acc + value, instructions, index + 1, visited);
        }
        if(op.equals("jmp")) {
            return executeInstructions(acc, instructions, index + value, visited);
        }
        return 0;
    }

    static void part2(String[] instructions) {
        boolean[] changed = new boolean[instructions.length];
        int res = -1;
        while(res == -1) {
            boolean[] visited = new boolean[instructions.length];
            res = executeInstructions2(0, instructions, 0, visited, changed, false);
        }
        System.out.println(res);
    }

    static int executeInstructions2(int acc, String[] instructions, int index, boolean[] visited, boolean[] changed, boolean modified){
        if(index >= instructions.length) { return acc; }
        if(visited[index]) { return -1; }
        visited[index] = true;
        String instruction = instructions[index];
        String[] inst = instruction.split(" ");
        String op = inst[0];
        int value = Integer.valueOf(inst[1]);
        if(op.equals("nop")){
            if(!modified && changed[index] == false){
                changed[index] = true;
                return executeInstructions2(acc, instructions, index + value, visited, changed, true);
            }
            return executeInstructions2(acc, instructions, index + 1, visited, changed, modified); }
        if(op.equals("acc")) {
            return executeInstructions2(acc + value, instructions, index + 1, visited, changed, modified);
        }
        if(op.equals("jmp")) {
            if(!modified && changed[index] == false){
                changed[index] = true;
                return executeInstructions2(acc, instructions, index + 1, visited, changed, true);
            }
            return executeInstructions2(acc, instructions, index + value, visited, changed, modified);
        }
        return 0;
    }
}
