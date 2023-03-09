import org.testng.Assert;
import org.testng.annotations.Test;

public class CalculatorTest {

    @Test
    public void testAddition() {
        Calculator calculator = new Calculator();
        int result = calculator.add(2, 3);
        Assert.assertEquals(result, 5);
    }

}

class Calculator {

    public int add(int a, int b) {
        return a + b;
    }

}
