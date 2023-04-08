package com.amazingnumbers.app.stage2;

import java.util.*;

public class Main {
    private int num;
    private Map<String, Boolean> boolMap;

    public static void main(String[] args) {
        Main app = new Main(getInput());
        if (app.num == -1) return;
        app.evaluateNum();
        app.printPropertiesOfNum();
    }

    private Main(int _num) {
        num = _num;
        boolMap = new LinkedHashMap<>();
        boolMap.put("even", false);
        boolMap.put("odd", false);
        boolMap.put("buzz", false);
        boolMap.put("duck", false);
    }

    private static int getInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a natural number:");
        try {
            int input = scanner.nextInt();
            if (!isNaturalNumber(input)) {
                throw new InputMismatchException();
            }
            return input;
        } catch (InputMismatchException e) {
            System.out.println("This number is not natural!");
        }
        return -1;
    }

    public static boolean isNaturalNumber(int num) {
        return num > 0 && num % 1 == 0;
    }

    private void evaluateNum() {
        evaluateOddEven();
        evaluateBuzz();
        evaluateDuck();
    }

    private void evaluateOddEven() {
        if (num % 2 == 0) {
            boolMap.put("even", true);
        } else {
            boolMap.put("odd", true);
        }
    }

    private void evaluateBuzz() {
        if (num % 7 == 0 || num % 10 == 7) {
            boolMap.put("buzz", true);
        }
    }

    private void evaluateDuck() {
        String strNum = String.valueOf(num);
        for (int i = 0; i < strNum.length(); i++) {
            // NOTE: If we find a number and it is not at 0th index, then it is a Duck Number!!
            if (i != 0 && strNum.charAt(i) == '0') {
                boolMap.put("duck", true);
            }
        }
    }

    private void printPropertiesOfNum() {
        System.out.println(String.format("""
                Properties of %d
                        even: %b
                         odd: %b
                        buzz: %b
                        duck: %b
                """,
                num,
                boolMap.get("even"),
                boolMap.get("odd"),
                boolMap.get("buzz"),
                boolMap.get("duck"))
        );
    }

}