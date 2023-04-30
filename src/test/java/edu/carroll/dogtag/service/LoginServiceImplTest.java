package edu.carroll.dogtag.service;
import edu.carroll.dogtag.jpa.model.Login;
import edu.carroll.dogtag.web.form.LoginForm;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import static org.springframework.test.util.AssertionErrors.*;

/**
 * Unit test to pass happy, crappy and crazy inputs to the loginService, and
 * it should handle all situation that are tested and return the expected results
 * a user must be registered to be in the database so that a login can be attempted
 * If no user is present in the database it will always come back with null for returning
 * a Login object.
 */
@SpringBootTest
@Transactional
public class LoginServiceImplTest {
    private static final String user = "testuser";
    private static final String password = "testpass";

    private static final String email = "dgardner@gmail";

    @Autowired
    private LoginService loginService;

    @Autowired
    private RegisterService registerService;

    @Test
    public void validateUserNoUsersInDatabase() {
        final LoginForm form = new LoginForm(user, password);
        assertFalse("validateUserNoUsersInDatabase: should fail with no users in database", loginService.validateUser(form));
        assertNull("User should be found in the database {}",loginService.findLogin(user));
    }

    @Test
    public void validateUserNoUsersInDatabase2(){
        final LoginForm form = new LoginForm(password, user);
        assertNull("User should be found in the database {}",loginService.findLogin(user));
        assertFalse("validateUserNoUsersInDatabase: should fail with no users in database and switched loginform inputs {}",
                loginService.validateUser(form));
    }

    @Test
    public void validateUserSuccessTest()  {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        final LoginForm form = new LoginForm(user, password);
        assertTrue("Register should return true {}",registerService.register(createUser));
        assertNotNull("User should be found in the database {}",loginService.findLogin(user));
        assertTrue("validateUserSuccessTest: should succeed using the same user/pass info", loginService.validateUser(form));
    }

    @Test
    public void validateUserSuccessTest2()  {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        final LoginForm form = new LoginForm(user, password);
        Login createUser2 = new Login();
        createUser2.setPassword(password + "2");
        createUser2.setUser(user + "2");
        createUser2.setEmail(email + "2");
        final LoginForm form2 = new LoginForm(user + "2", password + "2");
        assertTrue("Register should return true for user1 {}",registerService.register(createUser));
        assertTrue("Register should return true for user2 {}",registerService.register(createUser2));
        assertTrue("validateUserSuccessTest: should succeed using the same user/pass info", loginService.validateUser(form));
        assertTrue("validateUserSuccessTest: should succeed using the same user2/pass2 info", loginService.validateUser(form2));
        assertNotNull("User should be found in the database {}",loginService.findLogin(user));
        assertNotNull("User2 should be found in the database {}",loginService.findLogin(user+"2"));
    }

    @Test
    public void validateUserSuccessTest4()  {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        final LoginForm form = new LoginForm(user, password);
        Login createUser2 = new Login();
        createUser2.setPassword(password + "2");
        createUser2.setUser(user + "2");
        createUser2.setEmail(email + "2");
        final LoginForm form2 = new LoginForm(user + "2", password + "2");
        Login createUser3 = new Login();
        createUser3.setPassword(password + "3");
        createUser3.setUser(user + "3");
        createUser3.setEmail(email + "3");
        final LoginForm form3 = new LoginForm(user + "3", password + "3");
        Login createUser4 = new Login();
        createUser4.setPassword(password + "4");
        createUser4.setUser(user + "4");
        createUser4.setEmail(email + "4");
        final LoginForm form4 = new LoginForm(user + "2", password + "2");
        assertTrue("Register should return true for user1 {}",registerService.register(createUser));
        assertTrue("Register should return true for user2 {}",registerService.register(createUser2));
        assertTrue("Register should return true for user3 {}",registerService.register(createUser3));
        assertTrue("Register should return true for user4 {}",registerService.register(createUser4));
        assertTrue("validateUserSuccessTest: should succeed using the same user1/pass1 info", loginService.validateUser(form));
        assertTrue("validateUserSuccessTest: should succeed using the same user2/pass2 info", loginService.validateUser(form2));
        assertTrue("validateUserSuccessTest: should succeed using the same user3/pass3 info", loginService.validateUser(form3));
        assertTrue("validateUserSuccessTest: should succeed using the same user4/pass4 info", loginService.validateUser(form4));
        assertNotNull("User should be found in the database {}",loginService.findLogin(user));
        assertNotNull("User2 should be found in the database {}",loginService.findLogin(user+"2"));
        assertNotNull("User3 should be found in the database {}",loginService.findLogin(user+"3"));
        assertNotNull("User4 should be found in the database {}",loginService.findLogin(user+"4"));
    }

