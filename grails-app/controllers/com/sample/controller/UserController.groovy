package com.sample.controller

import com.sample.domain.User
import grails.converters.JSON
import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.OK

@Transactional
class UserController {

    def userService

    def list() {
        render(userService.findAll() as JSON)
    }

    def create() {
        User user = new User(JSON.parse(request))
        render(userService.createUser(user) as JSON)
    }

    def update() {
        User user = new User(JSON.parse(request))
        def updatedUser = userService.updateUser(user, params.userId as long)
        if (updatedUser) {
            render(updatedUser as JSON)
        } else {
            render status: NOT_FOUND
        }
    }

    def delete() {
        userService.deleteUser(params.userId as long)
        render status: OK
    }

    def show() {
        def user = userService.fetchUser(params.userId as long)
        if (user) {
            render(user as JSON)
        } else {
            render status: NOT_FOUND
        }
    }

}
