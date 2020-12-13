package aoc.y2020;

import java.util.HashMap;
import java.util.Set;

import aoc.Input;

public class Day13 {
    public static void main(String[] args) {
        final String input = Input.inputFromFile(2020, 13);
        part2(input.split("\n"));
    }

    static void part1(String[] lines){
        long startTime = Long.valueOf(lines[0]);
        String[] buses = lines[1].split(",");
        long min = Integer.MAX_VALUE;
        int minBus = 0;
        for(String bus : buses) {
            if(!bus.equals("x")){
                int b = Integer.valueOf(bus);
                long diff = ((startTime / b) + 1) * b - startTime;
                if(diff < min) {
                    min = diff;
                    minBus = b;
                }
            }
        }
        System.out.println(min * minBus);
    }

    static void part2(String[] lines) {
        String[] scheduleStr = lines[1].split(",");
        HashMap<Long, Integer> schedule = new HashMap<Long, Integer>();
        long maxBusNumber = Integer.MIN_VALUE;
        for(int i = 0; i < scheduleStr.length; i++) {
            long bus = scheduleStr[i].equals("x") ? -1 : Integer.valueOf(scheduleStr[i]);
            if(bus != -1) { schedule.put(bus, i); };
            if(bus > maxBusNumber) {
                maxBusNumber = bus;
            }
        }
        long t = 0;
        long m = 1;
        while(true){
            t += m;
            for(long bus : Set.copyOf(schedule.keySet())){
                System.out.println(t + " " + m + " " + bus);
                if((t + schedule.get(bus)) % bus == 0){
                    m = m * bus;
                    schedule.remove(bus);
                    continue;
                } else {
                    break;
                }
            }
            if(schedule.isEmpty()) {
                System.out.println(t);
                System.exit(0);
            }
        }
    }
}
