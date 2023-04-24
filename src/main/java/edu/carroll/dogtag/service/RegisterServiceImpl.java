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
import java.util.List;

@Service
@Transactional
public class RegisterServiceImpl implements RegisterService {
    private static final Logger log = LoggerFactory.getLogger(RegisterServiceImpl.class);
    private final RegisterRepository registerRepo;

    public RegisterServiceImpl(RegisterRepository registerRepo) {
        this.registerRepo = registerRepo;
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
    public boolean register(Login register) throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (register.getUser() != null && !register.getUser().isBlank()
                && register.getPassword() != null && register.getPassword() != ""
                && register.getEmail() != null && register.getEmail() != "") {

            SecureRandom secureRandom = new SecureRandom();
            byte[] salt = secureRandom.generateSeed(12);
            PBEKeySpec pbeKeySpec = new PBEKeySpec(register.getPassword().toCharArray(), salt, 10, 512);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            byte[] hash = skf.generateSecret(pbeKeySpec).getEncoded();

            //converting to string to store into database
            String base64Hash = Base64.getMimeEncoder().encodeToString(hash);
            String base64Salt = Base64.getMimeEncoder().encodeToString(salt);
            register.setSalt(base64Salt);
            register.setPassword(base64Hash);
            log.info(base64Hash);


            registerRepo.save(register);
            log.info("Register Info for {} sent to login table", register.getUser());
            return true;
        } else {
            log.info("Can can not be null or blank {}", register.getUser());
            return false;
        }
    }

}




