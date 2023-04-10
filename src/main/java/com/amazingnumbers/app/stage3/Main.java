package com.amazingnumbers.app.stage3;

import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private long num;
    private String strNum;
    private Map<String, Boolean> boolMap;

    public static void main(String[] args) {
        printIntro();
        while (true) {
            if (run() == -1L) {
                System.out.println("Goodbye!");
                break;
            }
        }
    }

    public static int run() {
        Main app = new Main(getInput());
        if (app.num == 0L) {
            // Terminate app
            return -1;
        } else if (app.num == -1L) {
            // Don't evaluate invalid numbers
            return 1;
        }
        app.evaluateNum();
        app.printPropertiesOfNum();
        return 1;
    }

    public static void printIntro() {
        System.out.println("""
                Welcome to Amazing Numbers!
                                
                Supported requests:
                - enter a natural number to know its properties;
                - enter 0 to exit.
                """);
    }

    private Main(long _num) {
        num = _num;
        strNum = addCommasToLong(_num);
        boolMap = new LinkedHashMap<>();
        boolMap.put("even", false);
        boolMap.put("odd", false);
        boolMap.put("buzz", false);
        boolMap.put("duck", false);
        boolMap.put("palindromic", false);
    }

    public String addCommasToLong(long n) {
        // Convert the number to a string
        String numberStr = String.valueOf(n);

        // Reverse the string so we can process it from right to left
        String reversedStr = new StringBuilder(numberStr).reverse().toString();

        // Add commas to the string every 3 digits
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < reversedStr.length(); i++) {
            if (i % 3 == 0 && i > 0) {
                result.append(",");
            }
            result.append(reversedStr.charAt(i));
        }

        // Reverse the result string again to get the original order
        return result.reverse().toString();
    }

    private static long getInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a request: ");
        try {
            long input = scanner.nextLong();
            if (input == 0L) {
                // Terminate app
                return input;
            } else if (!isNaturalNumber(input)) {
                throw new InputMismatchException();
            }
            return input;
        } catch (InputMismatchException e) {
            System.out.print("\n");
            System.out.println("The first parameter should be a natural number or zero.");
            return -1;
        } finally {
            System.out.print("\n");
        }
    }

    public static boolean isNaturalNumber(long num) {
        return num > 0 && num % 1 == 0;
    }

    private void evaluateNum() {
        evaluateOddEven();
        evaluateBuzz();
        evaluateDuck();
        evaluatePalindrome();
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

    private void evaluatePalindrome() {
        String str = Long.toString(num);
        StringBuilder stringBuilder = new StringBuilder();
        boolean isPalindrome = true;
        for (int i = 0; i < str.length() / 2; i++) {
            char curr = str.charAt(i);
            char oppChar = str.charAt(str.length() - 1 - i);
            if (curr != oppChar) {
                isPalindrome = false;
            }
        }
        boolMap.put("palindromic", isPalindrome);
    }

    private void printPropertiesOfNum() {
        System.out.println(String.format("""
                Properties of %s
                        even: %b
                         odd: %b
                        buzz: %b
                        duck: %b
                 palindromic: %b
                """,
                strNum,
                boolMap.get("even"),
                boolMap.get("odd"),
                boolMap.get("buzz"),
                boolMap.get("duck"),
                boolMap.get("palindromic"))
        );
    }

}