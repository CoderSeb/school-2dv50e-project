import spock.lang.Specification
import spock.lang.Unroll

class CalculatorTest extends Specification {

    ParameterCalculator calculator = new ParameterCalculator()

    @Unroll
    def "should divide two integers (#x / #y = #expectedResult)"() {
        when:
        int result = calculator.divide(x, y)

        then:
        result == expectedResult

        where:
        x | y | expectedResult
        3 | 3 | 1
        4 | 2 | 2
        1 | 1 | 1
    }
}

class ParameterCalculator {
    int divide(int x, int y) {
        x / y
    }
}
