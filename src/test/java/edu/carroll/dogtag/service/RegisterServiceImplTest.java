package edu.carroll.dogtag.service;

import edu.carroll.dogtag.jpa.model.Login;
import edu.carroll.dogtag.web.form.LoginForm;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.util.AssertionErrors.*;

/**
 * Unit test to pass happy, crappy and crazy inputs to the RegisterService, and
 * it should handle all situation that are tested and return the expected results.
 * A login must be checked to verify the user had been successfully saved into the database
 * If no user is present in the database it will always come back with null for returning
 * a Login object. This indicates that the user was not able to register and is not in the
 * database
 */
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
    private LoginService loginService;

    @Test
    public void userExistsTestNoUserHappy() {
        String noUser = "noUser";
        assertFalse("userExistsTest: should fail when a user does not exist", registerService.userExists(noUser));
    }

    //Add boolean test updates
    @Test
    public void userExistsTest1UsersHappy() {
        Login userRegister = new Login();
        userRegister.setPassword(password);
        userRegister.setUser(username);
        userRegister.setEmail(email);
        assertTrue("Register should return true {}", registerService.register(userRegister));
        assertNotNull("Should find a user and return a login", loginService.findLogin(username));
        assertTrue("userExistsTest: should succeed when a user already exists", registerService.userExists(userRegister.getUser()));
    }

    @Test
    public void userExistsTest2UsersHappy() {
        Login userRegister = new Login();
        userRegister.setPassword(password);
        userRegister.setUser(username);
        userRegister.setEmail(email);
        Login userRegister2 = new Login();
        userRegister2.setPassword(password + "2");
        userRegister2.setUser(username + "2");
        userRegister2.setEmail(email + "2");
        assertTrue("Register should return true {}", registerService.register(userRegister));
        assertTrue("Register2 should return true {}", registerService.register(userRegister2));
        assertNotNull("Should find user1 and return a login", loginService.findLogin(username));
        assertNotNull("Should find user2 and return a login", loginService.findLogin(username + "2"));
        assertTrue("userExistsTest: should succeed when a user already exists", registerService.userExists(userRegister.getUser()));
        assertTrue("user2ExistsTest: should succeed when user2 already exists", registerService.userExists(userRegister2.getUser()));
    }

    @Test
    public void userExistsTest4UsersHappy() {
        Login userRegister = new Login();
        userRegister.setPassword(password);
        userRegister.setUser(username);
        userRegister.setEmail(email);
        assertTrue("Register should return true {}", registerService.register(userRegister));

        Login userRegister2 = new Login();
        userRegister2.setPassword(password + "2");
        userRegister2.setUser(username + "2");
        userRegister2.setEmail(email + "2");
        assertTrue("Register2 should return true {}", registerService.register(userRegister2));

        Login userRegister3 = new Login();
        userRegister3.setPassword(password + "3");
        userRegister3.setUser(username + "3");
        userRegister3.setEmail(email + "3");
        assertTrue("Register3 should return true {}", registerService.register(userRegister3));

        Login userRegister4 = new Login();
        userRegister4.setPassword(password + "4");
        userRegister4.setUser(username + "4");
        userRegister4.setEmail(email + "4");
        assertTrue("Register should return true {}", registerService.register(userRegister4));

        assertTrue("userExistsTest: should succeed when a user already exists", registerService.userExists(userRegister.getUser()));
        assertTrue("user2ExistsTest: should succeed when user2 already exists", registerService.userExists(userRegister2.getUser()));
        assertTrue("user3ExistsTest: should succeed when a user3 already exists", registerService.userExists(userRegister3.getUser()));
        assertTrue("user4ExistsTest: should succeed when user4 already exists", registerService.userExists(userRegister4.getUser()));
    }

    @Test
    public void userExistsTestHappy5() {
        Login userRegister = new Login();
        userRegister.setPassword(password);
        userRegister.setUser(username);
        userRegister.setEmail(email);
        assertTrue("Register should return true {}", registerService.register(userRegister));
        String userNotFound = "userNotFound";
        assertFalse("userExistsTest: should fail when a user is not found in database", registerService.userExists(userNotFound));
    }

    @Test
    public void userExistsTestCrappy1() {
        Login userRegister = new Login();
        userRegister.setPassword(password);
        userRegister.setUser(username);
        userRegister.setEmail(email);
        assertTrue("Register should return true {}", registerService.register(userRegister));
        assertFalse("userExistsTest: should fail when checking for an email existing instead of user existing", registerService.userExists(userRegister.getEmail()));
    }

    @Test
    public void userExistsTestBlankCrappy2() {
        Login userRegister = new Login();
        userRegister.setPassword(password);
        userRegister.setUser("");
        userRegister.setEmail(email);
        assertFalse("Register should return false with blank setuser {}", registerService.register(userRegister));
        assertFalse("userExistsTest: should fail when a user is blank", registerService.userExists(userRegister.getUser()));
    }

    @Test
    public void userExistsTestNullUserCrazy1() {
        Login userRegister = new Login();
        userRegister.setPassword(password);
        userRegister.setUser(null);
        userRegister.setEmail(email);
        assertFalse("Register should return false with null set user {}", registerService.register(userRegister));
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
        assertTrue("Register should return true {}", registerService.register(emailRegister));
        assertTrue("emailExistsTest: should succeed when a email already exists", registerService.emailExists(emailRegister.getEmail()));

    }

    @Test
    public void emailExistsTestHappy3() {
        Login emailRegister = new Login();
        emailRegister.setPassword(password);
        emailRegister.setUser(username);
        emailRegister.setEmail(email);
        assertTrue("Register should return true {}", registerService.register(emailRegister));

        Login emailRegister2 = new Login();
        emailRegister2.setPassword(password + "2");
        emailRegister2.setUser(username + "2");
        emailRegister2.setEmail(email + "2");
        assertTrue("Register2 should return true {}", registerService.register(emailRegister2));

        assertTrue("emailExistsTest: should succeed when a email already exists", registerService.emailExists(emailRegister.getEmail()));
        assertTrue("emailExistsTest: should succeed when a email2 already exists", registerService.emailExists(emailRegister2.getEmail()));
    }

    @Test
    public void emailsExistsTestHappy4() {
        Login emailRegister = new Login();
        emailRegister.setPassword(password);
        emailRegister.setUser(username);
        emailRegister.setEmail(email);
        assertTrue("Register should return true {}", registerService.register(emailRegister));

        Login emailRegister2 = new Login();
        emailRegister2.setPassword(password + "2");
        emailRegister2.setUser(username + "2");
        emailRegister2.setEmail(email + "2");
        assertTrue("Register2 should return true {}", registerService.register(emailRegister2));

        Login emailRegister3 = new Login();
        emailRegister3.setPassword(password + "3");
        emailRegister3.setUser(username + "3");
        emailRegister3.setEmail(email + "3");
        assertTrue("Register3 should return true {}", registerService.register(emailRegister3));

        Login emailRegister4 = new Login();
        emailRegister4.setPassword(password + "4");
        emailRegister4.setUser(username + "4");
        emailRegister4.setEmail(email + "4");
        assertTrue("Register4 should return true {}", registerService.register(emailRegister4));

        assertTrue("emailExistsTest: should succeed when a email already exists", registerService.emailExists(emailRegister.getEmail()));
        assertTrue("emailExistsTest: should succeed when a email2 already exists", registerService.emailExists(emailRegister2.getEmail()));
        assertTrue("emailExistsTest: should succeed when a email3 already exists", registerService.emailExists(emailRegister3.getEmail()));
        assertTrue("emailExistsTest: should succeed when a email4 already exists", registerService.emailExists(emailRegister4.getEmail()));
    }

    @Test
    public void emailsExistsTestCrappy1() {
        Login emailRegister = new Login();
        emailRegister.setPassword(password);
        emailRegister.setUser(username);
        emailRegister.setEmail(email);
        assertTrue("Register should return true {}", registerService.register(emailRegister));
        assertFalse("emailExistsTest: should fail when checking if a user exists instead of an email", registerService.emailExists(emailRegister.getUser()));

    }

    @Test
    public void userEmailTestBlankCrappy2() {
        Login userRegister = new Login();
        userRegister.setPassword(password);
        userRegister.setUser(username);
        userRegister.setEmail("");
        assertFalse("Register should return false with setEmail blank {}", registerService.register(userRegister));
        assertFalse("userExistsTest: should fail when a email is blank", registerService.userExists(userRegister.getEmail()));
    }

    @Test
    public void userEmailTestNullEmailCrazy1() {
        Login userRegister = new Login();
        userRegister.setPassword(password);
        userRegister.setUser(username);
        userRegister.setEmail(null);
        assertFalse("Register should return false with setEmail is null {}", registerService.register(userRegister));
        assertFalse("userExistsTest: should fail when a email is null", registerService.userExists(userRegister.getUser()));
    }

    @Test
    public void userPasswordTestGoodPasswordHappy1() {
        Login userRegister = new Login();
        userRegister.setPassword(password);
        userRegister.setUser(username);
        userRegister.setEmail(email);
        assertTrue("Register should return true {}", registerService.register(userRegister));
        LoginForm loginForm = new LoginForm(username, password);
        assertTrue("userPasswordTestBlankCrappy2: should pass when a password is correctly created", loginService.validateUser(loginForm));
        assertTrue("userRegister should pass with correct register passed", registerService.userExists(userRegister.getUser()));
    }

    @Test
    public void userPasswordTestBlankCrappy1() {
        Login userRegister = new Login();
        userRegister.setPassword("");
        userRegister.setUser(username);
        userRegister.setEmail(email);
        assertFalse("Register should return false with blank setPassword {}", registerService.register(userRegister));
        assertFalse("userPasswordTestBlankCrappy2: should fail when a user is blank", registerService.userExists(userRegister.getUser()));
    }

    @Test
    public void userPasswordTestNullPasswordCrazy1() {
        Login userRegister = new Login();
        userRegister.setPassword(null);
        userRegister.setUser(username);
        userRegister.setEmail(email);
        assertFalse("Register should return false with setPassword null {}", registerService.register(userRegister));
        assertFalse("userPasswordTestNullPasswordCrazy1: should fail when a password is null", registerService.userExists(userRegister.getPassword()));
    }

    @Test
    public void userPasswordTestPasswdUserNullCrazy() {
        Login userRegister = new Login();
        userRegister.setPassword(null);
        userRegister.setUser(null);
        userRegister.setEmail(email);
        assertFalse("Register should return true {}", registerService.register(userRegister));
        assertFalse("userPasswordTestNullPasswordCrazy1: should fail when a password and user is null", registerService.userExists(userRegister.getPassword()));
        assertFalse("userPasswordTestNullPasswordCrazy1: should fail when a password is null and user is null", registerService.userExists(userRegister.getUser()));
        assertFalse("userPasswordTestNullPasswordCrazy1: should fail when a password is null and user is null", registerService.userExists(userRegister.getEmail()));
    }

    @Test
    public void userPasswordTestEmailUserNullCrazy() {
        Login userRegister = new Login();
        userRegister.setPassword(password);
        userRegister.setUser(null);
        userRegister.setEmail(null);
        assertFalse("Register should return true {}", registerService.register(userRegister));
        assertFalse("userPasswordTestNullPasswordCrazy1: should fail when a user and email is null", registerService.userExists(userRegister.getPassword()));
        assertFalse("userPasswordTestNullPasswordCrazy1: should fail when a user and email is null", registerService.userExists(userRegister.getUser()));
        assertFalse("userPasswordTestNullPasswordCrazy1: should fail when a user and email is null", registerService.userExists(userRegister.getEmail()));
    }

    @Test
    public void userPasswordTestAllNullCrazy() {
        Login userRegister = new Login();
        userRegister.setPassword(null);
        userRegister.setUser(null);
        userRegister.setEmail(null);
        assertFalse("Register should return false when all is null {}", registerService.register(userRegister));
        assertFalse("userPasswordTestNullPasswordCrazy1: should fail when a password is null", registerService.userExists(userRegister.getPassword()));
        assertFalse("userPasswordTestNullPasswordCrazy1: should fail when a password is null", registerService.userExists(userRegister.getUser()));
        assertFalse("userPasswordTestNullPasswordCrazy1: should fail when a password is null", registerService.userExists(userRegister.getEmail()));
    }

    @Test
    public void register() {
        Login userRegister = new Login();
        userRegister.setPassword(password);
        userRegister.setUser(username);
        userRegister.setEmail(email);
        assertTrue("Register should return true {}", registerService.register(userRegister));
        assertNotNull("Should find a user and return a login", loginService.findLogin(username));
        assertEquals("User should return {}", loginService.findLogin(username).getUser(), username);
    }

    @Test
    public void register2() {
        Login userRegister = new Login();
        userRegister.setPassword(password);
        userRegister.setUser(username);
        userRegister.setEmail(email);
        assertTrue("Register should return true {}", registerService.register(userRegister));
        assertNotNull("Should find a user and return a login", loginService.findLogin(username));
        Login userRegister2 = new Login();
        userRegister2.setPassword(password + "2");
        userRegister2.setUser(username + "2");
        userRegister2.setEmail(email + "2");
        assertTrue("Register should return true {}", registerService.register(userRegister2));
        assertNotNull("Should find a user and return a login", loginService.findLogin(username));
        assertEquals("User should return {}", loginService.findLogin(username).getUser(), username);
        assertEquals("User should return {}", loginService.findLogin(username + "2").getUser(), username + "2");
    }

    @Test
    public void register3() {
        Login userRegister = new Login();
        userRegister.setPassword(password);
        userRegister.setUser(username);
        userRegister.setEmail(email);
        assertTrue("Register should return true {}", registerService.register(userRegister));
        assertNotNull("Should find a user and return a login", loginService.findLogin(username));
        Login userRegister2 = new Login();
        userRegister2.setPassword(password + "2");
        userRegister2.setUser(username + "2");
        userRegister2.setEmail(email + "2");
        assertTrue("Register should return true {}", registerService.register(userRegister2));
        assertNotNull("Should find a user and return a login", loginService.findLogin(username));
        Login userRegister3 = new Login();
        userRegister3.setPassword(password + "3");
        userRegister3.setUser(username + "3");
        userRegister3.setEmail(email + "3");
        assertTrue("Register should return true {}", registerService.register(userRegister3));
        assertNotNull("Should find a user and return a login", loginService.findLogin(username));
        assertEquals("User should return {}", loginService.findLogin(username).getUser(), username);
        assertEquals("User should return {}", loginService.findLogin(username + "2").getUser(), username + "2");
        assertEquals("User should return {}", loginService.findLogin(username + "3").getUser(), username + "3");
    }

    @Test
    public void registerCrappy() {
        Login userRegister = new Login();
        userRegister.setPassword(password);
        userRegister.setUser(username);
        userRegister.setEmail("");
        assertFalse("Register should return true {}", registerService.register(userRegister));
        assertNull("Should find a user and return a login", loginService.findLogin(username));
    }

    @Test
    public void registerCrappy2() {
        Login userRegister = new Login();
        userRegister.setPassword(password);
        userRegister.setUser(username);
        userRegister.setEmail(null);
        assertFalse("Register should return true {}", registerService.register(userRegister));
        assertNull("Should find a user and return a login", loginService.findLogin(username));
        Login userRegister2 = new Login();
        userRegister2.setPassword(password);
        userRegister.setUser(username);
        userRegister.setEmail(null);
        assertFalse("Register should return true {}", registerService.register(userRegister));
        assertNull("Should find a user and return a login", loginService.findLogin(username));
    }

    @Test
    public void register2Crappy() {
        Login userRegister = new Login();
        userRegister.setPassword(password);
        userRegister.setUser(username);
        userRegister.setEmail(email);
        assertTrue("Register should return true {}", registerService.register(userRegister));
        assertNotNull("Should find a user and return a login", loginService.findLogin(username));
        Login userRegister2 = new Login();
        userRegister2.setPassword(password + "2");
        userRegister2.setUser(username);
        userRegister2.setEmail(email + "2");
        assertFalse("Register should return false {}", registerService.register(userRegister2));
        assertNotNull("Should find a user and return a login", loginService.findLogin(username));
    }

    @Test
    public void register3Crappy() {
        Login userRegister = new Login();
        userRegister.setPassword(password);
        userRegister.setUser(username);
        userRegister.setEmail(email);
        assertTrue("Register should return true {}", registerService.register(userRegister));
        assertNotNull("Should find a user and return a login", loginService.findLogin(username));
        Login userRegister2 = new Login();
        userRegister2.setPassword(password + "2");
        userRegister2.setUser(username + "2");
        userRegister2.setEmail(email);
        assertFalse("Register should return false {}", registerService.register(userRegister2));
        assertNotNull("Should find a user and return a login", loginService.findLogin(username));
        Login userRegister3 = new Login();
        userRegister3.setPassword(password + "3");
        userRegister3.setUser(username + "3");
        userRegister3.setEmail(email + "3");
        assertTrue("Register should return true {}", registerService.register(userRegister3));
        assertNotNull("Should find a user and return a login", loginService.findLogin(username));
    }

    @Test
    public void register3CrappyUser() {
        Login userRegister = new Login();
        userRegister.setPassword(password);
        userRegister.setUser(username);
        userRegister.setEmail(email);
        assertTrue("Register should return true {}", registerService.register(userRegister));
        assertNotNull("Should find a user and return a login", loginService.findLogin(username));
        Login userRegister2 = new Login();
        userRegister2.setPassword(password + "2");
        userRegister2.setUser(username);
        userRegister2.setEmail(email + "2");
        assertFalse("Register should return false {}", registerService.register(userRegister2));
        assertNotNull("Should find a user and return a login", loginService.findLogin(username));
        Login userRegister3 = new Login();
        userRegister3.setPassword(password + "3");
        userRegister3.setUser(username + "3");
        userRegister3.setEmail(email + "3");
        assertTrue("Register should return true {}", registerService.register(userRegister3));
        assertNotNull("Should find a user and return a login", loginService.findLogin(username));
    }

    @Test
    public void register3CrappyUserandEmail() {
        Login userRegister = new Login();
        userRegister.setPassword(password);
        userRegister.setUser(username);
        userRegister.setEmail(email);
        assertTrue("Register should return true {}", registerService.register(userRegister));
        assertNotNull("Should find a user and return a login", loginService.findLogin(username));
        Login userRegister2 = new Login();
        userRegister2.setPassword(password + "2");
        userRegister2.setUser(username);
        userRegister2.setEmail(email);
        assertFalse("Register should return false {}", registerService.register(userRegister2));
        assertNotNull("Should find a user and return a login", loginService.findLogin(username));
        Login userRegister3 = new Login();
        userRegister3.setPassword(password + "3");
        userRegister3.setUser(username + "3");
        userRegister3.setEmail(email + "3");
        assertTrue("Register should return true {}", registerService.register(userRegister3));
        assertNotNull("Should find a user and return a login", loginService.findLogin(username));
    }

    @Test
    public void register3CrappyUserandEmail1Good2Bad() {
        Login userRegister = new Login();
        userRegister.setPassword(password);
        userRegister.setUser(username);
        userRegister.setEmail(email);
        assertTrue("Register should return true {}", registerService.register(userRegister));
        assertNotNull("Should find a user and return a login", loginService.findLogin(username));
        Login userRegister2 = new Login();
        userRegister2.setPassword(password + "2");
        userRegister2.setUser(username);
        userRegister2.setEmail(email);
        assertFalse("Register should return false {}", registerService.register(userRegister2));
        assertNotNull("Should find a user and return a login", loginService.findLogin(username));
        Login userRegister3 = new Login();
        userRegister3.setPassword(password + "3");
        userRegister3.setUser(username);
        userRegister3.setEmail(email);
        assertFalse("Register should return true {}", registerService.register(userRegister3));
        assertNotNull("Should find a user and return a login", loginService.findLogin(username));
    }

    @Test
    public void register3CrappyFirst2GoodLastBad() {
        Login userRegister = new Login();
        userRegister.setPassword(password);
        userRegister.setUser(username);
        userRegister.setEmail(email);
        assertTrue("Register should return true {}", registerService.register(userRegister));
        assertNotNull("Should find a user and return a login", loginService.findLogin(username));
        Login userRegister2 = new Login();
        userRegister2.setPassword(password + "2");
        userRegister2.setUser(username + "2");
        userRegister2.setEmail(email + "2");
        assertTrue("Register should return false {}", registerService.register(userRegister2));
        assertNotNull("Should find a user and return a login", loginService.findLogin(username));
        Login userRegister3 = new Login();
        userRegister3.setPassword(password);
        userRegister3.setUser(username);
        userRegister3.setEmail(email);
        assertFalse("Register should return true {}", registerService.register(userRegister3));
        assertNotNull("Should find a user and return a login", loginService.findLogin(username));
    }

}