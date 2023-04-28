package edu.carroll.dogtag.service;

import edu.carroll.dogtag.jpa.model.Login;

/**
 * RegisterService is used as the business logic for the RegisterController. It checks if the user or
 * email or both already exist in the database in which they will not be able to register with those
 * entry's by returning a list of users and email which should return and empty list. The service then
 * registers the user if all information is entered correctly.
 */
public interface RegisterService {

    /**
     * Checks if a user is already in the database for allowing a user to
     * register or not with that name.
     *
     * @param user
     * @return true comes back that the user is already in the database.
     * false comes back in the user is not in the database. This is one of the
     * checks before a user can register
     */
    boolean userExists(String user);

    /**
     * Checks if an email is already in the database for allowing email to
     * register or not with that name.
     *
     * @param email @return true comes back that the email is already in the database.
     *              false comes back in the user is not in the database. This is one of the
     *              checks before a user can register
     */
    boolean emailExists(String email);

    /**
     * register is used once all the information is correct being given to
     * this method to send to the database
     *
     * @param register this is using the Login models methods to set all
     *                 information from the register form.
     * @return true if was successful in registering.
     */
    boolean register(Login register);


}
