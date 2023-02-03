package edu.carroll.dogtag.service;

import java.util.List;

import edu.carroll.dogtag.jpa.model.database.Auth;
import edu.carroll.dogtag.jpa.repo.LoginRepository;
import edu.carroll.dogtag.web.form.LoginForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
        log.info("validateUser: user '{}' attempted login", loginForm.getUser());
        // Always do the lookup in a case-insensitive manner (lower-casing the data).
        List<Auth> user = loginRepo.findByUserIgnoreCase(loginForm.getUser());

        // We expect 0 or 1, so if we get more than 1, bail out as this is an error we don't deal with properly.
        if (user.size() != 1) {
            log.debug("validateUser: found {} users", user.size());
            return false;
        }
        Auth u = user.get(0);
        // XXX - Using Java's hashCode is wrong on SO many levels, but is good enough for demonstration purposes.
        // NEVER EVER do this in production code!
        final String userProvided = loginForm.getPassword();
        if (!u.getPassword().equals(userProvided)) {
            log.debug("validateUser: password !match");
            return false;
        }

        // User exists, and the provided password matches the hashed password in the database.
        log.debug("validateUser: successful login");
        return true;
    }
}
