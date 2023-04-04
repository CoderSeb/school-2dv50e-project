package lnu.exam.examples;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ParameterTest {

    // Define a data provider that returns a set of test data
    @DataProvider(name = "testData")
    public Object[][] testData() {
        return new Object[][] {
                { 2, 3, 5 },
                { -1, 5, 4 },
                { 0, 0, 0 },
                { 100, -50, 50 }
        };
    }

    // Define a test method that accepts parameters from the data provider
    @Test(dataProvider = "testData")
    public void testAdd(int a, int b, int expected) {
        // Call the method being tested with the given arguments
        int result = MyClass.add(a, b);

        // Assert that the result matches the expected value
        assertEquals(result, expected);
    }
}

// A simple class with a method to be tested
class MyClass {
    public static int add(int a, int b) {
        return a + b;
    }
}