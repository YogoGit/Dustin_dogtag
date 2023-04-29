package edu.carroll.dogtag.service;

import edu.carroll.dogtag.jpa.model.Login;
import edu.carroll.dogtag.jpa.repo.RegisterRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

/**
 * RegisterService is used as the business logic for the RegisterController. It checks if the user or
 * email or both already exist in the database in which they will not be able to register with those
 * entry's to verify there is not a user with that name in the database by returning a list of users and email
 * which should return and empty list. The service then hashes what the user has entered the
 * password field then is saves the salt and hash for that user.
 */
@Service
@Transactional
public class RegisterServiceImpl implements RegisterService {
    private static final Logger log = LoggerFactory.getLogger(RegisterServiceImpl.class);
    private final RegisterRepository registerRepo;
    private final SecureRandom secureRandom = new SecureRandom();
    private final LoginService loginService;

    public RegisterServiceImpl(RegisterRepository registerRepo, LoginService loginService) {
        this.registerRepo = registerRepo;
        this.loginService = loginService;
    }

    /**
     * Checks if a user is already in the database for allowing a user to
     * register or not with that name.
     *
     * @param user is entered in html form and passed to controller is going to use that field
     *             to check if that name is already in the database.
     * @return true comes back that the user is already in the database.
     * false comes back in the user is not in the database. This is one of the
     * checks before a user can register
     */
    @Override
    public boolean userExists(String user) {
        if (user == null || user.isBlank()) {
            return false;
        }
        List<Login> users = registerRepo.findByUserIgnoreCase(user);
        log.info("Checking if {} user exists", user);
        return !users.isEmpty();
    }

    /**
     * Checks if a email is already in the database for allowing email to
     * register or not with that name.
     *
     * @param email @return true comes back that the email is already in the database.
     *              false comes back in the user is not in the database. This is one of the
     *              checks before a user can register
     * @return true comes back that the email is already in the database.
     * false comes back in the email is not in the database. This is one of the
     * checks before a email can be register
     */
    @Override
    public boolean emailExists(String email) {
        if (email == null || email.isBlank()) {
            return false;
        }
        List<Login> emails = registerRepo.findByEmailIgnoreCase(email);
        log.info("Checking if {} email exists", email);
        return !emails.isEmpty();
    }

    /**
     * register is used once all the information is correct being given to
     * this method to send to the database
     *
     * @param register this is using the Login models methods to set all
     *                 information from the register form.
     * @return true if was successful in registering.
     */
    @Override
    public boolean register(Login register) {
        if (register.getUser() != null && !register.getUser().isBlank()
                && register.getPassword() != null && register.getPassword() != ""
                && register.getEmail() != null && register.getEmail() != "") {
            if (!loginService.findLogin(register.getUser()).equals(Collections.EMPTY_LIST)) {
                return false;
            }
            String passSalt[] = hashString(register.getPassword());
            register.setPassword(passSalt[0]);
            register.setSalt(passSalt[1]);
            registerRepo.save(register);
            log.info("Register Info for {} sent to login table", register.getUser());
            return true;
        } else {
            log.info("Can can not be null or blank {}", register.getUser());
            return false;
        }
    }

    /**
     * @param rawPassword Takes the rawPassword that is passed from the registerForm and is used to
     *                    hash the rawPassword with a salt and both are saved into the database for
     *                    that user.
     * @return Sends result to register as a String array to be parsed out and set for the users
     * password and salt.
     */
    private String[] hashString(String rawPassword) {
        String[] result = new String[2];

        byte[] salt = secureRandom.generateSeed(12);
        PBEKeySpec pbeKeySpec = new PBEKeySpec(rawPassword.toCharArray(), salt, 10, 512);

        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            ;
            byte[] hash = skf.generateSecret(pbeKeySpec).getEncoded();
            //converting to string to store into database
            result[0] = Base64.getMimeEncoder().encodeToString(hash);
            result[1] = Base64.getMimeEncoder().encodeToString(salt);
        } catch (NoSuchAlgorithmException e) {
            log.error("Failed to encrypt password", e);
        } catch (InvalidKeySpecException e) {
            log.error("Failed to SHA512 encryption", e);
        }
        return result;
    }
}




