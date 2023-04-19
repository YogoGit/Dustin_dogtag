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
        log.info("validateUser: {} attempted login", loginForm.getUser());
        if (loginForm.getUser() == null || loginForm.getUser().isBlank()) {
            log.error("loginForm User was null or blank {}", loginForm.getUser());
            return false;
        }
        if (loginForm.getPassword() == null || loginForm.getPassword().isBlank()) {
            log.error("loginForm Password was null or blank for user {}", loginForm.getUser());
            return false;
        }
        log.info("validateUser: {} attempted login", loginForm.getUser());
        // Always do the lookup in a case-insensitive manner (lower-casing the data).
        List<Login> user = loginRepo.findByUserIgnoreCase(loginForm.getUser());

        if (user.size() != 1) {
            log.info("validateUser:{} users not found", user.size());
            return false;
        }
        Login u = user.get(0);
        final String userProvided = loginForm.getPassword();
        if (!u.getPassword().equals(userProvided)) {
            log.info("validateUser: {} password does not match", loginForm.getUser());
            return false;
        }
        log.info("validateUser: {} successful login", loginForm.getUser());
        return true;
    }

    /**
     * This is used to find the Login for a user with multiple services using them to
     * check if user is in the system before methods send back information
     *
     * @param user is what person is trying to log in this information is used to
     *             find all information pertaining to their login.
     * @return a Login object that can be used then to call methods.
     */

    @Override
    public Login findLogin(String user) {
        List<Login> logins = loginRepo.findByUserIgnoreCase(user);
        if (logins.isEmpty()) {
            log.info("findLogin: loging List was empty {}", logins.size());
            return null;
        }
        log.info("Returning Login user {}", logins.get(0).getUser());
        return logins.get(0);
    }
}
