package lnu.exam.examples;

import org.testng.annotations.Test;

public class ConditionalTest {

    @Test(enabled = true, dependsOnMethods = "myTest")
    public void dependentTest() {
        // Test code here
    }

    @Test
    public void myTest() {
        if (myFunction() != true) {
            throw new Error("myTest not passed");
        }
    }

    private boolean myFunction() {
        // Return true or false based on some condition
        return true;
    }
}
