package aoc.y2020;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;

import com.google.common.base.Objects;

import aoc.Input;

public class Day22 {
    public static void main(String[] args) {
        final String input = Input.inputFromFile(2020, 22);
        String[] sections = input.split("\\n\\n");
        Deque<Integer> queue1 = createQueue(sections[0]);
        Deque<Integer> queue2 = createQueue(sections[1]);
        //part1(queue1, queue2);
        part2(queue1, queue2);
    }

    static void part1(Deque<Integer> queue1, Deque<Integer> queue2) {
        while(true) {
            int i1 = queue1.remove();
            int i2 = queue2.remove();
            if(i1 > i2) {
                queue1.offer(i1); queue1.offer(i2);
            } else {
                queue2.offer(i2); queue2.offer(i1);
            }
            if(queue1.isEmpty() || queue2.isEmpty()) {
                break;
            }
            System.out.println(i1 + " " + i2 + " " + queue1.size() + " " + queue2.size());
        }
        long sum = 0;
        Deque<Integer> queue = queue1.isEmpty() ? queue2 : queue1;
        for(int i = queue.size(); i >= 1; i--){
            sum += i * queue.pop();
        }
        System.out.println(sum);
    }

    static void part2(Deque<Integer> queue1, Deque<Integer> queue2) {
        long sum = 0;
        Deque<Integer> queue = game(queue1, queue2, 1) ? queue1 : queue2;
        for(int i = queue.size(); i >= 1; i--){
            sum += i * queue.pop();
        }
        System.out.println(sum);
    }

    // returns true if player1 wins
    static boolean game(Deque<Integer> queue1, Deque<Integer> queue2, int game) {
        HashSet<Integer> set1 = new HashSet<>();
        HashSet<Integer> set2 = new HashSet<>();
        while(true) {
            if(set1.contains(queue1.hashCode()) || set2.contains(queue2.hashCode())) { return true; }
            set1.add(queue1.hashCode());
            set1.add(queue2.hashCode());
            int i1 = queue1.remove();
            int i2 = queue2.remove();
            if(queue1.size() >= i1 && queue2.size() >= i2) {
                Deque<Integer> newQ1 = new LinkedList<>(queue1);
                while(newQ1.size() > i1) { newQ1.removeLast(); }
                Deque<Integer> newQ2 = new LinkedList<>(queue2); 
                while(newQ2.size() > i2) { newQ2.removeLast();}
                if(game(newQ1, newQ2, game + 1)) {
                    queue1.offer(i1); queue1.offer(i2);
                } else {
                    queue2.offer(i2); queue2.offer(i1);
                }
            } else {
                if(i1 > i2) {
                    queue1.offer(i1); queue1.offer(i2);
                } else {
                    queue2.offer(i2); queue2.offer(i1);
                }
            }
            if(queue1.isEmpty()) {
                return false;
            } else if (queue2.isEmpty()) {
                return true;
            }
        }
    }

    static long gameHashCode(Deque<Integer> queue1, Deque<Integer> queue2) {
        long qHash1 = queue1.hashCode();
        long qHash2 = queue2.hashCode();
        return Objects.hashCode(qHash1, qHash2);
    }

    static Deque<Integer> createQueue(String section) {
        Deque<Integer> queue = new LinkedList<Integer>();
        String[] arr = section.split("\n");
        for(int i = arr.length - 1; i >= 1; i--){
            queue.push(Integer.valueOf(arr[i]));
        }
        return queue;
    }
}
