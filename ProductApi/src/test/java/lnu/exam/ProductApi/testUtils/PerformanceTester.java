package lnu.exam.ProductApi.testUtils;

public class PerformanceTester {
  public static long getUsedMemory() {
    Runtime runtime = Runtime.getRuntime();
    return runtime.totalMemory() - runtime.freeMemory();
  }
}
