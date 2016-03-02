package com.sample.controller

import com.sample.domain.User
import com.sample.service.UserService
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification
import com.sample.controller.UserController

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(UserController)
@Mock([UserService])
class UserControllerSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test get all users with no existing users"() {
        given:
        def mockUserService = Mock(UserService)
        mockUserService.findAll() >> []
        controller.userService = mockUserService

        when:
        def usersList = controller.list()

        then:
        !usersList
    }
}
