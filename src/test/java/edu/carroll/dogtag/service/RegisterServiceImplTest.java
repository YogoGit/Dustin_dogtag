package edu.carroll.dogtag.service;

import edu.carroll.dogtag.jpa.model.Login;
import edu.carroll.dogtag.jpa.repo.RegisterRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(RegisterServiceImplTest.class);
    @Autowired
    private RegisterService registerService;

    @Autowired
    private RegisterRepository registerRepository;

    @Test
    public void userExistsTestNoUserHappy() {
        String noUser = "noUser";
        assertFalse("userExistsTest: should succeed when a user already exists", registerService.userExists(noUser));
    }

    @Test
    public void userExistsTest1UsersHappy() {
        Login userRegister = new Login();
        userRegister.setPassword(password);
        userRegister.setUser(username);
        userRegister.setEmail(email);
        registerService.register(userRegister);
        assertTrue("userExistsTest: should succeed when a user already exists", registerService.userExists(userRegister.getUser()));
    }

    @Test
    public void userExistsTest2UsersHappy() {
        Login userRegister = new Login();
        userRegister.setPassword(password);
        userRegister.setUser(username);
        userRegister.setEmail(email);
        registerService.register(userRegister);
        assertTrue("userExistsTest: should succeed when a user already exists", registerService.userExists(userRegister.getUser()));

        Login userRegister2 = new Login();
        userRegister2.setPassword(password + "2");
        userRegister2.setUser(username + "2");
        userRegister2.setEmail(email + "2");
        registerService.register(userRegister2);
        assertTrue("user2ExistsTest: should succeed when user2 already exists", registerService.userExists(userRegister2.getUser()));
    }

    @Test
    public void userExistsTest4UsersHappy() {
        Login userRegister = new Login();
        userRegister.setPassword(password);
        userRegister.setUser(username);
        userRegister.setEmail(email);
        registerService.register(userRegister);
        assertTrue("userExistsTest: should succeed when a user already exists", registerService.userExists(userRegister.getUser()));

        Login userRegister2 = new Login();
        userRegister2.setPassword(password + "2");
        userRegister2.setUser(username + "2");
        userRegister2.setEmail(email + "2");
        registerService.register(userRegister2);
        assertTrue("user2ExistsTest: should succeed when user2 already exists", registerService.userExists(userRegister2.getUser()));

        Login userRegister3 = new Login();
        userRegister3.setPassword(password + "3");
        userRegister3.setUser(username + "3");
        userRegister3.setEmail(email + "3");
        registerService.register(userRegister3);
        assertTrue("user3ExistsTest: should succeed when a user already exists", registerService.userExists(userRegister3.getUser()));

        Login userRegister4 = new Login();
        userRegister4.setPassword(password + "4");
        userRegister4.setUser(username + "4");
        userRegister4.setEmail(email + "4");
        registerService.register(userRegister4);
        assertTrue("user4ExistsTest: should succeed when user2 already exists", registerService.userExists(userRegister4.getUser()));
    }

    @Test
    public void userExistsTestHappy5() {
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
        assertFalse("userExistsTest: should fail when checking for an email existing instead of user existing", registerService.userExists(userRegister.getEmail()));
    }

    @Test
    public void userExistsTestBlankCrappy2() {
        Login userRegister = new Login();
        userRegister.setPassword(password);
        userRegister.setUser("");
        userRegister.setEmail(email);
        log.info("User should be blank {}", userRegister.getUser());
        registerService.register(userRegister);
        assertFalse("userExistsTest: should fail when a user is blank", registerService.userExists(userRegister.getUser()));
    }

    @Test
    public void userExistsTestNullUserCrazy1() {
        Login userRegister = new Login();
        userRegister.setPassword(password);
        userRegister.setUser(null);
        userRegister.setEmail(email);
        log.info("User should be null {}", userRegister.getUser());
        registerService.register(userRegister);
        assertFalse("userExistsTest: should fail when a user is null", registerService.userExists(userRegister.getUser()));
    }


    @Test
    public void emailExistsTestHappy1() {
        String noEmail = "noEmail";
        assertFalse("emailExistsTest: should fail when no email exists", registerService.emailExists(noEmail));
    }

    @Test
    public void emailExistsTestHappy2() {
        Login emailRegister = new Login();
        emailRegister.setPassword(password);
        emailRegister.setUser(username);
        emailRegister.setEmail(email);
        registerService.register(emailRegister);
        assertTrue("emailExistsTest: should succeed when a email already exists", registerService.emailExists(emailRegister.getEmail()));

    }

    @Test
    public void emailExistsTestHappy3() {
        Login emailRegister = new Login();
        emailRegister.setPassword(password);
        emailRegister.setUser(username);
        emailRegister.setEmail(email);
        registerService.register(emailRegister);
        assertTrue("emailExistsTest: should succeed when a email already exists", registerService.emailExists(emailRegister.getEmail()));

        Login emailRegister2 = new Login();
        emailRegister2.setPassword(password + "2");
        emailRegister2.setUser(username + "2");
        emailRegister2.setEmail(email + "2");
        registerService.register(emailRegister2);
        assertTrue("emailExistsTest: should succeed when a email2 already exists", registerService.emailExists(emailRegister2.getEmail()));
    }

    @Test
    public void emailsExistsTestHappy4() {
        Login emailRegister = new Login();
        emailRegister.setPassword(password);
        emailRegister.setUser(username);
        emailRegister.setEmail(email);
        registerService.register(emailRegister);
        assertTrue("emailExistsTest: should succeed when a email already exists", registerService.emailExists(emailRegister.getEmail()));

        Login emailRegister2 = new Login();
        emailRegister2.setPassword(password + "2");
        emailRegister2.setUser(username + "2");
        emailRegister2.setEmail(email + "2");
        registerService.register(emailRegister2);
        assertTrue("emailExistsTest: should succeed when a email already exists", registerService.emailExists(emailRegister2.getEmail()));

        Login emailRegister3 = new Login();
        emailRegister3.setPassword(password + "3");
        emailRegister3.setUser(username + "3");
        emailRegister3.setEmail(email + "3");
        registerService.register(emailRegister3);
        assertTrue("emailExistsTest: should succeed when a email already exists", registerService.emailExists(emailRegister3.getEmail()));

        Login emailRegister4 = new Login();
        emailRegister4.setPassword(password + "4");
        emailRegister4.setUser(username + "4");
        emailRegister4.setEmail(email + "4");
        registerService.register(emailRegister4);
        assertTrue("emailExistsTest: should succeed when a email already exists", registerService.emailExists(emailRegister4.getEmail()));
    }

    @Test
    public void emailsExistsTestCrappy1() {
        Login emailRegister = new Login();
        emailRegister.setPassword(password);
        emailRegister.setUser(username);
        emailRegister.setEmail(email);
        registerService.register(emailRegister);
        assertFalse("emailExistsTest: should fail when checking if a user exists instead of an email", registerService.emailExists(emailRegister.getUser()));

    }

    @Test
    public void userEmailTestBlankCrappy2() {
        Login userRegister = new Login();
        userRegister.setPassword(password);
        userRegister.setUser(username);
        userRegister.setEmail("");
        log.info("Email should be blank {}", userRegister.getEmail());
        registerService.register(userRegister);
        assertFalse("userExistsTest: should fail when a user is blank", registerService.userExists(userRegister.getEmail()));
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

    @Test
    public void userPasswordTestBlankCrappy1() {
        Login userRegister = new Login();
        userRegister.setPassword("");
        userRegister.setUser(username);
        userRegister.setEmail(email);
        log.info("User should be blank {}", userRegister.getUser());
        registerService.register(userRegister);
        assertFalse("userPasswordTestBlankCrappy2: should fail when a user is blank", registerService.userExists(userRegister.getPassword()));
    }

    @Test
    public void userPasswordTestNullPasswordCrazy1() {
        Login userRegister = new Login();
        userRegister.setPassword(null);
        userRegister.setUser(username);
        userRegister.setEmail(email);
        log.info("Password should be null {}", userRegister.getUser());
        registerService.register(userRegister);
        assertFalse("userPasswordTestNullPasswordCrazy1: should fail when a password is null", registerService.userExists(userRegister.getPassword()));
    }


}