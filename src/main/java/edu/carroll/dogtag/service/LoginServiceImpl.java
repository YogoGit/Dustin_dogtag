package edu.carroll.dogtag.service;

import edu.carroll.dogtag.jpa.model.Login;
import edu.carroll.dogtag.jpa.repo.LoginRepository;
import edu.carroll.dogtag.web.form.LoginForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {
    private static final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);
    private final LoginRepository loginRepo;

    public LoginServiceImpl(LoginRepository loginRepo) {
        this.loginRepo = loginRepo;
    }

    /**
     * Given a loginForm, determine if the information provided is valid, and the user exists in the system.
     *
     * @param loginForm - Data containing user login information, such as username and password.
     * @return true if data exists and matches what's on record, false otherwise
     */
    @Override
    public boolean validateUser(LoginForm loginForm) {
        log.info("validateUser: ¡™'{}' attempted login", loginForm.getUser());
        // Always do the lookup in a case-insensitive manner (lower-casing the data).
        List<Login> user = loginRepo.findByUserIgnoreCase(loginForm.getUser());

        // We expect 0 or 1, so if we get more than 1, bail out as this is an error we don't deal with properly.
        if (user.size() != 1) {
            log.info("validateUser:{} users not found", loginForm.getUser());
            return false;
        }
        Login u = user.get(0);
        // XXX - Using Java's hashCode is wrong on SO many levels, but is good enough for demonstration purposes.
        // NEVER EVER do this in production code!
        final String userProvided = loginForm.getPassword();
        if (!u.getPassword().equals(userProvided)) {
            log.info("validateUser: {} password !match", loginForm.getUser());
            return false;
        }

        // User exists, and the provided password matches the hashed password in the database.
        log.info("validateUser: {} successful login", loginForm.getUser());
        return true;
    }

    /**
     * This is used to find the Login for a user with multiple services using them to
     * check if user is in the system before methods send back information
     * @param user
     * @return a Login object that can be used then to call methods.
     */

    @Override
    public Login findLogin(String user) {
        List<Login> logins = loginRepo.findByUserIgnoreCase(user);
        if (logins.isEmpty()) {
            return null;
        }
        log.info("Returning training {} from user", logins.get(0));
        return logins.get(0);
    }

}
