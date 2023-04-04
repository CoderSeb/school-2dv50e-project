package lnu.exam.examples;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

@EnabledOnOs(OS.MAC)
public class ConditionalTest {

    @Test
    public void testSomething() {
        // your test code here
    }
}
