package edu.carroll.dogtag.service;

import edu.carroll.dogtag.jpa.model.Login;

public interface RegisterService {
    /**
     * Given a registerForm, determine if the information provided is valid, and the user exists in the system.
     *
     * @param user - Data containing user login information, such as username and password.
     * @return true if data exists and matches what's on record, false otherwise
     */

    boolean userExists(String user);

    /**
     * Given a registerForm, determine if the information provided is valid, and the user exists in the system.
     *
     * @param email -
     * @return
     */

    boolean emailExists(String email);

    /**
     * Given a registerForm, determine if the information provided is valid, and the user exists in the system.
     *
     * @param register -
     * @return
     */

    void register(Login register);

    /**
     * Given a registerForm, determine if the information provided is valid, and the user exists in the system.
     *
     * @param userEntityDelete -
     * @return
     */

    void deleteByUser(String userEntityDelete);
}
