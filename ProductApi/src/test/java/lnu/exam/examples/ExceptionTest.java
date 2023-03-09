import org.testng.annotations.Test;

public class ExceptionTest {
    
    @Test(expectedExceptions = {NullPointerException.class, ArrayIndexOutOfBoundsException.class})
    public void testMultipleExceptions() {
        String str = null;
        int[] arr = new int[5];
        arr[10] = 5;
        str.toLowerCase();
    }

    @Test(expectedExceptions = {NullPointerException.class, ArrayIndexOutOfBoundsException.class})
    public void testMultipleExceptionsUnexpectedFirst() {
        int result = 5/0; // this line will throw an unexpected ArithmeticException, making the test fail.
        String str = null;
        int[] arr = new int[5];
        arr[10] = 5;
        str.toLowerCase();
    }

    @Test(expectedExceptions = {NullPointerException.class, ArrayIndexOutOfBoundsException.class})
    public void testMultipleExceptionsUnexpectedSecond() {
        String str = null;
        int[] arr = new int[5];
        arr[10] = 5;
        int result = 5/0; // this line will throw an unexpected ArithmeticException, but the test will pass anyways, since ArrayIndexOutOfBoundsException was already thrown.
        str.toLowerCase();
    }

    @Test(expectedExceptions = {NullPointerException.class, ArrayIndexOutOfBoundsException.class})
    public void testMultipleExceptionsUnexpectedThird() {
        String str = null;
        int[] arr = new int[5];
        arr[10] = 5;
        str.toLowerCase();
        int result = 5/0; // this line will throw an unexpected ArithmeticException, but the test will pass anyways, since ArrayIndexOutOfBoundsException and NullPointerException was already thrown.
    }
}
