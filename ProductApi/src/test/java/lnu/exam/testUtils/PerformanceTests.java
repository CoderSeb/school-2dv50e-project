package lnu.exam.testUtils;

public class PerformanceTests {
  public static long getMemoryUsage() {
    Runtime runtime = Runtime.getRuntime();
    return runtime.totalMemory() - runtime.freeMemory();
  }

}
