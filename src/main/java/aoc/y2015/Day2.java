package aoc.y2015;

import aoc.Input;

public class Day2 {
    public static void main(String args[]) {
        String input = Input.inputFromFile(2);
        Present[] presents = parse_input_to_presents(input);
        int part1Res = part_2_result(presents);
        System.out.println(part1Res);
    }

    public static Present[] parse_input_to_presents(String input) {
        String[] presentStrings = input.split("\n");
        Present[] presents = new Present[presentStrings.length];
        for (int i = 0; i < presents.length; i ++) {
            String pString = presentStrings[i];
            String[] attributes = pString.split("x");
            int length = Integer.parseInt(attributes[0]);
            int width = Integer.parseInt(attributes[1]);
            int height = Integer.parseInt(attributes[2]);
            Present present = new Present(length, width, height);
            presents[i] = present;
        }
        return presents;
    }

    public static int part_1_result(Present[] presents) {
        int res = 0;
        for (int i = 0; i < presents.length; i ++){
            res += presents[i].surface_area();
        }
        return res;
    }

    public static int part_2_result(Present[] presents) {
        int res = 0;
        for (int i = 0; i < presents.length; i ++){
            res += presents[i].ribbon();
        }
        return res;
    }

    public static class Present {
        int length;
        int width;
        int height;

        public Present (int l, int w, int h) {
            this.length = l;
            this.width = w;
            this.height = h;
        }

        public int surface_area() {
            int s1 = this.length * this.width;
            int s2 = this.length * this.height;
            int s3 = this.width * this.height;
            int slack = Math.min(s1, Math.min(s2, s3));
            return s1 * 2 + s2 * 2 + s3 * 2 + slack;
        }

        public int ribbon() {
            int maxSide = Math.max(length, Math.max(width, height));
            return 2 * (length + width + height) - 2 * maxSide + length * width * height;
        }
    }
}