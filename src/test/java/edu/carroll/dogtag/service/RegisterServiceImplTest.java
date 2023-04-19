package edu.carroll.dogtag.service;

import edu.carroll.dogtag.jpa.model.Login;
import edu.carroll.dogtag.jpa.repo.RegisterRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
@Transactional
class RegisterServiceImplTest {
    private static final String username = "usertest";
    private static final String usernameShort = "uShrt";
    private static final String password = "passwordtest";
    private static final String passwordShort = "pShort";
    private static final String email = "emailTest@gmail.com";
    private static final String emailInvalid = "emailInvalid";
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

    //Add boolean test updates
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

        Login userRegister2 = new Login();
        userRegister2.setPassword(password + "2");
        userRegister2.setUser(username + "2");
        userRegister2.setEmail(email + "2");
        registerService.register(userRegister2);

        assertTrue("userExistsTest: should succeed when a user already exists", registerService.userExists(userRegister.getUser()));
        assertTrue("user2ExistsTest: should succeed when user2 already exists", registerService.userExists(userRegister2.getUser()));
    }

    @Test
    public void userExistsTest4UsersHappy() {
        Login userRegister = new Login();
        userRegister.setPassword(password);
        userRegister.setUser(username);
        userRegister.setEmail(email);
        registerService.register(userRegister);

        Login userRegister2 = new Login();
        userRegister2.setPassword(password + "2");
        userRegister2.setUser(username + "2");
        userRegister2.setEmail(email + "2");
        registerService.register(userRegister2);

        Login userRegister3 = new Login();
        userRegister3.setPassword(password + "3");
        userRegister3.setUser(username + "3");
        userRegister3.setEmail(email + "3");
        registerService.register(userRegister3);

        Login userRegister4 = new Login();
        userRegister4.setPassword(password + "4");
        userRegister4.setUser(username + "4");
        userRegister4.setEmail(email + "4");
        registerService.register(userRegister4);

        assertTrue("userExistsTest: should succeed when a user already exists", registerService.userExists(userRegister.getUser()));
        assertTrue("user2ExistsTest: should succeed when user2 already exists", registerService.userExists(userRegister2.getUser()));
        assertTrue("user3ExistsTest: should succeed when a user already exists", registerService.userExists(userRegister3.getUser()));
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

        Login emailRegister2 = new Login();
        emailRegister2.setPassword(password + "2");
        emailRegister2.setUser(username + "2");
        emailRegister2.setEmail(email + "2");
        registerService.register(emailRegister2);

        assertTrue("emailExistsTest: should succeed when a email already exists", registerService.emailExists(emailRegister.getEmail()));
        assertTrue("emailExistsTest: should succeed when a email2 already exists", registerService.emailExists(emailRegister2.getEmail()));
    }

    @Test
    public void emailsExistsTestHappy4() {
        Login emailRegister = new Login();
        emailRegister.setPassword(password);
        emailRegister.setUser(username);
        emailRegister.setEmail(email);
        registerService.register(emailRegister);

        Login emailRegister2 = new Login();
        emailRegister2.setPassword(password + "2");
        emailRegister2.setUser(username + "2");
        emailRegister2.setEmail(email + "2");
        registerService.register(emailRegister2);

        Login emailRegister3 = new Login();
        emailRegister3.setPassword(password + "3");
        emailRegister3.setUser(username + "3");
        emailRegister3.setEmail(email + "3");
        registerService.register(emailRegister3);

        Login emailRegister4 = new Login();
        emailRegister4.setPassword(password + "4");
        emailRegister4.setUser(username + "4");
        emailRegister4.setEmail(email + "4");
        registerService.register(emailRegister4);

        assertTrue("emailExistsTest: should succeed when a email already exists", registerService.emailExists(emailRegister.getEmail()));
        assertTrue("emailExistsTest: should succeed when a email already exists", registerService.emailExists(emailRegister2.getEmail()));
        assertTrue("emailExistsTest: should succeed when a email already exists", registerService.emailExists(emailRegister3.getEmail()));
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
        assertFalse("userExistsTest: should fail when a email is blank", registerService.userExists(userRegister.getEmail()));
    }

    @Test
    public void userEmailTestNullEmailCrazy1() {
        Login userRegister = new Login();
        userRegister.setPassword(password);
        userRegister.setUser(username);
        userRegister.setEmail(null);
        log.info("User should be null {}", userRegister.getUser());
        registerService.register(userRegister);
        assertFalse("userExistsTest: should fail when a email is null", registerService.userExists(userRegister.getUser()));
    }

    @Test
    public void deleteCreatedUserTestHappy1() {
        Login userRegisterDelete = new Login();
        userRegisterDelete.setPassword(password);
        userRegisterDelete.setEmail(email);
        userRegisterDelete.setUser(username);
        registerService.register(userRegisterDelete);
        assertTrue("deleteCreatedUserTest: should succeed creating a user", registerService.userExists(userRegisterDelete.getUser()));
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
       }

    @Test
    public void userPasswordTestGoodPasswordHappy1() {
        Login userRegister = new Login();
        userRegister.setPassword(password);
        userRegister.setUser(username);
        userRegister.setEmail(email);
        log.info("password should be {}", userRegister.getPassword());
        registerService.register(userRegister);
        List<Login> users = registerRepository.findByUserIgnoreCase(username);
        String pass = users.get(0).getPassword();
        assertTrue("userPasswordTestBlankCrappy2: should pass when a password is correctly created", password == pass);
        assertTrue("userRegister should pass with correct register passed", registerService.userExists(userRegister.getUser()));
    }

    @Test
    public void userPasswordTestBlankCrappy1() {
        Login userRegister = new Login();
        userRegister.setPassword("");
        userRegister.setUser(username);
        userRegister.setEmail(email);
        log.info("User should be blank {}", userRegister.getPassword());
        registerService.register(userRegister);
        assertFalse("userPasswordTestBlankCrappy2: should fail when a user is blank", registerService.userExists(userRegister.getUser()));
    }

    @Test
    public void userPasswordTestNullPasswordCrazy1() {
        Login userRegister = new Login();
        userRegister.setPassword(null);
        userRegister.setUser(username);
        userRegister.setEmail(email);
        log.info("Password should be null {}", userRegister.getPassword());
        registerService.register(userRegister);
        assertFalse("userPasswordTestNullPasswordCrazy1: should fail when a password is null", registerService.userExists(userRegister.getPassword()));
    }

    @Test
    public void userPasswordTestPasswdUserNullCrazy() {
        Login userRegister = new Login();
        userRegister.setPassword(null);
        userRegister.setUser(null);
        userRegister.setEmail(email);
        log.info("Password should be null {}", userRegister.getPassword());
        registerService.register(userRegister);
        assertFalse("userPasswordTestNullPasswordCrazy1: should fail when a password is null", registerService.userExists(userRegister.getPassword()));
        assertFalse("userPasswordTestNullPasswordCrazy1: should fail when a password is null", registerService.userExists(userRegister.getUser()));
        assertFalse("userPasswordTestNullPasswordCrazy1: should fail when a password is null", registerService.userExists(userRegister.getEmail()));
    }

    @Test
    public void userPasswordTestEmailUserNullCrazy() {
        Login userRegister = new Login();
        userRegister.setPassword(password);
        userRegister.setUser(null);
        userRegister.setEmail(null);
        log.info("Password should be null {}", userRegister.getPassword());
        registerService.register(userRegister);
        assertFalse("userPasswordTestNullPasswordCrazy1: should fail when a password is null", registerService.userExists(userRegister.getPassword()));
        assertFalse("userPasswordTestNullPasswordCrazy1: should fail when a password is null", registerService.userExists(userRegister.getUser()));
        assertFalse("userPasswordTestNullPasswordCrazy1: should fail when a password is null", registerService.userExists(userRegister.getEmail()));
    }

    @Test
    public void userPasswordTestAllNullCrazy() {
        Login userRegister = new Login();
        userRegister.setPassword(null);
        userRegister.setUser(null);
        userRegister.setEmail(null);
        log.info("Password should be null {}", userRegister.getPassword());
        registerService.register(userRegister);
        assertFalse("userPasswordTestNullPasswordCrazy1: should fail when a password is null", registerService.userExists(userRegister.getPassword()));
        assertFalse("userPasswordTestNullPasswordCrazy1: should fail when a password is null", registerService.userExists(userRegister.getUser()));
        assertFalse("userPasswordTestNullPasswordCrazy1: should fail when a password is null", registerService.userExists(userRegister.getEmail()));
    }

}