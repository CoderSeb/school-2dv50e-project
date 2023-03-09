import spock.lang.FailsWith
import spock.lang.Specification

class ExpectedFailTest extends Specification {
    def "throws exception if method called on null object"() {
        given:
        def obj = null
        def myClass = new MyClass()

        when:
        myClass.someMethod(obj)

        then:
        thrown(NullPointerException)
    }

    def "throws exception if method called on null object and assert message"() {
        given:
        def obj = null
        def myClass = new MyClass()

        when:
        myClass.someMethod(obj)

        then:
        def t = thrown(NullPointerException)
        t.message == 'Object is null'
    }
}

class MyClass {
    def someMethod(obj) {
        if (obj == null) {
            throw new NullPointerException("Object is null")
        }
    }
}
