package com.sample.service

import com.sample.domain.User
import grails.test.mixin.TestFor
import grails.validation.ValidationException
import spock.lang.Ignore
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(UserService)
class UserServiceSpec extends Specification {

    def setup() {
        mockDomain(User)
    }

    def cleanup() {
    }

    void "test create User"() {
        given:
        def user = new User(fullName: fullName, username: username, email: email, password: password)

        when:
        def createdUser = service.createUser(user)

        then:
        createdUser.id != null
        createdUser.fullName == fullName
        createdUser.email == email
        createdUser.username == username
        createdUser.password == password

        where:
        fullName = "fullName"
        username = "username"
        email = "email@email.com"
        password = "password"
    }

    @Ignore //TODO -- Fix ME
    void "test create User invalid values"() {
        given:
        def user = new User(fullName: null, username: null, email:  "abc@test.com", password: "password")

        when:
        def createdUser = service.createUser(user)

        then:
        thrown(ValidationException)
    }

    void "test fetch user"(){
        given:
        def user = new User(fullName: "fullName", username: "username", email: "email@email.com", password: "password").save()

        when:
        def fetchedUser = service.fetchUser(user.id)


        then:
        user == fetchedUser
    }

    void "test fetch user with non-existing user"(){
        given:
        User.get(1000) == null // user doesn't exist

        when:
        //random user id
        def fetchedUser = service.fetchUser(1000)

        then:
        fetchedUser == null
    }

    void "test find all users with no existing users"(){
        given: 'no user exists'
        User.count() == 0

        when:
        def result = service.findAll()

        then:
        !result //result should be empty
    }


    void "test find all users with existing users"(){
        given:
        def user1 = new User(fullName: "fullName1", username: "username1", email: "email1@email.com", password: "password").save()
        def user2 = new User(fullName: "fullName2", username: "username2", email: "email2@email.com", password: "password").save()
        def user3 = new User(fullName: "fullName3", username: "username3", email: "email3@email.com", password: "password").save()

        when:
        def result = service.findAll()


        then:
        result // NOT null and NOT empty
        result.size() == 3
        result[0] == user1
        result[1] == user2
        result[2] == user3
    }

    void "test delete user for non-existing user"(){
        given:
        User.get(1000) == null // make sure user is absent
        def userCount = User.count()

        when:
        service.deleteUser(1000)

        then:
        userCount == User.count()
    }

    @Ignore //TODO - Fix ME
    void "test delete user for existing user"(){
        given:
        mockDomain(User, [[id:1, fullName: "fullName1", username: "username1", email: "email1@email.com", password: "password"]])
        //def user = new User(fullName: "fullName1", username: "username1", email: "email1@email.com", password: "password").save()

        when: "delete the user"
        service.deleteUser(1)
        //user.delete()

        then: 'user should not exist after deleting'
        User.get(1) == null
    }
}
