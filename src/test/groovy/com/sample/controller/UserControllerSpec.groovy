package com.sample.controller

import com.sample.domain.User
import com.sample.service.UserService
import grails.converters.JSON
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.validation.ValidationException
import org.springframework.http.HttpStatus
import org.springframework.validation.Errors
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(UserController)
@Mock([UserService])
class UserControllerSpec extends Specification {

    public static final String CONTENT_TYPE_JSON = "application/json;charset=UTF-8"
    public static final String USER_JSON =
            '{"fullName": "fullName", "username":"username", "email": "abc@test.com", "password":"password"}'

    UserService mockUserService = Mock()

    def setup() {
        controller.userService = mockUserService
        mockDomain(User)
    }

    def cleanup() {
    }

    void "test list users with no existing users"() {
        given:
        1 * mockUserService.findAll() >> []

        when:
        controller.list()

        then:
        response.status == HttpStatus.OK.value()
        response.contentType == CONTENT_TYPE_JSON
        response.contentAsString == "[]" // empty results
    }

    void "test list users with existing users"() {
        given:
        1 * mockUserService.findAll() >> [Mock(User), Mock(User)] // return list containing two users (mock)

        when:
        controller.list()

        then:
        response.status == HttpStatus.OK.value()
        response.contentType == CONTENT_TYPE_JSON
        List parsedList = JSON.parse(response.contentAsString) as List
        parsedList.size() == 2 // result contains 2 user elements
    }

    void "test create user with invalid values"() {
        given:
        controller.request.contentType = "application/json"
        controller.request.content = '{email: "abc@test.com"}'

        //get the list of errors
        User user = new User(email: "abc@test.com")
        Object errors = getValidationErrors(user)

        //create validationException with the errors
        1 * mockUserService.createUser(_) >> { throw new ValidationException("Validation Exception", errors) }

        when:
        controller.create();

        then:
        response.status == HttpStatus.BAD_REQUEST.value()
        // Not used multiline string for readability (as this is a test)
        response.text == "com.sample.domain.User.fullName.nullable.error.fullName\n" +
                "com.sample.domain.User.username.nullable.error.username\n" +
                "com.sample.domain.User.password.nullable.error.password"
    }


    void "test create user with Unknown exception"() {
        given:
        controller.request.contentType = "application/json"
        controller.request.content = USER_JSON

        // throw some random exception
        1 * mockUserService.createUser(_) >> { throw new IllegalArgumentException("some exception") }

        when:
        controller.create();

        then:
        response.status == HttpStatus.INTERNAL_SERVER_ERROR.value()
        response.text == "application.message.server.error"
    }

    void "test create with valid values"() {
        given:
        controller.request.contentType = "application/json"
        controller.request.content = '{"fullName": $fullName, "username":$username, "email": $email, "password":$password}'
        1 * mockUserService.createUser(_) >> {
            new User(fullName: fullName, username: username, email: email, password: password)
        }

        when:
        controller.create()

        then:
        response.status == HttpStatus.CREATED.value()
        response.contentType == CONTENT_TYPE_JSON

        //Parse the request and verify the values
        def result = response.json
        result.username == username
        result.email == email
        result.fullName == fullName
        result.password == password

        where:
        fullName = "fullName"
        username = "username"
        email = "email@email.com"
        password = "password"
    }


    void "test update user with invalid values"() {
        given:
        controller.request.contentType = "application/json"
        controller.request.content = '{email: "invalidEmail"}'
        controller.params.userId = 100

        //get the list of errors
        User user = new User(fullName: "fullName", username: "username", email: "invalidEmail", password: "password")
        def errors = getValidationErrors(user)

        //create validationException with the errors
        1 * mockUserService.updateUser(_, _) >> { throw new ValidationException("Validation Exception", errors) }

        when:
        controller.update();

        then:
        response.status == HttpStatus.BAD_REQUEST.value()
        // Not used multiline string for readability (as this is a test)
        response.text == "com.sample.domain.User.email.email.error.email"
    }

    void "test update user with Unknown exception"() {
        given:
        controller.request.contentType = "application/json"
        controller.request.content = USER_JSON
        controller.params.userId = 100

        // throw some random exception
        1 * mockUserService.updateUser(_, _) >> { throw new IllegalArgumentException("some exception") }

        when:
        controller.update();

        then:
        response.status == HttpStatus.INTERNAL_SERVER_ERROR.value()
        response.text == "application.message.server.error"
    }

    void "test update with valid values"() {
        given:
        controller.request.contentType = "application/json"
        controller.request.content = '{"fullName": $fullName, "username":$username, "email": $email, "password":$password}'
        controller.params.userId = 100
        1 * mockUserService.updateUser(_, _) >> {
            new User(fullName: fullName, username: username, email: email, password: password)
        }

        when:
        controller.update()

        then:
        response.status == HttpStatus.OK.value()
        response.contentType == CONTENT_TYPE_JSON

        //Parse the request and verify the values
        def result = response.json
        result.username == username
        result.email == email
        result.fullName == fullName
        result.password == password

        where:
        fullName = "fullName"
        username = "username"
        email = "email@email.com"
        password = "password"
    }

    void "test show user with Unknown exception"() {
        given:
        controller.params.userId = 100

        // throw some random exception
        1 * mockUserService.fetchUser(_) >> { throw new IllegalArgumentException("some exception") }

        when:
        controller.show();

        then:
        response.status == HttpStatus.INTERNAL_SERVER_ERROR.value()
        response.text == "application.message.server.error"
    }

    void "test show user with valid values"() {
        given:
        controller.params.userId = 100
        1 * mockUserService.fetchUser(_) >> {
            new User(fullName: fullName, username: username, email: email, password: password)
        }

        when:
        controller.show()

        then:
        response.status == HttpStatus.OK.value()
        response.contentType == CONTENT_TYPE_JSON

        //Parse the request and verify the values
        def result = response.json
        result.username == username
        result.email == email
        result.fullName == fullName
        result.password == password

        where:
        fullName = "fullName"
        username = "username"
        email = "email@email.com"
        password = "password"
    }

    void "test delete user with Unknown exception"() {
        given:
        controller.params.userId = 100

        // throw some random exception
        1 * mockUserService.deleteUser(_) >> { throw new IllegalArgumentException("some exception") }

        when:
        controller.delete();

        then:
        response.status == HttpStatus.INTERNAL_SERVER_ERROR.value()
        response.text == "application.message.server.error"
    }

    void "test delete user with valid values"() {
        given:
        controller.params.userId = 100
        1 * mockUserService.deleteUser(_) >> {}

        when:
        controller.delete()

        then:
        response.status == HttpStatus.OK.value()
    }

    private Errors getValidationErrors(User user) {
        user.validate()
        return user.errors
    }


}
