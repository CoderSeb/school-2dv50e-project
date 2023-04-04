package lnu.exam.testUtils

class PerformanceTests {
    static long getMemoryUsage() {
        Runtime runtime = Runtime.getRuntime()
        runtime.gc()
        return runtime.totalMemory() - runtime.freeMemory()
    }
}
