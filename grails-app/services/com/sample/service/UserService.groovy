package com.sample.service

import com.sample.domain.User
import grails.transaction.Transactional
import grails.validation.ValidationException

/**
 * Service class for the {@link User} domain object.
 *
 * Provides services for general CRUD operations.
 */
@Transactional
class UserService {

    /**
     * Returns all the users.
     *
     * Only an admin user can request for all the users
     *
     * @return returns the list of users
     */
    // @Secured("ADMIN") TODO -- uncomment ME once security is implemented
    def findAll() {
        //TODO - implement pagination.
        return User.list()
    }

    /**
     * Creates an {@link User} with the input.
     *
     * Throws {@link ValidationException} if the validation fails.
     *
     * @param user
     *      input {@link User}
     * @return returns the created {@link User}
     */
    def createUser(User user) {
        return user.save();
    }

    /**
     * Updates the {@link User} details. Updates only {@link User#fullName} and {@link User#email}
     *
     * Throws {@link ValidationException} if the validation fails.
     *
     * @param user
     *      User details
     * @param userId
     *      userId for which the details needs to be updated
     * @return returns the updated {@link User}. Returns null if user doesn't exist.
     */
    def updateUser(User user, userId) {
        //TODO - Verify whether the userId Matches with the id of the user (if present)
        //TODO - throw ValidationException if not matching
        def fetchedUser = User.get(userId)
        if (fetchedUser) {
            fetchedUser.fullName = user.fullName
            fetchedUser.email = user.email
            return fetchedUser.save()
        }
        return null
    }

    /**
     * Deletes the exiting user. NO-OP if the user doesn't exist.
     *
     * @param userId
     *       Id of the user to be deleted
     */
    void deleteUser(userId) {
        //TODO -- verify whether we can delete the user without fetching
        User fetchedUser = User.get(userId);
        if(fetchedUser){
            fetchedUser.delete()
        }
        //fetchedUser?.delete()
    }

    /**
     * Returns the {@link User} with the specified id. Returns null if the user doesn't exist
     *
     * @param id
     *      id of the user
     * @return returns the {@link User} with the id. Returns null if the user doesn't exist
     */
    def fetchUser(id) {
        return User.get(id);
    }
}
