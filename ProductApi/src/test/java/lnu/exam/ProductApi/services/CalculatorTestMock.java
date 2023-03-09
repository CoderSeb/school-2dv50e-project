import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

public class CalculatorTestMock {

    interface MyLogger {
        void log(String message);
    }

    class MyCalculator {
        private final MyLogger logger;

        public MyCalculator(MyLogger logger) {
            this.logger = logger;
        }

        public int add(int a, int b) {
            logger.log("Adding " + a + " and " + b);
            return a + b;
        }
    }

    @Test
    void testAdd() {
        // Create a mock object of the dependency
        MyLogger mockLogger = mock(MyLogger.class);

        // Create an instance of the class being tested, and inject the mock dependency
        MyCalculator calculator = new MyCalculator(mockLogger);

        // Call the method being tested
        int result = calculator.add(2, 3);

        // Verify that the method called the dependency as expected
        verify(mockLogger).log("Adding 2 and 3");

        // Assert the result
        assertEquals(5, result);
    }
}
