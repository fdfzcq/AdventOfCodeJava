package aoc.y2020;

public class Day25 {
    final static long p = 20201227;

    public static void main(String[] args) {
        long cardLoop = findLoopSize(16616892);
        long doorLoop = findLoopSize(14505727);
        System.out.println(transform(cardLoop, 14505727) + " " + transform(doorLoop, 16616892));
    }

    static long findLoopSize(long pubKey) {
        long loop = 0L;
        long n = 1L;
        while (n != pubKey) {
            n = 7 * n % p;
            loop++;
        }
        return loop;
    }

    static long transform(long loop, long sub) {
        long n = 1L;
        while(loop > 0) {
            n = n * sub % p;
            loop--;
        }
        return n;
    }
}
