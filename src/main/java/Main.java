import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Locale;

public class Main {
    static Character[] operators = new Character[]{'+', '-', '*', '/'};
    static String[] arabNumbers = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    static String[] romanNumbers = new String[]{"i", "ii", "iii", "iv", "v", "vi", "vii", "viii", "ix", "x"};

    public static void main(String[] args) {
        String rowInput = getInput();
        System.out.println(calc(rowInput));
    }

    public static String calc(String input) {
        validateString(input);
        int numberCase = getCase(input);
        String result;
        String[] parsedStrings = parseString(input);
        result = doMath(parsedStrings, numberCase);

        if (numberCase == 2) {
            result = new RomanNumber(Integer.parseInt(result)).toString();
        }

        return result;
    }

    private static String doMath(String[] parsedStrings, int numberCase) {
        int numberOne = 0;
        int numberTwo = 0;
        String result = "";

        switch (numberCase) {
            case 1:
                try {
                    numberOne = Integer.parseInt(parsedStrings[0]);
                    numberTwo = Integer.parseInt(parsedStrings[2]);
                } catch (NumberFormatException exception) {
                    throw new UnsupportedOperationException("Only integers are allowed!");
                }
                break;
            case 2:
                numberOne = new RomanNumber(parsedStrings[0]).toInt();
                numberTwo = new RomanNumber(parsedStrings[2]).toInt();
                break;
        }

        if ((numberOne < 1 || numberOne > 10) || (numberTwo < 1 || numberTwo > 10)) {
            throw new UnsupportedOperationException("Only numbers from 1 to 10 are allowed!");
        }

        switch (parsedStrings[1]) {
            case "+":
                result = String.valueOf(numberOne + numberTwo);
                break;
            case "-":
                result = String.valueOf(numberOne - numberTwo);
                break;
            case "*":
                result = String.valueOf(numberOne * numberTwo);
                break;
            case "/":
                result = String.valueOf(numberOne / numberTwo);
                break;
        }

        return result;
    }

    private static String[] parseString(String input) {
        String[] parsedString = new String[3];
        StringBuilder builder = new StringBuilder();

        char[] chars = input.toCharArray();
        for (char ch : chars
        ) {
            String str = String.valueOf(ch);
            if (!str.isBlank()) {
                builder.append(str);
            }
        }

        input = builder.toString();
        int operatorIndex = StringUtils.indexOfAny(input, Arrays.toString(operators));

        String first = input.substring(0, operatorIndex);
        String second = input.substring(operatorIndex + 1);

        parsedString[0] = first;
        parsedString[1] = String.valueOf(input.charAt(operatorIndex));
        parsedString[2] = second;

        return parsedString;
    }

    private static void validateString(String input) {

        if (input.isBlank() || input.length() < 3) {
            throw new UnsupportedOperationException("There are no input or input is too short!");
        }
        if (Arrays.stream(operators)
                .map(String::valueOf)
                .noneMatch(input::contains)) {
            throw new UnsupportedOperationException("There is no math operator in input!");
        }
        if (Arrays.stream(arabNumbers).anyMatch(input::contains) && Arrays.stream(romanNumbers).anyMatch(input::contains)) {
            throw new UnsupportedOperationException("There are both arabic and roman numbers in input!");
        }

        int operatorCounter = 0;
        char[] chars = input.toCharArray();
        for (char ch : chars
        ) {
            String str = String.valueOf(ch);
            if (Arrays.stream(operators)
                    .map(String::valueOf)
                    .anyMatch(str::contains)) {
                operatorCounter++;
            }
        }
        if (operatorCounter > 1) {
            throw new UnsupportedOperationException("There are more then one operator in input!");
        }

    }

    private static int getCase(String input) {
        if (Arrays.stream(arabNumbers).anyMatch(input::contains)) {
            return 1;
        }
        return 2;
    }

    private static String getInput() {
        String input = "";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            input = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return input.toLowerCase(Locale.ROOT);
    }

}
