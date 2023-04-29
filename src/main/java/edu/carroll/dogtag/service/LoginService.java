package edu.carroll.dogtag.service;

import edu.carroll.dogtag.jpa.model.Login;
import edu.carroll.dogtag.web.form.LoginForm;

/**
 * LoginService is used as the business logic for the LoginController. It uses the findLogin
 * to verify there is a user with that name in the database by returning a list of users that match
 * which should only return 1. This service takes care of validating a user that is attempting to log into the
 * application. It uses the methods to ensure the user has entered correct information and
 * that they are in the database. The service checks what the user has entered by creating a
 * hash of the password the same way that it does when the user registers. Then compares the password
 * in the database with the hashed user entry in the form.
 */
public interface LoginService {

    /**
     * Given a loginForm, determine if the information provided is valid, and the user exists in the system.
     *
     * @param loginForm - Data containing user login information, such as username and password.
     * @return true if data exists and matches what's on record, false otherwise
     */
    boolean validateUser(LoginForm loginForm);

    /**
     * This is used to find the Login for a user with multiple services using them to
     * check if user is in the system before methods send back information
     *
     * @param user is what person is trying to log in this information is used to
     *             find all information pertaining to their login.
     * @return a Login object that can be used then to call methods.
     */
    Login findLogin(String user);

}