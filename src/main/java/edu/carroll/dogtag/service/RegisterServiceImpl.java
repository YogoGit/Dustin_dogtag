package edu.carroll.dogtag.service;

import edu.carroll.dogtag.jpa.model.Login;
import edu.carroll.dogtag.jpa.repo.RegisterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
     * @param user
     * @return true comes back that the user is already in the database.
     * false comes back in the user is not in the database. This is one of the
     * checks before a user can register
     */
    @Override
    public boolean userExists(String user) {
        // Always do the lookup in a case-insensitive manner (lower-casing the data).
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
     */
    @Override
    public boolean emailExists(String email) {
        List<Login> emails = registerRepo.findByEmailIgnoreCase(email);
        log.info("Checking if {} email exists", email);
        return !emails.isEmpty();
    }

    /**
     * register is used once all the information is correct being given to
     * this method to send to the database
     *
     * @param register
     * @return true if was successful in registering.
     */

    @Override
    public boolean register(Login register) {
        if (register.getUser() != null && !register.getUser().isBlank()
                //fix is blank
                && register.getPassword() != null && register.getPassword() != ""
                && register.getEmail() != null && register.getEmail() != "") {

            registerRepo.save(register);
            log.info("Register Info for {} sent to login table", register.getUser());
            return true;
        } else {
            log.info("Can can not be null or blank {}", register.getUser());
            return false;
        }
    }

    /**
     * Given a user this method is to delete a user for the database
     *
     * @param userEntityDelete
     */
    @Override
    public void deleteByUser(String userEntityDelete) {
        List<Login> userRowDelete = registerRepo.findByUserIgnoreCase(userEntityDelete);
        registerRepo.deleteAll(userRowDelete);
        log.info("User {} was deleted", userEntityDelete);
    }


}




