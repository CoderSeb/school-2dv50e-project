import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ParameterTest {

    @ParameterizedTest
    @ValueSource(strings = { "foo", "bar", "baz" })
    void testStringLength(String input) {
        assertEquals(3, input.length());
    }
}
