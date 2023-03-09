import spock.lang.IgnoreIf
import spock.lang.Requires
import spock.lang.Specification

class ConditionalTest extends Specification {
  def jvm = System.getProperties()
  def sys = System.getenv()

  @IgnoreIf({ !jvm.java8Compatible })
  def "addition in Java 8+"() {
    given:
    ConditionalCalculator calculator = new ConditionalCalculator()

    when:
    int result = calculator.add(2, 3)

    then:
    result == 5
  }

  @Requires({ sys["targetEnvironment"] != "prod" })
  def "addition in non production environment"() {
    given:
    ConditionalCalculator calculator = new ConditionalCalculator()

    when:
    int result = calculator.add(5, 5)

    then:
    result == 10
  }
}

class ConditionalCalculator {
    int add(int a, int b) {
        return a + b
    }
}
