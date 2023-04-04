package lnu.exam.examples;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

public class ConditionalTest {

    @Test
    @EnabledOnOs(OS.MAC)
    public void testRunOnMACOS() {
        Assertions.assertTrue(true);
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    public void testRunOnWindows() {
        Assertions.assertTrue(true);
    }
}
