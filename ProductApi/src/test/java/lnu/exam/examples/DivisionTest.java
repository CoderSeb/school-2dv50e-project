package lnu.exam.examples;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class DivisionTest {

    @Test
    void testDivideByZero() {
        assertThrows(ArithmeticException.class, () -> {
            int result = 5 / 0;
        });
    }
}
