package com.sample.controller

import com.sample.domain.User
import grails.converters.JSON
import grails.transaction.Transactional
import grails.validation.ValidationException
import org.springframework.http.HttpStatus

import static org.springframework.http.HttpStatus.*

@Transactional
class UserController {

    // TODO - Centralize exception handling. Avoid code duplications

    def userService

    /**
     * Get all the users
     *
     * @return JSON of the list of users
     */
    def list() {
        //TODO - implement pagination.
        def userList = userService.findAll()
        render(userList as JSON)
    }

    /**
     * Create the user.
     *
     * @return JSON of the created {@link User}
     */
    def create() {
        try {
            User user = new User(JSON.parse(request)) // Parse the request
            def createdUser = userService.createUser(user)
            response.status = HttpStatus.CREATED.value()
            render(createdUser as JSON)
        } catch (ValidationException ve) {
            log.error("ValidationException occurred while creating User", ve)
            response.status = BAD_REQUEST.value()
            def errorMessage = ve.getErrors().getAllErrors().collect {
                message(code: it.codes[1])
            }
            render errorMessage.join('\n')
        } catch (any) {
            log.error('Cannot create User', any)
            response.status = INTERNAL_SERVER_ERROR.value()
            render message(code: "application.message.server.error")
        }
    }

    /**
     * Updates the {@link User} details. Updates only {@link User#fullName} and {@link User#email}
     *
     * @return JSON of the updated {@link User}
     *         NOT_FOUND (404) status if the user doesn't exist.
     *         BAD_REQUEST (400) status if the validation fails
     *         BAD_REQUEST (400) status if the userId and id of the user object doesn't match
     *
     */
    def update() {
        try {
            User user = new User(JSON.parse(request)) // Parse the request
            def updatedUser = userService.updateUser(user, params.userId as long)
            if (updatedUser) { // user updated successfully
                render(updatedUser as JSON)
            } else { // No user found with the userId
                render status: NOT_FOUND
            }
        } catch (ValidationException ve) {
            // Log the exception and return all the validation errors
            log.error("ValidationException occurred while updating User", ve)
            response.status = BAD_REQUEST.value()
            def errorMessage = ve.getErrors()?.getAllErrors()?.collect {
                message(code: it.codes[1])
            }
            render errorMessage.join('\n')
        } catch (any) {
            log.error('Cannot update User', any)
            response.status = INTERNAL_SERVER_ERROR.value()
            render message(code: "application.message.server.error")
        }
    }

    /**
     * Deletes the {@link User} associated with the userId. NO-OP if the {@link User} doesn't exist.
     */
    def delete() {
        try{
            userService.deleteUser(params.userId as long)
            render status: OK
        }catch(any){
            log.error('Cannot delete User', any)
            response.status = INTERNAL_SERVER_ERROR.value()
            render message(code: "application.message.server.error")
        }
    }

    /**
     * Get {@link User} with the userId
     *
     * @return JSON of the user
     *         NOT_FOUND (404) status if the user doesn't exist.
     */
    def show() {
        try{
            def user = userService.fetchUser(params.userId as long)
            if (user) {
                render(user as JSON)
            } else {
                render status: NOT_FOUND
            }
        }catch (any){
            log.error('Cannot show User', any)
            response.status = INTERNAL_SERVER_ERROR.value()
            render message(code: "application.message.server.error")
        }
    }

}