    @Test
    public void validateUserFailPasswordAndSuccessInputTest()  {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        final LoginForm form = new LoginForm(user, password + "2");
        final LoginForm form2 = new LoginForm(user, password);
        assertTrue("Register should return true {}",registerService.register(createUser));
        assertNotNull("User should be found in the database {}",loginService.findLogin(user));
        assertFalse("validateUserFailInputSwitchTest: should fail with correct user and wrong password", loginService.validateUser(form));
        assertTrue("validateUserFailInputSwitchTest: should succeed with correct user/pass", loginService.validateUser(form2));
    }

    @Test
    public void validateUserFailAndSuccessInputTest()  {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        final LoginForm form = new LoginForm(user + "2", password);
        final LoginForm form2 = new LoginForm(user, password);
        assertTrue("Register should return true {}",registerService.register(createUser));
        assertNotNull("User should be found in the database {}",loginService.findLogin(user));
        assertFalse("validateUserFailInputSwitchTest: should fail with wrong user but correct password", loginService.validateUser(form));
        assertTrue("validateUserFailInputSwitchTest: should fail switching user/pass info", loginService.validateUser(form2));
    }

    @Test
    public void validateUserFailInputSwitchTest()  {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        final LoginForm form = new LoginForm(password, user);
        assertTrue("Register should return true {}",registerService.register(createUser));
        assertNotNull("User should be found in the database {}",loginService.findLogin(user));
        assertFalse("validateUserFailInputSwitchTest: should fail switching user/pass info", loginService.validateUser(form));
    }

    @Test
    public void validateUserExistingUserInvalidPasswordTest()  {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        final LoginForm form = new LoginForm(user, password + "extra");
        assertTrue("Register should return true {}",registerService.register(createUser));
        assertNotNull("User should be found in the database {}",loginService.findLogin(user));
        assertFalse("validateUserExistingUserInvalidPasswordTest: should fail using a valid user, invalid pass", loginService.validateUser(form));
    }

    @Test
    public void validateUserInvalidUserValidPasswordTest()  {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        final LoginForm form = new LoginForm(user + "not", password);
        assertTrue("Register should return true {}",registerService.register(createUser));
        assertNotNull("User should be found in the database {}",loginService.findLogin(user));
        assertFalse("validateUserExistingUserInvalidPasswordTest: should fail using an invalid user and correct pass", loginService.validateUser(form));
    }

    @Test
    public void validateUserInvalidUserInvalidPasswordTest()  {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        final LoginForm form = new LoginForm(user + "not", password + "extra");
        assertTrue("Register should return true {}",registerService.register(createUser));
        assertNotNull("User should be found in the database {}",loginService.findLogin(user));
        assertFalse("validateUserExistingUserInvalidPasswordTest: should fail using a invalid user, invalid pass", loginService.validateUser(form));

    }

    @Test
    public void validateUserInvalidUserInvalidPasswordTest2()  {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        final LoginForm form = new LoginForm(user + "not", password + "extra");
        Login createUser2 = new Login();
        createUser2.setPassword(password + "2");
        createUser2.setUser(user + "2");
        createUser2.setEmail(email + "2");
        final LoginForm form2 = new LoginForm(user + "not2", password + "extra2");
        assertTrue("Register should return true {}",registerService.register(createUser));
        assertTrue("Register should return true {}",registerService.register(createUser2));
        assertNotNull("User should be found in the database {}",loginService.findLogin(user));
        assertNotNull("User should be found in the database {}",loginService.findLogin(user+"2"));
        assertFalse("validateUserInvalidUserInvalidPasswordTest: should fail using an invalid user, invalid pass", loginService.validateUser(form));
        assertFalse("validateUserInvalidUserInvalidPasswordTest: should fail using an invalid user2, invalid pass", loginService.validateUser(form2));
    }

