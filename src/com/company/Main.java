package com.company;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int[] xBorder = new int[2];
        int[] yBorder = new int[2];

        String input = scanner.nextLine();
        String[] xBorderString = scanner.nextLine().split(" ");
        String[] yBorderString = scanner.nextLine().split(" ");

        for (int i = 0; i < 2; i++) {
            xBorder[i] = Integer.parseInt(xBorderString[i]);
            yBorder[i] = Integer.parseInt(yBorderString[i]);
        }

        String[] splitedInput = splitInput(input);
        ArrayList<Poly>[] parsedInput = parseInput(splitedInput);
        draw(parsedInput, xBorder[0], xBorder[1], yBorder[0], yBorder[1]);
    }

    public static String[] splitInput(String input) {

        String[] splitedInput = input.split("\\(");
        for (int i = 1; i < splitedInput.length; i++) {
            StringBuilder sb = new StringBuilder(splitedInput[i]);
            sb.deleteCharAt(splitedInput[i].length() - 1);
            splitedInput[i] = sb.toString();
        }
        return splitedInput;
    }

    public static ArrayList<Poly>[] parseInput(String[] input) {

        ArrayList<String>[] temp = new ArrayList[input.length];

        temp[0] = new ArrayList<>();
        for (int i = 1; i < input.length; i++) {
            int start = 0;
            int end = -1;
            int flag = 1;
            temp[i] = new ArrayList();

            if (input[i].charAt(0) == '-' || input[i].charAt(0) == '+') {
                start = 0;
                flag = 1;
            }

            for (int j = 1; j < input[i].length(); j++) {
                if (input[i].charAt(j) == '+' || input[i].charAt(j) == '-') {
                    if (flag == 0) {
                        flag = 1;
                        start = j;
                    } else {
                        flag = 0;
                        end = j;
                        temp[i].add(input[i].substring(start, end));
                        start = j;
                        j--;
                    }
                }

            }
            if (flag == 1) {
                end = input[i].length();
                temp[i].add(input[i].substring(start, end));
            }
        }

        String[] x = new String[2];
        String[] pow = new String[2];
        ArrayList<Poly>[] output = new ArrayList[input.length];

        for (int i = 1; i < temp.length; i++) {
            output[i] = new ArrayList<>();
            /*for (int j = 0; j < 1000; j++) {
                output[i].add(0);
            }*/
            for (int j = 0; j < temp[i].size(); j++) {
                if (temp[i].get(j).contains("x")) {
                    x = temp[i].get(j).split("x");
                    if (x.length == 0) {
                        output[i].add(new Poly(1, 1));
                    } else if (x.length != 1) {
                        pow = x[1].split("\\^");
                        if (Objects.equals(x[0], "") || Objects.equals(x[0], "+")) {
                            x[0] = "1";
                        } else if (Objects.equals(x[0], "-")) {
                            x[0] = "-1";
                        }
                        output[i].add(new Poly(Integer.parseInt(pow[1]), Integer.parseInt(x[0])));
                    } else {
                        if (Objects.equals(x[0], "") || Objects.equals(x[0], "+")) {
                            x[0] = "1";
                        } else if (Objects.equals(x[0], "-")) {
                            x[0] = "-1";
                        }
                        output[i].add(new Poly(1, Integer.parseInt(x[0])));
                    }

                } else {
                    output[i].add(new Poly(0, Integer.parseInt(temp[i].get(j))));
                }
            }
        }
        return output;
    }

    public static float calculate(ArrayList<Poly>[] parsedInput, float x) {
        float[] sum = new float[parsedInput.length];
        float ans = 1;
        for (int i = 0; i < parsedInput.length; i++) {
            sum[i] = 0;
        }

        for (int i = 1; i < parsedInput.length; i++) {
            for (int j = 0; j < parsedInput[i].size(); j++) {
                sum[i] += parsedInput[i].get(j).multiplyer *
                        Math.pow(x, parsedInput[i].get(j).exp);
            }
        }

        for (int i = 1; i < sum.length; i++) {
            if (sum[i] == 0) {
                return 0.0F;
            } else {
                ans *= sum[i];
            }
        }
        return ans;
    }

    public static void draw(ArrayList<Poly>[] parsedInput, int xLow, int xHigh, int yLow, int yHigh) {
        for (int j = yHigh; j >= yLow; j--) {
            for (int i = xLow; i <= xHigh; i++) {
                if (calculate(parsedInput, i) == j) {
                    System.out.printf("*");
                } else if ((i == 0) && (j == 0)) {
                    System.out.printf("_");
                } else if (j == 0) {
                    System.out.printf("_");
                } else if (i == 0) {
                    System.out.printf("|");
                } else System.out.printf(" ");
            }
            System.out.println();
        }
    }

    public static class Poly {

        public int multiplyer;
        public int exp;

        public Poly(int exp, int multiplier) {
            this.multiplyer = multiplier;
            this.exp = exp;
        }

        public Poly() {
            this.multiplyer = 0;
            this.exp = 0;
        }

        public int getMultiplyer() {
            return multiplyer;
        }

        public void setMultiplyer(int multiplyer) {
            this.multiplyer = multiplyer;
        }

        public int getExp() {
            return exp;
        }

        public void setExp(int exp) {
            this.exp = exp;
        }
    }
}
