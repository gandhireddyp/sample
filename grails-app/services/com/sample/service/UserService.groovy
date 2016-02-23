package com.sample.service

import com.sample.domain.User
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional

@Transactional
class UserService {

    def findAll(){
        return User.list()
    }

    def createUser(User user) {
        return user.save(failOnError: true);
    }

    def updateUser(User user, userId) {
        def fetchedUser = User.get(userId)
        if (fetchedUser) {
            fetchedUser.email = user.email
            return fetchedUser.save(flush: true)
        }
        return null
    }

    /**
     * Deletes the exiting user.
     *
     * returns "true" if the user exists. Else. returns false
     *
     * @param userId
     * @return
     */
    def deleteUser(userId) {
        User fetchedUser = User.get(userId);
        if (fetchedUser) {
            fetchedUser.delete()
        }
    }

    def fetchUser(id) {
        return User.get(id);
    }
}
