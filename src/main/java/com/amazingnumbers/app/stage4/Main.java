package com.amazingnumbers.app.stage4;

import java.util.*;

public class Main {
    private String[] strings;
    private List<Long> numbers;
    private boolean twoNaturalNumbers;

    public static void main(String[] args) {
        printWelcome();
        printInstructions();
        while (true) {
            int result = run();
            if (result == -1) break;
        }
        System.out.println("\nGoodbye!");
    }

    public static int run() {
        Main app = new Main(getInput());

        // Terminate app
        if (!app.twoNaturalNumbers && app.strings[0].equals("0")) return -1;

        List<Long> longNumbers;
        try {
            longNumbers = app.convertStringsToLongs(app.strings);
        } catch (NumberFormatException e) {
            System.out.println("\nThe first parameter should be a natural number or zero.");
            return 0;
        } finally {
            System.out.print("\n");
        }

        app.setNumbers(longNumbers);
        if (app.twoNaturalNumbers) {
            // Call a function to handle logic for handling TWO natural numbers
        } else {
            // Call an instance method to handle logic for printing properties of ONE natural number
            // This will require us to examine previous code form stage3 and apply it here
            app.printProperties();
        }

        return 0;
    }

    private void printProperties() {
        // Examine the number, then dish out buzz, duck, palindromic, gapful, even, odd
        // We'll probably have to create new methods that will give us buzz, duck, palindromic, gapful, even, and odd

//        Long n = numbers.get(0);
//        boolean buzz = getBuzz(n);
//        boolean duck = getDuck(n);
//        boolean palindromic = getPalindromic(n);
//        boolean gapful = getGapful(n);
//        boolean even = getEven(n);
//        boolean odd = !even;
    }

    private Main(String[] _stringInput) {
        strings = _stringInput;
        twoNaturalNumbers = _stringInput.length > 1;
    }

    private void setNumbers(List<Long> numbers) {
        this.numbers = numbers;
    }

    public List<Long> convertStringsToLongs(String[] stringInput) {
        List<Long> numbers = new ArrayList<>();
        for (String numberString : stringInput) {
            long number = Long.parseLong(numberString);
            numbers.add(number);
        }
        return numbers;
    }

    private static String[] getInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a request: ");
        String input = scanner.nextLine();
        return input.split(" ");
    }

    public static void printWelcome() {
        System.out.println("Welcome to Amazing Numbers!");
    }

    public static void printInstructions() {
        System.out.println("""
                
                Supported requests:
                - enter a natural number to know its properties;
                - enter two natural numbers to obtain the properties of the list:
                  * the first parameter represents a starting number;
                  * the second parameter shows how many consecutive numbers are to be printed;
                - separate the parameters with one space;
                - enter 0 to exit.
                """);
    }
}
