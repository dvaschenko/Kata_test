import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class MainTest {

    @ParameterizedTest
    @MethodSource("validStringsProvider")
    void givenValidInputs_whenCalculate_thenOk(String expression, String expected) {
        Assertions.assertEquals(expected, Main.calc(expression));

    }

    static Stream<Arguments> validStringsProvider() {
        return Stream.of(
                arguments("1+1", "2"),
                arguments("2-3", "-1"),
                arguments("4*8", "32"),
                arguments("5/2", "2"),
                arguments("I + V", "VI"),
                arguments("iv - III", "I"),
                arguments("iii * x", "XXX"),
                arguments("vii / iii", "II")
        );
    }

    @Test
    void givenNumbersMoreThan10_thenExceptionThrown() {
        String expression = "11 + 1";
        Assertions.assertThrows(UnsupportedOperationException.class, () -> Main.calc(expression));
    }

    @Test
    void givenNumbersLessThan1_thenExceptionThrown() {
        String expression = "0 + 1";
        Assertions.assertThrows(UnsupportedOperationException.class, () -> Main.calc(expression));
    }

    @Test
    void givenNumbersOfTwoTypes_thenExceptionThrown() {
        String expression = "5 + V";
        Assertions.assertThrows(UnsupportedOperationException.class, () -> Main.calc(expression));
    }

    @Test
    void givenNotExpression_thenExceptionThrown() {
        String expression = "do this math already!!";
        Assertions.assertThrows(UnsupportedOperationException.class, () -> Main.calc(expression));
    }

    @Test
    void givenExpressionWithNegativeRomanResult_thenExceptionThrown() {
        String expression = "V - VI";
        Assertions.assertThrows(NumberFormatException.class, () -> Main.calc(expression));
    }
}
