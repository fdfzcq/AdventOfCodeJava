package aoc.y2020;

import java.util.Arrays;

import aoc.Input;

public class Day4 {
    static String[] mandatoryFields = {"byr:", "iyr:", "eyr:", "hgt:", "hcl:", "ecl:", "pid:"};

    public static void main(String[] args) {
        final String input = Input.inputFromFile(2020, 4);
        part2(input.split("\\n\\n"));
    }

    static void part1(String[] lines) {
        int valid = 0;
        for(String line : lines) {
            boolean isValid = true;
            for(String mandatoryField : mandatoryFields) {
                isValid = isValid && line.contains(mandatoryField);
            }
            valid = isValid ? valid + 1: valid;
        }
        System.out.println(valid);
    }

    static void part2(String[] lines) {
        int valid = 0;
        for(String line : lines) {
            boolean isValid = true;
            for(String mandatoryField : mandatoryFields) {
                isValid = isValid && line.contains(mandatoryField);
            }
            if(isValid) {
                Passport pass = new Passport();
                for(String keyValue : line.split("\\s|\\n")) {
                    String[] keyNValue = keyValue.split(":");
                    String key = keyNValue[0];
                    String value = keyNValue[1];
                    if(key.equals("byr")) {
                        pass.setBYR(value);
                    }
                    if(key.equals("iyr")) {
                        pass.setIYR(value);
                    }
                    if(key.equals("eyr")) {
                        pass.setEYR(value);
                    }
                    if(key.equals("hgt")) {
                        pass.setHGT(value);
                    }
                    if(key.equals("hcl")) {
                        pass.setHCL(value);
                    }
                    if(key.equals("ecl")) {
                        pass.setECL(value);
                    }
                    if(key.equals("pid")) {
                        pass.setPID(value);
                    }
                }
                if(pass.isValid()){
                    valid ++;
                }
            }
        }
        System.out.println(valid);
    }

    public static class Passport {
        int byr;
        int iyr;
        int eyr;
        String hgt;
        String hcl;
        String ecl;
        String pid;

        Passport() {
        }

        void setBYR(String byr) {
            this.byr = Integer.valueOf(byr);
        }

        void setIYR(String iyr) {
            this.iyr = Integer.valueOf(iyr);
        }

        void setEYR(String eyr) {
            this.eyr = Integer.valueOf(eyr);
        }

        void setHGT(String hgt) {
            System.out.println(hgt);
            this.hgt = hgt;
        }

        void setHCL(String hcl) {
            this.hcl = hcl;
        }

        void setECL(String ecl) {
            this.ecl = ecl;
        }

        void setPID(String pid) {
            this.pid = pid;
        }

        boolean isValid() {
            return this.validBYR() && this.validIYR() && this.validEYR() &&
                this.validHGT() && this.validHCL() &&
                this.validECL() && this.validPID();
        }

        private boolean validBYR() {
            return this.byr >= 1920 && this.byr <= 2002;
        }

        private boolean validIYR() {
            return this.iyr >= 2010 && this.iyr <= 2020;
        }

        private boolean validEYR() {
            return this.eyr >= 2020 && this.eyr <= 2030;
        }

        private boolean validHGT() {
            System.out.println(this.hgt);
            if(this.hgt.contains("cm")) {
                String numStr = this.hgt.replaceAll("[^0-9]", "");
                int height = Integer.valueOf(numStr);
                return height >= 150 && height <= 193;
            }
            if(this.hgt.contains("in")) {
                String numStr = this.hgt.replaceAll("[^0-9]", "");
                int height = Integer.valueOf(numStr);
                return height >= 59 && height <= 76;
            }
            return false;
        }

        private boolean validHCL() {
            return this.hcl.matches("^#[0-9a-f]{6}$");
        }

        private boolean validECL() {
            String[] validECLs = {"amb", "blu", "brn", "gry", "grn", "hzl", "oth"};
            return Arrays.asList(validECLs).contains(this.ecl);
        }

        private boolean validPID() {
            return this.pid.matches("^[0-9]{9}$");
        }
    }
}
