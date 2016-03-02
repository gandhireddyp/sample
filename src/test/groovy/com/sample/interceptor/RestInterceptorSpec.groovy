package com.sample.interceptor


import grails.test.mixin.TestFor
import spock.lang.Specification
import com.sample.interceptor.CorsInterceptor

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(CorsInterceptor)
class CorsInterceptorSpec extends Specification {

    def setup() {
    }

    def cleanup() {

    }

    void "Test rest interceptor matching"() {
        when:"A request matches the interceptor"
            withRequest(controller:"rest")

        then:"The interceptor does match"
            interceptor.doesMatch()
    }
}
