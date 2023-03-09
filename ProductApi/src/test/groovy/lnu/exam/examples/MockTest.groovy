import spock.lang.Specification

class MockTest extends Specification {

  def "should return mocked result"() {
    given:
    def mockObj = Mock(ClassToMock)
    mockObj.someMethod() >> "mocked result"

    when:
    def result = mockObj.someMethod()

    then:
    result == "mocked result"
  }
}

class ClassToMock {
  def someMethod() {
    // implementation
  }
}