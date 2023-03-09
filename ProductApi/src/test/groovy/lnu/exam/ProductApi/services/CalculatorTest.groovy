import spock.lang.Specification

class CalculatorSpec extends Specification {
    def "addition"() {
        given:
        Calculator calculator = new Calculator()
        
        when:
        int result = calculator.add(2, 3)
        
        then:
        result == 5
    }
}

class Calculator {
    int add(int a, int b) {
        return a + b
    }
}
