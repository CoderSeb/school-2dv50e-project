import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DivisionTest {

    @Test
    void testDivideByZero() {
        assertThrows(ArithmeticException.class, () -> {
            int result = 5 / 0;
        });
    }
}
