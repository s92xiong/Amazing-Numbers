package com.amazingnumbers.app.stage7;

import java.util.*;

enum Property {
    BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING, EVEN, ODD
}

public class Main {
    private String[] inputs; // String input split at " " when instantiating the Main class
    private long startingNum;
    private long consecutiveNum;

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
        inputs = _stringInput;
    }

    private static int run() {
        Main app = new Main(getInput());

        // Terminate app when user inputs "0"
        if (app.inputs[0].equals("0")) return -1;

        // User incorrectly inputs an empty line
        if (app.inputs[0].isEmpty()) {
            printInstructions();
            return 0;
        }

        // Update startingNum
        if (app.inputs.length >= 1) {
            try {
                long result = Long.parseLong(app.inputs[0]);
                if (result < 0) {
                    throw new NumberFormatException();
                } else {
                    app.startingNum = result;
                }
            } catch (NumberFormatException e) {
                System.out.println("The first parameter should be a natural number or zero.\n");
                return 0;
            }
        }

        // Update consecutiveNum
        if (app.inputs.length >= 2) {
            try {
                long result = Long.parseLong(app.inputs[1]);
                if (result < 0) {
                    throw new NumberFormatException();
                } else {
                    app.consecutiveNum = result;
                }
            } catch (NumberFormatException e) {
                System.out.println("The second parameter should be a natural number.\n");
                return 0;
            }
        }

        // User inputs an invalid property name (e.g. jojo)
        if (app.inputs.length == 3) {
            String inputProperty = app.inputs[2].toUpperCase();
            // Check if the input property matches the enum property
            try {
                Property.valueOf(inputProperty);
            } catch (IllegalArgumentException e) {
                printIncorrectProperty(inputProperty);
                return 0;
            }
        } else if (app.inputs.length >= 4) {
            // There are at least two or more property inputs, therefore we must compare multiple properties

            // Property inputs an invalid multiple or single invalid property names (e.g. jojo drake)
            String property1 = app.inputs[2].toUpperCase();
            String property2 = app.inputs[3].toUpperCase();

            // Prevent user input for same property
            if (property1.equals(property2)) {
                printMutuallyExclusiveProperties(property1, property2);
                return 0;
            } else if (
                // Prevent user input of mutually exclusive properties
                    // EVEN & ODD
                    (property1.equals(Property.ODD.name()) && property2.equals(Property.EVEN.name()) ||
                            property1.equals(Property.EVEN.name()) && property2.equals(Property.ODD.name())) ||
                    // SUNNY & SQUARE
                    (property1.equals(Property.SUNNY.name()) && property2.equals(Property.SQUARE.name()) ||
                            property1.equals(Property.SQUARE.name()) && property2.equals(Property.SUNNY.name())) ||
                    // SPY & DUCK
                    (property1.equals(Property.SPY.name()) && property2.equals(Property.DUCK.name()) ||
                            property1.equals(Property.DUCK.name()) && property2.equals(Property.SPY.name()))
            ) {
                printMutuallyExclusiveProperties(property1, property2);
                return 0;
            }

            // Check for valid enum properties from user input
            boolean property1Found = false;
            boolean property2Found = false;
            for (Enum enumProperty : Property.values()) {
                if (property1.equals(enumProperty.name())) {
                    property1Found = true;
                } else if (property2.equals(enumProperty.name())) {
                    property2Found = true;
                }
            }

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

        app.printProperties();
        System.out.print("\n");
        return 0;
    }

    private static String[] getInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a request: ");
        String input = scanner.nextLine();
        System.out.print("\n");
        return input.split(" ");
    }

    private void printProperties() {
        // Get the first parameter of the input: starting number
        long n = startingNum;

        // Get the second parameter of the input: consecutive numbers
        long length = (inputs.length >= 2) ? consecutiveNum : 1L;

        if (inputs.length > 3) {
            printFourthOption(n, length, inputs);
        } else if (inputs.length == 3) {
            printThirdOption(n, length, inputs[2]);
        } else {
            printFirstSecondOption(n, length, inputs.length);
        }
    }

    private static void printFirstSecondOption(long n, long length, int inputsLength) {
        for (int i = 0; i < length; i++) {
            // Store booleans into a LinkedHashMap since we need to filter for true values later
            long currentNum = n + i;
            Map<String, Boolean> boolMap = getBooleanMap(currentNum);

            if (inputsLength == 1) {
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
                                     jumping: %b
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
                        boolMap.get("jumping"),
                        boolMap.get("even"),
                        boolMap.get("odd"));
            } else {
                System.out.println("\t\t\t" + buildNumberStatement(currentNum, boolMap));
            }
        }
    }

    private static void printThirdOption(long n, long length, String propertyInput) {
        int i = 0;
        long currentNum = n;

        StringBuilder stringBuilder = new StringBuilder();

        while (i < length) {
            Map<String, Boolean> boolMap = getBooleanMap(currentNum);

            boolean propertyIsTrue = boolMap.get(propertyInput.toLowerCase());
            // Explicitly check for jumping property, increment by anther large number
            if (propertyInput.toLowerCase().equals("jumping") && currentNum == 1234567899L) {
                currentNum += 866_442_201L;
            }
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

    private static void printFourthOption(long n, long length, String[] strings) {
        int i = 0;
        int currentNum = (int) n;

        StringBuilder stringBuilder = new StringBuilder();

        while (i < length) {
            Map<String, Boolean> boolMap = getBooleanMap(currentNum);

            boolean firstPropertyIsTrue = boolMap.get(strings[2].toLowerCase());
            boolean secondPropertyIsTrue = boolMap.get(strings[3].toLowerCase());

            if (firstPropertyIsTrue && secondPropertyIsTrue) {
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
            - two natural numbers and properties to search for;
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
                The properties [%s, %s] are wrong.
                Available properties: [EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY]
                %n""", propertyInput1, propertyInput2);
    }

    private static void printMutuallyExclusiveProperties(String propertyInput1, String propertyInput2) {
        System.out.printf("""
                The request contains mutually exclusive properties: [%S, %S]
                There are no numbers with these properties.
                %n""", propertyInput1, propertyInput2);
    }

    private static boolean getBuzz(long n) {
        return (n % 7 == 0 || n % 10 == 7);
    }

    private static boolean getDuck(long n) {
        String strNum = String.valueOf(n);
        for (int i = 0; i < strNum.length(); i++) {
            // NOTE: If we find a number and it is not at 0th index, then it is a Duck Number!!
            if (i != 0 && strNum.charAt(i) == '0') {
                return true;
            }
        }
        return false;
    }

    private static boolean getPalindromic(long n) {
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

    private static boolean getGapful(long n) {
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

    private static boolean getEven(long n) {
        return (n % 2 == 0);
    }

    private static boolean getSpy(long n) {
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

    private static boolean getSquare(long n) {
        double sqrt = Math.sqrt(n);
        return sqrt == (int) sqrt;
    }

    private static boolean getSunny(long n) {
        long nextConsecutive = n + 1L;
        return getSquare(nextConsecutive);
    }

//    private static boolean getJumpingNumber(long n) {
//        // Convert long number into a string
//        String str = Long.toString(n);
//        for (int i = 1; i < str.length(); i++) {
//            int digit1 = Integer.parseInt(String.valueOf(str.charAt(i - 1)));
//            int digit2 = Integer.parseInt(String.valueOf(str.charAt(i)));
//            int difference = Math.abs(digit1 - digit2);
//            if (difference > 1) return false;
//        }
//        return true;
//    }

//    private static boolean getJumpingNumber(long n) {
//        long prevDigit = n % 10;  // Get the last digit as the starting digit
//        n /= 10;  // Remove the last digit
//        while (n > 0) {
//            long currDigit = n % 10;  // Get the current digit
//            long diff = Math.abs(prevDigit - currDigit);  // Calculate the absolute difference
//            if (diff > 1) {
//                return false;  // If difference is greater than 1, not a jumping number
//            }
//            prevDigit = currDigit;  // Update previous digit for next iteration
//            n /= 10;  // Remove the current digit
//        }
//        return true;  // All adjacent digit differences are 0 or 1, so it's a jumping number
//    }

    private static boolean getJumpingNumber(long n) {
        if (n < 10) {
            // Single-digit numbers are always jumping numbers
            return true;
        }

        long prev = n % 10; // Last digit of the number
        n /= 10; // Remove the last digit

        while (n > 0) {
            long curr = n % 10; // Current digit
            long diff = Math.abs(prev - curr); // Absolute difference between previous and current digits

            if (diff != 1) {
                // If the absolute difference is not 1, it's not a jumping number
                return false;
            }

            prev = curr;
            n /= 10; // Move to the next digit
        }

        // If all differences are 1, it's a jumping number
        return true;
    }


    private static String addCommasToLong(long n) {
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

    private static Map<String, Boolean> getBooleanMap(long num) {
        Map<String, Boolean> boolMap = new LinkedHashMap<>();
        boolMap.put("buzz", getBuzz(num));
        boolMap.put("duck", getDuck(num));
        boolMap.put("palindromic", getPalindromic(num));
        boolMap.put("gapful", getGapful(num));
        boolMap.put("spy", getSpy(num));
        boolMap.put("square", getSquare(num));
        boolMap.put("sunny", getSunny(num));
        boolMap.put("jumping", getJumpingNumber(num));
        boolMap.put("even", getEven(num));
        boolMap.put("odd", !getEven(num));
        return boolMap;
    }

    private static String buildNumberStatement(long num, Map<String, Boolean> map) {
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
