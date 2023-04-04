import spock.lang.Specification

class CalculatorTestUnitStyle extends Specification {
    void "test addition"() {
        CalculatorUnitStyle calculator = new CalculatorUnitStyle()
        int result = calculator.add(2, 3)
        assert result == 5
    }
}

class CalculatorUnitStyle {
    int add(int a, int b) {
        return a + b
    }
}
