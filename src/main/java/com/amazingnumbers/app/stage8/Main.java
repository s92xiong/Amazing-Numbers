package com.amazingnumbers.app.stage8;

import java.util.*;

enum NumberProperty {
    EVEN("EVEN", "-EVEN") {
        @Override
        public boolean performOperation(long n) {
            return (n % 2 == 0);
        }
    },
    ODD("ODD", "-ODD") {
        @Override
        public boolean performOperation(long n) {
            return !EVEN.performOperation(n);
        }
    },
    BUZZ("BUZZ", "-BUZZ") {
        public boolean performOperation(long n) {
            return (n % 7 == 0 || n % 10 == 7);
        }
    },
    DUCK("DUCK", "-DUCK") {
        public boolean performOperation(long n) {
            String strNum = String.valueOf(n);
            for (int i = 0; i < strNum.length(); i++) {
                // NOTE: If we find a number, and it is not at 0th index, then it is a Duck Number!!
                if (i != 0 && strNum.charAt(i) == '0') {
                    return true;
                }
            }
            return false;
        }
    },
    PALINDROMIC("PALINDROMIC", "-PALINDROMIC") {
        @Override
        public boolean performOperation(long n) {
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
    },
    GAPFUL("GAPFUL", "-GAPFUL") {
        @Override
        public boolean performOperation(long n) {
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
    },
    SPY("SPY", "-SPY") {
        @Override
        public boolean performOperation(long n) {
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
    },
    SQUARE("SQUARE", "-SQUARE") {
        @Override
        public boolean performOperation(long n) {
            double sqrt = Math.sqrt(n);
            return sqrt == (int) sqrt;
        }
    },
    SUNNY("SUNNY", "-SUNNY") {
        @Override
        public boolean performOperation(long n) {
            long nextConsecutive = n + 1L;
            return SQUARE.performOperation(nextConsecutive);
        }
    },
    JUMPING("JUMPING", "-JUMPING") {
        @Override
        public boolean performOperation(long n) {
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
    },
    HAPPY("HAPPY", "-HAPPY") {
        @Override
        public boolean performOperation(long n) {
            if (n <= 0 ) return false;
            long num = n;
            Set<Integer> seen = new HashSet<>(); // Store numbers (sum) that have been seen

            while (num != 1) {
                int sum = 0;
                while (num > 0) {
                    int digit = (int) (num % 10); // Extract the last digit
                    sum += (digit * digit);
                    num /= 10; // Remove the last digit
                }
                if (seen.contains(sum)) return false;
                seen.add(sum);
                num = sum;
            }
            return true; // If loop terminates with n = 1, it's a happy number
        }
    },
    SAD("SAD", "-SAD") {
        @Override
        public boolean performOperation(long n) {
            return !HAPPY.performOperation(n);
        }
    };

    private String positive;
    private String negative;

    private NumberProperty(String _positive, String _negative) {
        positive = _positive;
        negative = _negative;
    }

    public String getPositive() {
        return positive;
    }

    public String getNegative() {
        return negative;
    }

    public abstract boolean performOperation(long n);
}

public class Main {
    private final String[] inputs; // String input split at " " when instantiating the Main class
    private long startingNum;
    private long consecutiveNum;
    private String[] propertyInputs;
    private List<String> negativeProperties;

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
        negativeProperties = new ArrayList<>();
        if (_stringInput.length > 2) {
            // Initialize propertyInputs with values from _stringInput
            propertyInputs = new String[_stringInput.length - 2];
            for (int i = 2; i < _stringInput.length; i++) {
                propertyInputs[i - 2] = _stringInput[i].toUpperCase();
            }
        }
    }

    private static int run() {
        Main app = new Main(getInput());

        // Terminate app
        if (app.inputs[0].equals("0")) return -1;

        // Re-prompt instructions if user inputs an empty line
        if (app.inputs[0].isEmpty()) {
            printInstructions();
            return 0;
        }

        // Update startingNum
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

        // Property validation
        if (app.inputs.length >= 3) {
            // Check if properties exist in the enum
            List<String> invalidProperties = app.checkPropertiesExistInEnum(app.propertyInputs);
            if (!invalidProperties.isEmpty()) {
                printIncorrectProperties(invalidProperties);
                return 0;
            }

            // Check for duplicate property inputs
            String[] duplicateProperties = hasDuplicates(app.propertyInputs);
            if (duplicateProperties != null) {
                printMutuallyExclusiveProperties(duplicateProperties);
                return 0;
            }

            // Check for mutually exclusive property inputs
            String[] mutuallyExclusiveProps = isMutuallyExclusiveProperties(app.propertyInputs);
            if (mutuallyExclusiveProps != null) {
                printMutuallyExclusiveProperties(mutuallyExclusiveProps);
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
        long length = (inputs.length >= 2) ? consecutiveNum : 1L;
        if (inputs.length >= 3) {
            printAdditionalProperties(startingNum, length, propertyInputs, negativeProperties);
        } else {
            printFirstOrSecondOption(startingNum, length, inputs.length, negativeProperties);
        }
    }

    private static void printFirstOrSecondOption(long n, long length, int inputsLength, List<String> negativeProperties) {
        // This runs when the input has two natural numbers with or without a single property
        for (int i = 0; i < length; i++) {
            // Store booleans into a LinkedHashMap since we need to filter for true values later
            long currentNum = n + i;
            Map<String, Boolean> boolMap = createBooleanMap(currentNum);

            if (inputsLength == 1) {
                String strNum = formatLongNumberWithCommas(currentNum);
                // When there is only 1 natural number, we print out all properties as normal
                System.out.printf("""
                                Properties of %s
                                        even: %b
                                         odd: %b
                                        buzz: %b
                                        duck: %b
                                 palindromic: %b
                                      gapful: %b
                                         spy: %b
                                      square: %b
                                       sunny: %b
                                     jumping: %b
                                       happy: %b
                                         sad: %b%n""",
                        strNum,
                        boolMap.get("odd"),
                        boolMap.get("even"),
                        boolMap.get("buzz"),
                        boolMap.get("duck"),
                        boolMap.get("palindromic"),
                        boolMap.get("gapful"),
                        boolMap.get("spy"),
                        boolMap.get("square"),
                        boolMap.get("sunny"),
                        boolMap.get("jumping"),
                        boolMap.get("happy"),
                        boolMap.get("sad"));
            } else {
                System.out.println("\t\t\t" + buildNumberStatement(currentNum, boolMap));
            }
        }
    }

    private static void printAdditionalProperties(long n,
                                                  long length,
                                                  String[] propertyInputs,
                                                  List<String> negativeProperties) {
        int i = 0;
        long currentNum = n;

        StringBuilder stringBuilder = new StringBuilder();

        while (i < length) {
            Map<String, Boolean> boolMap = createBooleanMap(currentNum);

            // Explicitly check for jumping property, increment by large number
            if (propertyInputs[0].equalsIgnoreCase("jumping") && currentNum == 1234567899L) {
                currentNum += 866_442_201L;
            }

            boolean negProps = validatePropertyValues(boolMap, propertyInputs, negativeProperties);
            if (negProps) {
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

    private static boolean validatePropertyValues(Map<String, Boolean> map,
                                                  String[] propertyInputs,
                                                  List<String> negativeProperties) {
        // Check that all negative properties are false
        for (String str : negativeProperties) {
            if (map.get(str.toLowerCase())) {
                // If the negative property is true, return false
                return false;
            }
        }

        // Check that all non-negative properties are true
        for (String str : propertyInputs) {
            if (str.charAt(0) != '-') {
                if (!map.get(str.toLowerCase())) {
                    return false;
                }
            }
        }
        // If we made it through both checks, then all properties for this number are valid
        return true;
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
            - a property preceded by minus must not be present in numbers;
            - separate the parameters with one space;
            - enter 0 to exit.
            """);
    }

    private static void printIncorrectProperties(List<String> properties) {
        String plural1 = properties.size() > 1 ? "properties" : "property";
        String plural2 = properties.size() > 1 ? "are" : "is";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < properties.size(); i++) {
            sb.append(properties.get(i));
            if (i + 1 != properties.size()) {
                sb.append(", ");
            }
        }
        System.out.printf("""
                The %s [%s] %s wrong.
                Available properties:
                [EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING, HAPPY, SAD]
                %n""", plural1, sb, plural2);
    }

    private static void printMutuallyExclusiveProperties(String[] properties) {
        String str = properties[0] + ", " + properties[1];
        System.out.printf("""
                The request contains mutually exclusive properties: [%S]
                There are no numbers with these properties.
                %n""", str);
    }

    private static String formatLongNumberWithCommas(long n) {
        // Convert the number to a string
        String numberStr = String.valueOf(n);

        // Reverse the string, so we can process it from right to left
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

    private static Map<String, Boolean> createBooleanMap(long num) {
        Map<String, Boolean> boolMap = new LinkedHashMap<>();
        for (NumberProperty prop : NumberProperty.values()) {
            boolMap.put(prop.name().toLowerCase(), prop.performOperation(num));
        }
        return boolMap;
    }

    private static String buildNumberStatement(long num, Map<String, Boolean> map) {
        StringBuilder strBuilder = new StringBuilder();
        String strNum = formatLongNumberWithCommas(num);
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

    private List<String> checkPropertiesExistInEnum(String[] propertyInputs) {
        List<String> list = new LinkedList<>();
        for (String input : propertyInputs) {
            // Check if the input has a hyphen at the start, if it does, get the remainder of the string and compare it
            char firstChar = input.charAt(0);
            String validateInput;
            boolean negativeProp = false;
            if (firstChar == '-') {
                negativeProp = true;
                validateInput = removeFirstChar(input);
            } else {
                validateInput = input;
            }
            validateInput.toUpperCase();

            try {
                NumberProperty.valueOf(validateInput);
            } catch (IllegalArgumentException e) {
                list.add(validateInput);
            }

            // If the input passed validation with a negative property, add it to negativeProperties List
            if (negativeProp) {
                for (String str : negativeProperties) {
                    System.out.println(str);
                }
                negativeProperties.add(validateInput);
            }
        }
        return list;
    }

    private static String[] hasDuplicates(String[] propertyInputs) {
        // Use a HashSet since it doesn't allow duplicate elements
        Set<String> set = new HashSet<>();
        String[] arr = new String[2];
        boolean isNegativeProp = false;
        for (String input : propertyInputs) {
            String str;
            if (input.charAt(0) == '-') {
                str = removeFirstChar(input);
                isNegativeProp = true;
            } else {
                str = input;
            }
            // Return the string that has the duplicate
            if (!set.add(str)) {
                arr[0] = str;
                if (isNegativeProp) {
                    arr[1] = "-" + str;
                    isNegativeProp = false; // Reset to false so we don't continuously add "-"
                } else {
                    arr[1] = str;
                }
                return arr;
            }
        }
        return null;
    }

    private static String[] isMutuallyExclusiveProperties(String[] propertyInputs) {
        List<String> list = Arrays.asList(propertyInputs);
        String[] arr = new String[2];
        // Conflicting properties
        boolean oddEven = list.contains(NumberProperty.EVEN.name()) && list.contains(NumberProperty.ODD.name());
        boolean spyDuck = list.contains(NumberProperty.SPY.name()) && list.contains(NumberProperty.DUCK.name());
        boolean sunnySquare = list.contains(NumberProperty.SUNNY.name()) && list.contains(NumberProperty.SQUARE.name());
        if (oddEven) {
            arr[0] = "ODD";
            arr[1] = "EVEN";
            return arr;
        } else if (spyDuck) {
            arr[0] = "SPY";
            arr[1] = "DUCK";
            return arr;
        } else if (sunnySquare) {
            arr[0] = "SUNNY";
            arr[1] = "SQUARE";
            return arr;
        }
        return null;
    }

    public static String removeFirstChar(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("Input string cannot be null or empty");
        }
        return input.substring(1);
    }
}