    @Test
    public void validateUserInvalidUserInvalidPasswordTest4()  {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);;
        final LoginForm form = new LoginForm(user + "not", password + "extra");
        Login createUser2 = new Login();
        createUser2.setPassword(password + "2");
        createUser2.setUser(user + "2");
        createUser2.setEmail(email + "2");
        final LoginForm form2 = new LoginForm(user + "not2", password + "extra2");
        Login createUser3 = new Login();
        createUser3.setPassword(password + "3");
        createUser3.setUser(user + "3");
        createUser3.setEmail(email + "3");
        final LoginForm form3 = new LoginForm(user + "not3", password + "extra3");
        Login createUser4 = new Login();
        createUser4.setPassword(password + "4");
        createUser4.setUser(user + "4");
        createUser4.setEmail(email + "4");
        final LoginForm form4 = new LoginForm(user + "not4", password + "extra4");
        assertTrue("Register should return true {}",registerService.register(createUser));
        assertTrue("Register2 should return true {}",registerService.register(createUser2));
        assertTrue("Register3 should return true {}",registerService.register(createUser3));
        assertTrue("Register4 should return true {}",registerService.register(createUser4));
        assertNotNull("User1 should be found in the database {}",loginService.findLogin(user));
        assertNotNull("User2 should be found in the database {}",loginService.findLogin(user+"2"));
        assertNotNull("User3 should be found in the database {}",loginService.findLogin(user+"3"));
        assertNotNull("User4 should be found in the database {}",loginService.findLogin(user+"4"));
        assertFalse("validateUserInvalidUserInvalidPasswordTest: should fail using an invalid user1, invalid pass", loginService.validateUser(form));
        assertFalse("validateUserInvalidUserInvalidPasswordTest: should fail using an invalid user2, invalid pass", loginService.validateUser(form2));
        assertFalse("validateUserInvalidUserInvalidPasswordTest: should fail using an invalid user3, invalid pass", loginService.validateUser(form3));
        assertFalse("validateUserInvalidUserInvalidPasswordTest: should fail using an invalid user4, invalid pass", loginService.validateUser(form4));
    }

    @Test
    public void validateUserSwitchUserLookupTest2()  {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        final LoginForm form = new LoginForm(user, password + "2");
        Login createUser2 = new Login();
        createUser2.setPassword(password + "2");
        createUser2.setUser(user + "2");
        createUser2.setEmail(email + "2");
        final LoginForm form2 = new LoginForm(user + "2", password);
        assertTrue("Register should return true {}",registerService.register(createUser));
        assertTrue("Register should return true {}",registerService.register(createUser2));
        assertNotNull("User should be found in the database {}",loginService.findLogin(user));
        assertNotNull("User should be found in the database {}",loginService.findLogin(user+"2"));
        assertFalse("validateUserSuccessTest: should fail using the user and different user2 pass info", loginService.validateUser(form));
        assertFalse("validateUserSuccessTest: should fail using the user2 and different user pass info", loginService.validateUser(form2));
    }

    @Test
    public void validateUserSwitchUserLookupTest4()  {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        final LoginForm form = new LoginForm(user, password + "2");
        Login createUser2 = new Login();
        createUser2.setPassword(password + "2");
        createUser2.setUser(user + "2");
        createUser2.setEmail(email + "2");
        final LoginForm form3 = new LoginForm(user + "2", password);
        Login createUser3 = new Login();
        createUser3.setPassword(password + "3");
        createUser3.setUser(user + "3");
        createUser3.setEmail(email + "3");
        final LoginForm form4 = new LoginForm(user, password + "3");
        Login createUser4 = new Login();
        createUser4.setPassword(password + "4");
        createUser4.setUser(user + "4");
        createUser4.setEmail(email + "4");
        final LoginForm form2 = new LoginForm(user + "4", password);
        assertTrue("Register should return true {}",registerService.register(createUser));
        assertTrue("Register2 should return true {}",registerService.register(createUser2));
        assertTrue("Register3 should return true {}",registerService.register(createUser3));
        assertTrue("Register4 should return true {}",registerService.register(createUser4));
        assertNotNull("User should be found in the database {}",loginService.findLogin(user));
        assertNotNull("User2 should be found in the database {}",loginService.findLogin(user+"2"));
        assertNotNull("User3 should be found in the database {}",loginService.findLogin(user+"3"));
        assertNotNull("User4 should be found in the database {}",loginService.findLogin(user+"4"));
        assertFalse("validateUserSuccessTest: should fail using the users switched in form", loginService.validateUser(form));
        assertFalse("validateUserSuccessTest: should fail using the user2 switched in form", loginService.validateUser(form2));
        assertFalse("validateUserSuccessTest: should fail using the user3 switched in form", loginService.validateUser(form3));
        assertFalse("validateUserSuccessTest: should fail using the user4 switched in form", loginService.validateUser(form4));
    }

    @Test
    public void validateUserFormNull()  {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        final LoginForm form = new LoginForm(null, null);
        assertTrue("Register should return true {}",registerService.register(createUser));
        assertNotNull("User should be found in the database {}",loginService.findLogin(user));
        assertFalse("validateUserSuccessTest: should fail when null is passed in both fields", loginService.validateUser(form));
    }

    @Test
    public void validateUserFormBlank()  {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        final LoginForm form = new LoginForm("", "");
        assertTrue("Register should return true {}",registerService.register(createUser));
        assertNotNull("User should be found in the database {}",loginService.findLogin(user));
        assertFalse("validateUserSuccessTest: should fail when blank is passed in both fields", loginService.validateUser(form));
    }

    @Test
    public void validateUserFormBlankUser()  {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        final LoginForm form = new LoginForm("", password);
        assertTrue("Register should return true {}",registerService.register(createUser));
        assertNotNull("User should be found in the database {}",loginService.findLogin(user));
        assertFalse("validateUserSuccessTest: should fail when blank user but correct pass are sent", loginService.validateUser(form));
    }

    @Test
    public void validateUserFormBlankPassword()  {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        final LoginForm form = new LoginForm(user, "");
        assertTrue("Register should return true {}",registerService.register(createUser));
        assertNotNull("User should be found in the database {}",loginService.findLogin(user));
        assertFalse("validateUserSuccessTest: should fail when correct user name but blank password is in the form", loginService.validateUser(form));
    }

    @Test
    public void validateUserNullUser()  {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        final LoginForm form = new LoginForm(null, password);
        assertTrue("Register should return true {}",registerService.register(createUser));
        assertNotNull("User should be found in the database {}",loginService.findLogin(user));
        assertFalse("validateUserSuccessTest: should fail when null user and correct password is entered", loginService.validateUser(form));
    }

    @Test
    public void validateUserNullPassword()  {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        final LoginForm form = new LoginForm(user, null);
        assertTrue("Register should return true {}",registerService.register(createUser));
        assertNotNull("User should be found in the database {}",loginService.findLogin(user));
        assertFalse("validateUserSuccessTest: should fail when correct user but password is null in form", loginService.validateUser(form));
    }

    @Test
    public void findLogin()  {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        final LoginForm form = new LoginForm(user, password);
        assertTrue("Register should return true {}",registerService.register(createUser));
        assertNotNull("User should be found in the database {}",loginService.findLogin(user));
        assertTrue("should succeed using the same user/pass info", loginService.findLogin(form.getUser()).equals(createUser));
    }

    @Test
    public void findLogin2()  {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        final LoginForm form = new LoginForm(user, password);
        Login createUser2 = new Login();
        createUser2.setPassword(password + "2");
        createUser2.setUser(user + "2");
        createUser2.setEmail(email + "2");
        final LoginForm form2 = new LoginForm(user + "2", password + "2");
        assertTrue("Register should return true {}",registerService.register(createUser));
        assertTrue("Register2 should return true {}",registerService.register(createUser2));
        assertTrue("should succeed using the same user/pass info", loginService.findLogin(form.getUser()).equals(createUser));
        assertTrue("should succeed using the same user2/pass2 info", loginService.findLogin(form2.getUser()).equals(createUser2));
    }

    @Test
    public void findLogin4()  {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        final LoginForm form = new LoginForm(user, password);
        Login createUser2 = new Login();
        createUser2.setPassword(password + "2");
        createUser2.setUser(user + "2");
        createUser2.setEmail(email + "2");
        final LoginForm form2 = new LoginForm(user + "2", password + "2");
        Login createUser3 = new Login();
        createUser3.setPassword(password + "3");
        createUser3.setUser(user + "3");
        createUser3.setEmail(email + "3");
        final LoginForm form3 = new LoginForm(user + "3", password + "3");
        Login createUser4 = new Login();
        createUser4.setPassword(password + "4");
        createUser4.setUser(user + "4");
        createUser4.setEmail(email + "4");
        final LoginForm form4 = new LoginForm(user + "4", password + "4");
        assertTrue("Register1 should return true {}",registerService.register(createUser));
        assertTrue("Register2 should return true {}",registerService.register(createUser2));
        assertTrue("Register3 should return true {}",registerService.register(createUser3));
        assertTrue("Register4 should return true {}",registerService.register(createUser4));
        assertTrue("should succeed using the same user1/pass1 info", loginService.findLogin(form.getUser()).equals(createUser));
        assertTrue("should succeed using the same user2/pass2 info", loginService.findLogin(form2.getUser()).equals(createUser2));
        assertTrue("should succeed using the same user3/pass3 info", loginService.findLogin(form3.getUser()).equals(createUser3));
        assertTrue("should succeed using the same user4/pass4 info", loginService.findLogin(form4.getUser()).equals(createUser4));
    }

    @Test
    public void findLoginNoLogin() {
        final LoginForm form = new LoginForm(user, password);
        assertNull("should return null when no user has been created", loginService.findLogin(form.getUser()));
    }

    @Test
    public void findLoginPassNull() {
        final LoginForm form = new LoginForm(user, password);
        assertNull(" should return null when passed a null form", loginService.findLogin(null));
    }

    @Test
    public void findLoginNullLoginForm() {
        final LoginForm form = new LoginForm(null, null);
        assertNull("Should return null passing null user and password in field and no user created", loginService.findLogin(form.getUser()));
    }

    @Test
    public void findLoginNullLoginFormUser() {
        final LoginForm form = new LoginForm(null, password);
        assertNull("Should return null passing null user in field and no user created", loginService.findLogin(form.getUser()));
    }

    @Test
    public void findLoginNullLoginFormPassword() {
        final LoginForm form = new LoginForm(user, null);
        assertNull("Should return null passing null password in field and no user created", loginService.findLogin(form.getUser()));
    }

    @Test
    public void findLoginUserCreatedCrappy()  {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        final LoginForm form = new LoginForm(user + "2", password);
        assertTrue("Register should return true {}",registerService.register(createUser));
        assertNull("Should return null passing is being looked up for wrong user", loginService.findLogin(form.getUser()));
    }

    @Test
    public void findLoginUserNullUserCrappy()  {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        final LoginForm form = new LoginForm(null, password);
        assertTrue("Register should return true {}",registerService.register(createUser));
        assertNull("Should return null when null is being looked up in form", loginService.findLogin(null));
    }

    @Test
    public void findLoginPasswordCreatedCrappy()  {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        final LoginForm form = new LoginForm(user, password+"2");
        assertTrue("Register should return true {}",registerService.register(createUser));
        assertNull("Should return null when searching by password", loginService.findLogin(form.getPassword()));
    }

    @Test
    public void findLoginCrappy2()  {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        final LoginForm form = new LoginForm(user, password);
        Login createUser2 = new Login();
        createUser2.setPassword(password + "2");
        createUser2.setUser(user + "2");
        createUser2.setEmail(email + "2");
        final LoginForm form2 = new LoginForm(user + "2", password + "2");
        assertTrue("Register should return true {}",registerService.register(createUser));
        assertTrue("Register should return true {}",registerService.register(createUser2));
        assertFalse("Should fail when looking at the form2 user and comparing it to the wrong login", loginService.findLogin(form2.getUser()).equals(createUser));
        assertFalse("When looking at the form user and comparing it to the wrong login", loginService.findLogin(form.getUser()).equals(createUser2));
    }

    @Test
    public void findLoginCrappy4()  {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        final LoginForm form = new LoginForm(user, password);
        Login createUser2 = new Login();
        createUser2.setPassword(password + "2");
        createUser2.setUser(user + "2");
        createUser2.setEmail(email + "2");
        final LoginForm form2 = new LoginForm(user + "2", password + "2");
        Login createUser3 = new Login();
        createUser3.setPassword(password + "3");
        createUser3.setUser(user + "3");
        createUser3.setEmail(email + "3");
        final LoginForm form3 = new LoginForm(user + "3", password + "3");
        Login createUser4 = new Login();
        createUser4.setPassword(password + "4");
        createUser4.setUser(user + "4");
        createUser4.setEmail(email + "4");
        final LoginForm form4 = new LoginForm(user + "4", password + "4");
        assertTrue("Register should return true {}",registerService.register(createUser));
        assertTrue("Register should return true {}",registerService.register(createUser2));
        assertTrue("Register should return true {}",registerService.register(createUser3));
        assertTrue("Register should return true {}",registerService.register(createUser4));
        assertFalse("Should fail when looking at the form4 user and comparing it to the wrong login", loginService.findLogin(form4.getUser()).equals(createUser));
        assertFalse("Should fail when looking at the form3 user and comparing it to the wrong login", loginService.findLogin(form3.getUser()).equals(createUser2));
        assertFalse("Should fail when looking at the form2 user and comparing it to the wrong login", loginService.findLogin(form2.getUser()).equals(createUser3));
        assertFalse("Should fail when looking at the form1 user and comparing it to the wrong login", loginService.findLogin(form.getUser()).equals(createUser4));
    }
}