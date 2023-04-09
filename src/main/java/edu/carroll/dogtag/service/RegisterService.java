package edu.carroll.dogtag.service;

import edu.carroll.dogtag.jpa.model.Login;

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
     * Checks if a email is already in the database for allowing email to
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
     * @param register
     * @return true if was successful in registering.
     */

    boolean register(Login register);

    /**
     * Given a user this method is to delete a user for the database
     *
     * @param userEntityDelete
     */

    void deleteByUser(String userEntityDelete);
}
