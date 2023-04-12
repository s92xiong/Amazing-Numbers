package com.amazingnumbers.app.stage6;

import java.util.*;

public class Main {
    private String[] strings; // String input split at " " when instantiating the Main class
    private final long[] numbersInput; // Includes parameter 1 & 2 Mapped from strings[]
    private boolean twoNaturalNumbers;
    private final String[] properties;

    public static void main(String[] args) {
        printWelcome();
        printInstructions();
        while (true) {
            int result = run();
            if (result == -1) break;
        }
        System.out.println("Goodbye!");
    }

    private Main(String[] _stringInput) {
        strings = _stringInput;
        if (_stringInput.length == 4) {
            numbersInput = new long[_stringInput.length - 2];
        } else if (_stringInput.length == 3) {
            numbersInput = new long[_stringInput.length - 1];
        } else {
            numbersInput = new long[_stringInput.length];
        }
        twoNaturalNumbers = _stringInput.length > 1;
        properties = new String[]{
                "BUZZ", "DUCK", "PALINDROMIC", "GAPFUL", "SPY", "SQUARE", "SUNNY", "EVEN", "ODD"
        };
    }

    private static int run() {
        Main app = new Main(getInput());

        // User incorrectly inputs an empty line
        if (app.strings[0].isEmpty()) printInstructions();

        // User inputs an invalid property name (e.g. jojo)
        if (app.strings.length == 3) {
            String property = app.strings[2].toUpperCase();
            boolean propertyFound = Arrays.asList(app.properties).contains(property);
            if (!propertyFound) {
                printIncorrectProperty(property);
                return 0;
            }
        } else if (app.strings.length == 4) {
            // User inputs an invalid multiple or single invalid property names (e.g. jojo drake)
            String property1 = app.strings[2].toUpperCase();
            String property2 = app.strings[3].toUpperCase();
            boolean property1Found = Arrays.asList(app.properties).contains(property1);
            boolean property2Found = Arrays.asList(app.properties).contains(property2);

            if (!property1Found && !property2Found) {
                printIncorrectProperties(property1, property2);
                return 0;
            } else if (!property1Found) {
                printIncorrectProperty(property1);
                return 0;
            } else if (!property2Found) {
                printIncorrectProperty(property2);
                return 0;
            }
        }

        // Terminate app when user inputs "0"
        if (!app.twoNaturalNumbers && app.strings[0].equals("0")) return -1;

        // Map the first two numerical string values in strings[] to long[] numbersInput
        for (int i = 0; i < app.numbersInput.length; i++) {
            if (i == app.numbersInput.length) break;
            try {
                long result = Long.parseLong(app.strings[i]);
                if (result < 0) {
                    throw new NumberFormatException();
                } else {
                    app.numbersInput[i] = result;
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
        long n = numbersInput[0];

        // Get the second parameter of the input: consecutive numbers
        long length = (twoNaturalNumbers) ? numbersInput[1] : 1L;

        if (twoNaturalNumbers && strings.length == 4) {
            System.out.println("Print out logic here for when we have four parameters");
        } else if (twoNaturalNumbers && strings.length == 3) {
            // Add logic to process 3rd user request
            printThirdOption(n, length, strings[2]);
        } else {
            printFirstSecondOption(n, length);
        }
    }

    private void printFirstSecondOption(long n, long length) {
        for (int i = 0; i < length; i++) {
            // Store booleans into a LinkedHashMap since we need to filter for true values later
            long currentNum = n + i;
            Map<String, Boolean> boolMap = getBooleanMap(currentNum);

            if (!twoNaturalNumbers) {
                String strNum = addCommasToLong(currentNum);
                // When there is only 1 natural number, we print out all of the properties as normal
                System.out.printf("""
                                Properties of %s
                                        buzz: %b
                                        duck: %b
                                 palindromic: %b
                                      gapful: %b
                                         spy: %b
                                      square: %b
                                       sunny: %b
                                        even: %b
                                         odd: %b%n""",
                                strNum,
                                boolMap.get("buzz"),
                                boolMap.get("duck"),
                                boolMap.get("palindromic"),
                                boolMap.get("gapful"),
                                boolMap.get("spy"),
                                boolMap.get("square"),
                                boolMap.get("sunny"),
                                boolMap.get("even"),
                                boolMap.get("odd"));
            } else {
                System.out.println("\t\t\t" + buildNumberStatement(currentNum, boolMap));
            }
        }
    }

    private void printThirdOption(long n, long length, String propertyInput) {
        int i = 0;
        int currentNum = (int) n;

        StringBuilder stringBuilder = new StringBuilder();

        while (i < length) {
            Map<String, Boolean> boolMap = getBooleanMap(currentNum);

            boolean propertyIsTrue = boolMap.get(propertyInput.toLowerCase());

            if (propertyIsTrue) {
                String tmpStr = buildNumberStatement(currentNum, boolMap);
                stringBuilder.append(tmpStr);
                if (i + 1 != length) {
                    stringBuilder.append("\n");
                }
                i++;
            }
            currentNum++;
        }
        System.out.println(stringBuilder);
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
        - two natural numbers and a property to search for;
        - separate the parameters with one space;
        - enter 0 to exit.
        """);
    }

    private static void printIncorrectProperty(String propertyInput) {
        System.out.printf("""
                The property [%s] is wrong.
                Available properties: [EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY]
                %n""", propertyInput);
    }

    private static void printIncorrectProperties(String propertyInput1, String propertyInput2) {
        System.out.printf("""
                The property [%s, %s] are wrong.
                Available properties: [EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY]
                %n""", propertyInput1, propertyInput2);
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
        String concatStr = String.valueOf(firstDigit) + lastDigit;
        long longNum = Long.parseLong(concatStr);
        return (n % longNum == 0);
    }

    private boolean getEven(long n) {
        return (n % 2 == 0);
    }

    private boolean getSpy(long n) {
        String numStr = String.valueOf(n);
        long sum = 0;
        long product = 1L;

        for (int i = 0; i < numStr.length(); i++) {
            long digit = Character.getNumericValue(numStr.charAt(i));
            sum += digit;
            product *= digit;
        }
        return (sum == product);
    }

    private boolean getSquare(long n) {
        double sqrt = Math.sqrt(n);
        return sqrt == (int) sqrt;
    }

    private boolean getSunny(long n) {
        long nextConsecutive = n + 1L;
        return getSquare(nextConsecutive);
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

    private Map<String, Boolean> getBooleanMap(long num) {
        Map<String, Boolean> boolMap = new LinkedHashMap<>();
        boolMap.put("buzz", getBuzz(num));
        boolMap.put("duck", getDuck(num));
        boolMap.put("palindromic", getPalindromic(num));
        boolMap.put("gapful", getGapful(num));
        boolMap.put("spy", getSpy(num));
        boolMap.put("square", getSquare(num));
        boolMap.put("sunny", getSunny(num));
        boolMap.put("even", getEven(num));
        boolMap.put("odd", !getEven(num));
        return boolMap;
    }

    private String buildNumberStatement(long num, Map<String, Boolean> map) {
        StringBuilder strBuilder = new StringBuilder();
        String strNum = addCommasToLong(num);
        strBuilder.append(String.format("%s is", strNum));
        for (Map.Entry<String, Boolean> entry : map.entrySet()) {
            // If the value is true, then we add it to the StringBuilder
            if (entry.getValue()) {
                strBuilder.append(String.format(" %s,", entry.getKey()));
            }
        }
        String tmpStr = strBuilder.toString();
        // Remove unwanted comma at end of the string
        return tmpStr.substring(0, tmpStr.length() - 1);
    }

}
