package edu.carroll.dogtag.service;

import edu.carroll.dogtag.jpa.model.Login;
import edu.carroll.dogtag.jpa.repo.RegisterRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
@Transactional
class RegisterServiceImplTest {
    private static final String username = "usertest";
    private static final String usernameShort = "user";

    private static final String password = "passwordtest";
    private static final String passwordShort = "pass";

    private static final String email = "emailTest@gmail.com";


    @Autowired
    private RegisterService registerService;

    @Autowired
    private RegisterRepository registerRepository;

    @Test
    public void userExistsTestHappy1() {
        Login userRegister = new Login();
        userRegister.setPassword(password);
        userRegister.setUser(username);
        userRegister.setEmail(email);
        registerService.register(userRegister);
        assertTrue("userExistsTest: should succeed when a user already exists", registerService.userExists(userRegister.getUser()));
    }

    @Test
    public void userExistsTestHappy2() {
        Login userRegister = new Login();
        userRegister.setPassword(password);
        userRegister.setUser(username);
        userRegister.setEmail(email);
        registerService.register(userRegister);
        String userNotFound = "userNotFound";
        assertFalse("userExistsTest: should fail when a user is not found in database", registerService.userExists(userNotFound));
    }
    @Test
    public void userExistsTestCrappy1() {
        Login userRegister = new Login();
        userRegister.setPassword(password);
        userRegister.setUser(username);
        userRegister.setEmail(email);
        registerService.register(userRegister);
        assertFalse("userExistsTest: should fail when checking for an email instead of user", registerService.userExists(userRegister.getEmail()));
    }

    @Test
    public void emailsExistsTestHappy1() {
        Login emailRegister = new Login();
        emailRegister.setPassword(password);
        emailRegister.setUser(username);
        emailRegister.setEmail(email);
        registerService.register(emailRegister);
        assertTrue("emailExistsTest: should succeed when a email already exists", registerService.emailExists(emailRegister.getEmail()));

    }
    @Test
    public void emailsExistsTestHappy2() {
        Login emailRegister = new Login();
        emailRegister.setPassword(password);
        emailRegister.setUser(username);
        emailRegister.setEmail(email);
        registerService.register(emailRegister);
        String emailNotFound = "emailNotFound";
        assertFalse("emailExistsTest: should fail when email is not found", registerService.emailExists(emailNotFound));

    }

    @Test
    public void emailsExistsTestCrappy1() {
        Login emailRegister = new Login();
        emailRegister.setPassword(password);
        emailRegister.setUser(username);
        emailRegister.setEmail(email);
        registerService.register(emailRegister);
        assertFalse("emailExistsTest: should fail when user instead of an email", registerService.emailExists(emailRegister.getUser()));

    }

    @Test
    public void deleteCreatedUserTestHappy1() {
        Login userRegisterDelete = new Login();
        userRegisterDelete.setPassword(password);
        userRegisterDelete.setEmail(email);
        userRegisterDelete.setUser(username);
        registerService.register(userRegisterDelete);
        assertTrue("deleteCreatedUserTest: should succeed creating a user", registerService.userExists(userRegisterDelete.getUser()));
        registerService.deleteByUser(userRegisterDelete.getUser());
        assertFalse("deleteCreatedUserTest: should succeeded deleting because no longer not found", registerService.userExists(userRegisterDelete.getUser()));
    }

    @Test
    public void deleteCreatedUserTestHappy2() {
        Login userRegisterDelete = new Login();
        userRegisterDelete.setPassword(password);
        userRegisterDelete.setEmail(email);
        userRegisterDelete.setUser(username);
        registerService.register(userRegisterDelete);
        assertTrue("deleteCreatedUserTest: should succeed creating a user", registerService.userExists(userRegisterDelete.getUser()));
        String noUserToDelete = "noUserToDelete";
        registerService.deleteByUser(noUserToDelete);
        assertTrue("deleteCreatedUserTest: should fail because user was not deleted", registerService.userExists(userRegisterDelete.getUser()));
    }

}