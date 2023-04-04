package lnu.exam.examples;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

public class MockTest {
  class ClassToMock {
    public String someMethod() {
      return "original result";
    }
  }

  @Test
  public void shouldReturnMockedResult() {
    // Create a mock object of the class to mock
    ClassToMock mockObj = mock(ClassToMock.class);
    when(mockObj.someMethod()).thenReturn("mocked result");

    // Call the method being tested
    String result = mockObj.someMethod();

    // Verify that the method called the mock object as expected
    verify(mockObj).someMethod();

    // Assert the result
    assertEquals("mocked result", result);
  }
}
