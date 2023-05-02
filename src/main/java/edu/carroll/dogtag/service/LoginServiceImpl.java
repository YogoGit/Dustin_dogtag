package edu.carroll.dogtag.service;

import edu.carroll.dogtag.jpa.model.Login;
import edu.carroll.dogtag.jpa.repo.LoginRepository;
import edu.carroll.dogtag.web.form.LoginForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

/**
 * LoginService is used as the business logic for the LoginController. It uses the findLogin
 * to verify there is a user with that name in the database by returning a list of users that match
 * which should only return 1. This service takes care of validating a user that is attempting to log into the
 * application. It uses the methods to ensure the user has entered correct information and
 * that they are in the database. The service checks what the user has entered by creating a
 * hash of the password the same way that it does when the user registers. Then compares the password
 * in the database with the hashed user entry in the form.
 */
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
        if (findLogin(loginForm.getUser()) == null) {
            return false;
        }
        final String userProvidedPass = loginForm.getPassword();
        final String userProvidedLogin = loginForm.getUser();
        boolean comparedHash = hashCheck(userProvidedLogin, userProvidedPass);
        if (!comparedHash) {
            log.info("validateUser: {} password does not match", loginForm.getUser());
            return false;
        }
        log.info("validateUser: {} successful login", loginForm.getUser());
        return true;
    }

    /**
     * @param user        when the hash is checked the user is passed from the loginForm and is used to
     *                    find if there is a user and return false if not or continue with verifying the passwords
     *                    match.
     * @param rawPassword when the hash is checked the rawPassword is passed from the loginForm and is used to
     *                    hash the rawPassword  entered with the same hash
     *                    and then check the database for the same password as a string.
     *                    match.
     * @return true is returned if the passwords match and sent to the validate user method. If is false
     * with is sent back but then validate user is not correct and is dealt with accordingly.
     */
    private boolean hashCheck(String user, String rawPassword) {
        if (findLogin(user).equals(Collections.EMPTY_LIST)) {
            return false;
        }
        Login u = findLogin(user);
        final String userProvided = rawPassword;
        byte[] salt = Base64.getMimeDecoder().decode(u.getSalt());
        PBEKeySpec pbeKeySpec = new PBEKeySpec(userProvided.toCharArray(), salt, 10, 512);
        SecretKeyFactory skf = null;
        try {
            skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] hash = new byte[0];
        try {
            hash = skf.generateSecret(pbeKeySpec).getEncoded();
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        String base64Hash = Base64.getMimeEncoder().encodeToString(hash);
        String hash2 = u.getPassword();
        if (!hash2.equals(base64Hash)) {
            log.info("validateUser: {} password does not match", user);
            return false;
        }
        log.info("validateUser: {} successful login", user);
        return true;
    }

    /**
     * This is used to find the Login for a user with multiple services using them to
     * check if user is in the system before methods send back information
     *
     * @param user is what person is trying to log in this information is used to
     *             find all information pertaining to their login.
     * @return a Login object that can be used then to call methods.
     **/
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