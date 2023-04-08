package com.amazingnumbers.app.stage1;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private int num;
    public static void main(String[] args) {
        Main app = new Main(getInput());
        if (app.num == -1) return;
        app.evaluateNum();
    }

    private Main(int _num) {
        num = _num;
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
        evaluateBuzzNum();
    }

    private void evaluateOddEven() {
        if (num % 2 != 0) {
            System.out.println("This number is Odd.");
        } else {
            System.out.println("This number is Even.");
        }
    }

    private void evaluateBuzzNum() {
        String explanation;
        boolean isBuzzNum = true;

        if (num % 7 == 0 && num % 10 == 7) {
            explanation = String.format("%d is divisible by 7 and ends with 7.", num);
        } else if (num % 7 == 0) {
            explanation = String.format("%d is divisible by 7.", num);
        } else if (num % 10 == 7) {
            explanation = String.format("%d ends with 7.", num);
        } else {
            explanation = String.format("%d is neither divisible by 7 nor does it end with 7.", num);
            isBuzzNum = false;
        }

        if (isBuzzNum) {
            System.out.println("It is a Buzz number.");
        } else {
            System.out.println("It is not a Buzz number.");
        }

        System.out.println(String.format("""
                Explanation:
                %s
                """, explanation));
    }

}