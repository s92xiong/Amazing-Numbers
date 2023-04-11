package com.amazingnumbers.app.stage4;

import java.util.*;

public class Main {
    private String[] strings;
    private long[] numbers;
    private boolean twoNaturalNumbers;

    private Main(String[] _stringInput) {
        strings = _stringInput;
        numbers = new long[_stringInput.length];
        twoNaturalNumbers = _stringInput.length > 1;
    }

    public static void main(String[] args) {
        printWelcome();
        printInstructions();
        while (true) {
            int result = run();
            if (result == -1) break;
        }
        System.out.println("Goodbye!");
    }

    private static int run() {
        Main app = new Main(getInput());

        if (app.strings[0].isEmpty()) {
            printInstructions();
        }

        // Terminate app
        if (!app.twoNaturalNumbers && app.strings[0].equals("0")) return -1;

        for (int i = 0; i < app.numbers.length; i++) {
            try {
                // Map the strings to long values
                long result = Long.parseLong(app.strings[i]);
                if (result < 0) {
                    throw new NumberFormatException();
                } else {
                    app.numbers[i] = result;
                }
            } catch (NumberFormatException e) {
                if (i == 0) {
                    System.out.println("The first parameter should be a natural number or zero.\n");
                } else if (i == 1) {
                    System.out.println("The second parameter should be a natural number.\n");
                }
                return 0;
            }
        }

        app.printProperties();
        System.out.print("\n");
        return 0;
    }

    private void printProperties() {
        // Get the first parameter of the input: starting number
        Long n = numbers[0];

        // Get the second parameter of the input: consecutive numbers
        long length = (twoNaturalNumbers) ? numbers[1] : 1L;

        for (int i = 0; i < length; i++) {
            // Store booleans into a LinkedHashMap since we need to filter for true values later
            long currentNum = n + i;
            Map<String, Boolean> boolMap = new LinkedHashMap<>();
            boolMap.put("buzz", getBuzz(currentNum));
            boolMap.put("duck", getDuck(currentNum));
            boolMap.put("palindromic", getPalindromic(currentNum));
            boolMap.put("gapful", getGapful(currentNum));
            boolMap.put("even", getEven(currentNum));
            boolMap.put("odd", !getEven(currentNum));

            if (!twoNaturalNumbers) {
                String strNum = addCommasToLong(currentNum);
                // When there is only 1 natural number, we print out all of the properties as normal
                System.out.println(String.format("""
                Properties of %s
                        buzz: %b
                        duck: %b
                 palindromic: %b
                      gapful: %b
                        even: %b
                         odd: %b""",
                        strNum,
                        boolMap.get("buzz"),
                        boolMap.get("duck"),
                        boolMap.get("palindromic"),
                        boolMap.get("gapful"),
                        boolMap.get("even"),
                        boolMap.get("odd")));
            } else {
                // When there are two natural numbers, we filter for true values
                StringBuilder strBuilder = new StringBuilder();
                String strNum = addCommasToLong(currentNum);
                strBuilder.append(String.format("%s is", strNum));
                for (Map.Entry<String, Boolean> entry : boolMap.entrySet()) {
                    // If the value is true, then we add it to the StringBuilder
                    if (entry.getValue()) {
                        strBuilder.append(String.format(" %s,", entry.getKey()));
                    }
                }
                String myString = strBuilder.toString();
                // Remove unwanted comma at end of the string
                myString = myString.substring(0, myString.length() - 1);
                System.out.println("\t\t\t" + myString);
            }
        }
    }

    private static String[] getInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a request: ");
        String input = scanner.nextLine();
        System.out.print("\n");
        return input.split(" ");
    }

    private static void printWelcome() {
        System.out.println("Welcome to Amazing Numbers!\n");
    }

    private static void printInstructions() {
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

    private boolean getBuzz(long n) {
        return (n % 7 == 0 || n % 10 == 7);
    }

    private boolean getDuck(long n) {
        String strNum = String.valueOf(n);
        for (int i = 0; i < strNum.length(); i++) {
            // NOTE: If we find a number and it is not at 0th index, then it is a Duck Number!!
            if (i != 0 && strNum.charAt(i) == '0') {
                return true;
            }
        }
        return false;
    }

    private boolean getPalindromic(long n) {
        String str = Long.toString(n);
        for (int i = 0; i < str.length() / 2; i++) {
            char curr = str.charAt(i);
            char oppChar = str.charAt(str.length() - 1 - i);
            if (curr != oppChar) {
                return false;
            }
        }
        return true;
    }

    private boolean getGapful(long n) {
        if (n < 100) return false;
        // Convert long into string, then examine the first and last characters of the string
        String str = Long.toString(n);
        char firstDigit = str.charAt(0);
        char lastDigit = str.charAt(str.length() - 1);

        // Concatenate the string and convert it back into a long
        String concatStr = new StringBuilder().append(firstDigit).append(lastDigit).toString();
        long longNum = Long.parseLong(concatStr);
        return (n % longNum == 0);
    }

    private boolean getEven(long n) {
        return (n % 2 == 0);
    }

    private String addCommasToLong(long n) {
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
}
