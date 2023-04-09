package edu.carroll.dogtag.service;

import edu.carroll.dogtag.jpa.model.Login;
import edu.carroll.dogtag.web.form.LoginForm;

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
     *             find all information pertaining to their log in.
     * @return a Login object that can be used then to call methods.
     */

    Login findLogin(String user);
}