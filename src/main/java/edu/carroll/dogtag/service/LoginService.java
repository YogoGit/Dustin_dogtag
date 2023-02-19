package edu.carroll.dogtag.service;

import edu.carroll.dogtag.jpa.model.Login;
import edu.carroll.dogtag.web.form.LoginForm;

import java.util.List;

public interface LoginService {
    /**
     * Given a loginForm, determine if the information provided is valid, and the user exists in the system.
     * @param loginForm - Data containing user login information, such as username and password.
     * @return true if data exists and matches what's on record, false otherwise
     */
    boolean validateUser(LoginForm loginForm);

    Login save(Login login);
}