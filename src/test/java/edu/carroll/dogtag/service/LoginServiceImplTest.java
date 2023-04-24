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
    public void validateUserNoUsersInDatabase() throws InvalidKeySpecException, NoSuchAlgorithmException {
        final LoginForm form = new LoginForm(user, password);
        assertFalse("validateUserNoUsersInDatabase: should fail with no users in database", loginService.validateUser(form));
    }

    @Test
    public void validateUserNoUsersInDatabase2() throws InvalidKeySpecException, NoSuchAlgorithmException {
        final LoginForm form = new LoginForm(password, user);
        assertFalse("validateUserNoUsersInDatabase: should fail with no users in database and switched loginform inputs",
                loginService.validateUser(form));
    }

    @Test
    public void validateUserSuccessTest() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        final LoginForm form = new LoginForm(user, password);
        assertTrue("validateUserSuccessTest: should succeed using the same user/pass info", loginService.validateUser(form));
    }

    @Test
    public void validateUserSuccessTest2() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        final LoginForm form = new LoginForm(user, password);
        Login createUser2 = new Login();
        createUser2.setPassword(password + "2");
        createUser2.setUser(user + "2");
        createUser2.setEmail(email + "2");
        registerService.register(createUser2);
        final LoginForm form2 = new LoginForm(user + "2", password + "2");
        assertTrue("validateUserSuccessTest: should succeed using the same user/pass info", loginService.validateUser(form));
        assertTrue("validateUserSuccessTest: should succeed using the same user/pass2 info", loginService.validateUser(form2));
    }

    @Test
    public void validateUserSuccessTest4() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        final LoginForm form = new LoginForm(user, password);
        Login createUser2 = new Login();
        createUser2.setPassword(password + "2");
        createUser2.setUser(user + "2");
        createUser2.setEmail(email + "2");
        registerService.register(createUser2);
        final LoginForm form2 = new LoginForm(user + "2", password + "2");
        Login createUser3 = new Login();
        createUser3.setPassword(password + "3");
        createUser3.setUser(user + "3");
        createUser3.setEmail(email + "3");
        registerService.register(createUser3);
        final LoginForm form3 = new LoginForm(user + "3", password + "3");
        Login createUser4 = new Login();
        createUser4.setPassword(password + "4");
        createUser4.setUser(user + "4");
        createUser4.setEmail(email + "4");
        registerService.register(createUser4);
        final LoginForm form4 = new LoginForm(user + "2", password + "2");
        assertTrue("validateUserSuccessTest: should succeed using the same user/pass1 info", loginService.validateUser(form));
        assertTrue("validateUserSuccessTest: should succeed using the same user/pass2 info", loginService.validateUser(form2));
        assertTrue("validateUserSuccessTest: should succeed using the same user/pass3 info", loginService.validateUser(form3));
        assertTrue("validateUserSuccessTest: should succeed using the same user/pass4 info", loginService.validateUser(form4));

    }

    @Test
    public void validateUserFailPasswordAndSuccessInputTest() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        final LoginForm form = new LoginForm(user, password + "2");
        final LoginForm form2 = new LoginForm(user, password);
        assertFalse("validateUserFailInputSwitchTest: should fail switching user/pass info", loginService.validateUser(form));
        assertTrue("validateUserFailInputSwitchTest: should fail switching user/pass info", loginService.validateUser(form2));
    }

    @Test
    public void validateUserFailAndSuccessInputTest() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        final LoginForm form = new LoginForm(user + "2", password);
        final LoginForm form2 = new LoginForm(user, password);
        assertFalse("validateUserFailInputSwitchTest: should fail switching user/pass info", loginService.validateUser(form));
        assertTrue("validateUserFailInputSwitchTest: should fail switching user/pass info", loginService.validateUser(form2));
    }

    @Test
    public void validateUserFailInputSwitchTest() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        final LoginForm form = new LoginForm(password, user);
        assertFalse("validateUserFailInputSwitchTest: should fail switching user/pass info", loginService.validateUser(form));
    }

    @Test
    public void validateUserExistingUserInvalidPasswordTest() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        final LoginForm form = new LoginForm(user, password + "extra");
        assertFalse("validateUserExistingUserInvalidPasswordTest: should fail using a valid user, invalid pass", loginService.validateUser(form));
    }

    @Test
    public void validateUserInvalidUserValidPasswordTest() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        final LoginForm form = new LoginForm(user + "not", password);
        assertFalse("validateUserInvalidUserValidPasswordTest: should fail using an invalid user, valid pass", loginService.validateUser(form));
    }

    @Test
    public void validateUserInvalidUserInvalidPasswordTest() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        final LoginForm form = new LoginForm(user + "not", password + "extra");
        assertFalse("validateUserInvalidUserInvalidPasswordTest: should fail using an invalid user, valid pass", loginService.validateUser(form));
    }

    @Test
    public void validateUserInvalidUserInvalidPasswordTest2() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        final LoginForm form = new LoginForm(user + "not", password + "extra");
        Login createUser2 = new Login();
        createUser2.setPassword(password + "2");
        createUser2.setUser(user + "2");
        createUser2.setEmail(email + "2");
        registerService.register(createUser2);
        final LoginForm form2 = new LoginForm(user + "not2", password + "extra2");
        assertFalse("validateUserInvalidUserInvalidPasswordTest: should fail using an invalid user, invalid pass", loginService.validateUser(form));
        assertFalse("validateUserInvalidUserInvalidPasswordTest: should fail using an invalid user2, invalid pass", loginService.validateUser(form2));
    }

    @Test
    public void validateUserInvalidUserInvalidPasswordTest4() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        final LoginForm form = new LoginForm(user + "not", password + "extra");
        Login createUser2 = new Login();
        createUser2.setPassword(password + "2");
        createUser2.setUser(user + "2");
        createUser2.setEmail(email + "2");
        registerService.register(createUser2);
        final LoginForm form2 = new LoginForm(user + "not2", password + "extra2");
        Login createUser3 = new Login();
        createUser3.setPassword(password + "3");
        createUser3.setUser(user + "3");
        createUser3.setEmail(email + "3");
        registerService.register(createUser3);
        final LoginForm form3 = new LoginForm(user + "not3", password + "extra3");
        Login createUser4 = new Login();
        createUser4.setPassword(password + "4");
        createUser4.setUser(user + "4");
        createUser4.setEmail(email + "4");
        registerService.register(createUser4);
        final LoginForm form4 = new LoginForm(user + "not4", password + "extra4");
        assertFalse("validateUserInvalidUserInvalidPasswordTest: should fail using an invalid user, invalid pass", loginService.validateUser(form));
        assertFalse("validateUserInvalidUserInvalidPasswordTest: should fail using an invalid user2, invalid pass", loginService.validateUser(form2));
        assertFalse("validateUserInvalidUserInvalidPasswordTest: should fail using an invalid user3, invalid pass", loginService.validateUser(form3));
        assertFalse("validateUserInvalidUserInvalidPasswordTest: should fail using an invalid user4, invalid pass", loginService.validateUser(form4));
    }

    @Test
    public void validateUserSwitchUserLookupTest2() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        final LoginForm form = new LoginForm(user, password + "2");
        Login createUser2 = new Login();
        createUser2.setPassword(password + "2");
        createUser2.setUser(user + "2");
        createUser2.setEmail(email + "2");
        registerService.register(createUser2);
        final LoginForm form2 = new LoginForm(user + "2", password);
        assertFalse("validateUserSuccessTest: should fail using the user and different user2 pass info", loginService.validateUser(form));
        assertFalse("validateUserSuccessTest: should fail using the user2 and different user pass info", loginService.validateUser(form2));
    }

    @Test
    public void validateUserSwitchUserLookupTest4() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        final LoginForm form = new LoginForm(user, password + "2");
        Login createUser2 = new Login();
        createUser2.setPassword(password + "2");
        createUser2.setUser(user + "2");
        createUser2.setEmail(email + "2");
        registerService.register(createUser2);
        final LoginForm form3 = new LoginForm(user + "2", password);
        Login createUser3 = new Login();
        createUser3.setPassword(password + "3");
        createUser3.setUser(user + "3");
        createUser3.setEmail(email + "3");
        registerService.register(createUser3);
        final LoginForm form4 = new LoginForm(user, password + "3");
        Login createUser4 = new Login();
        createUser4.setPassword(password + "4");
        createUser4.setUser(user + "4");
        createUser4.setEmail(email + "4");
        registerService.register(createUser4);
        final LoginForm form2 = new LoginForm(user + "4", password);
        assertFalse("validateUserSuccessTest: should fail using the user and different user2 pass info", loginService.validateUser(form));
        assertFalse("validateUserSuccessTest: should fail using the user2 and different user pass info", loginService.validateUser(form2));
        assertFalse("validateUserSuccessTest: should fail using the user and different user2 pass info", loginService.validateUser(form3));
        assertFalse("validateUserSuccessTest: should fail using the user2 and different user pass info", loginService.validateUser(form4));
    }

    @Test
    public void validateUserFormNull() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        final LoginForm form = new LoginForm(null, null);
        assertFalse("validateUserSuccessTest: should fail using the user2 and different user pass info", loginService.validateUser(form));
    }

    @Test
    public void validateUserFormBlank() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        final LoginForm form = new LoginForm("", "");
        assertFalse("validateUserSuccessTest: should fail using the user2 and different user pass info", loginService.validateUser(form));
    }

    @Test
    public void validateUserFormBlankUser() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        final LoginForm form = new LoginForm("", password);
        assertFalse("validateUserSuccessTest: should fail using the user2 and different user pass info", loginService.validateUser(form));
    }

    @Test
    public void validateUserFormBlankPassword() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        final LoginForm form = new LoginForm(user, "");
        assertFalse("validateUserSuccessTest: should fail using the user2 and different user pass info", loginService.validateUser(form));
    }

    @Test
    public void validateUserNullUser() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        final LoginForm form = new LoginForm(null, password);
        assertFalse("validateUserSuccessTest: should fail using the user2 and different user pass info", loginService.validateUser(form));
    }

    @Test
    public void validateUserNullPassword() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        final LoginForm form = new LoginForm(user, null);
        assertFalse("validateUserSuccessTest: should fail using the user2 and different user pass info", loginService.validateUser(form));
    }

    @Test
    public void findLogin() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        final LoginForm form = new LoginForm(user, password);
        assertTrue("validateUserSuccessTest: should succeed using the same user/pass info", loginService.findLogin(form.getUser()).equals(createUser));
    }


    @Test
    public void findLogin2() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        final LoginForm form = new LoginForm(user, password);
        Login createUser2 = new Login();
        createUser2.setPassword(password + "2");
        createUser2.setUser(user + "2");
        createUser2.setEmail(email + "2");
        registerService.register(createUser2);
        final LoginForm form2 = new LoginForm(user + "2", password + "2");
        assertTrue("validateUserSuccessTest: should succeed using the same user/pass info", loginService.findLogin(form.getUser()).equals(createUser));
        assertTrue("validateUserSuccessTest: should succeed using the same user/pass info", loginService.findLogin(form2.getUser()).equals(createUser2));
    }

    @Test
    public void findLogin4() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        final LoginForm form = new LoginForm(user, password);
        Login createUser2 = new Login();
        createUser2.setPassword(password + "2");
        createUser2.setUser(user + "2");
        createUser2.setEmail(email + "2");
        registerService.register(createUser2);
        final LoginForm form2 = new LoginForm(user + "2", password + "2");
        Login createUser3 = new Login();
        createUser3.setPassword(password + "3");
        createUser3.setUser(user + "3");
        createUser3.setEmail(email + "3");
        registerService.register(createUser3);
        final LoginForm form3 = new LoginForm(user + "3", password + "3");
        Login createUser4 = new Login();
        createUser4.setPassword(password + "4");
        createUser4.setUser(user + "4");
        createUser4.setEmail(email + "4");
        registerService.register(createUser4);
        final LoginForm form4 = new LoginForm(user + "4", password + "4");
        assertTrue("validateUserSuccessTest: should succeed using the same user/pass info", loginService.findLogin(form.getUser()).equals(createUser));
        assertTrue("validateUserSuccessTest: should succeed using the same user/pass info", loginService.findLogin(form2.getUser()).equals(createUser2));
        assertTrue("validateUserSuccessTest: should succeed using the same user/pass info", loginService.findLogin(form3.getUser()).equals(createUser3));
        assertTrue("validateUserSuccessTest: should succeed using the same user/pass info", loginService.findLogin(form4.getUser()).equals(createUser4));
    }

    @Test
    public void findLoginNoLogin() {
        final LoginForm form = new LoginForm(user, password);
        assertNull("validateUserSuccessTest: should succeed using the same user/pass info", loginService.findLogin(form.getUser()));
    }

    @Test
    public void findLoginPassNull() {
        final LoginForm form = new LoginForm(user, password);
        assertNull("validateUserSuccessTest: should succeed using the same user/pass info", loginService.findLogin(null));
    }

    @Test
    public void findLoginNullLoginForm() {
        final LoginForm form = new LoginForm(null, null);
        assertNull("validateUserSuccessTest: should succeed using the same user/pass info", loginService.findLogin(form.getUser()));
    }

    @Test
    public void findLoginNullLoginFormUser() {
        final LoginForm form = new LoginForm(null, password);
        assertNull("validateUserSuccessTest: should succeed using the same user/pass info", loginService.findLogin(form.getUser()));
    }

    @Test
    public void findLoginNullLoginFormPassword() {
        final LoginForm form = new LoginForm(user, null);
        assertNull("validateUserSuccessTest: should succeed using the same user/pass info", loginService.findLogin(form.getPassword()));
    }

    @Test
    public void findLoginUserCreatedCrappy() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        final LoginForm form = new LoginForm(user + "2", password);
        assertNull("validateUserSuccessTest: should succeed using the same user/pass info", loginService.findLogin(form.getUser()));
    }

    @Test
    public void findLoginUserNullUserCrappy() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        final LoginForm form = new LoginForm(null, password);
        assertNull("validateUserSuccessTest: should succeed using the same user/pass info", loginService.findLogin(null));
    }

    @Test
    public void findLoginPasswordCreatedCrappy() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        final LoginForm form = new LoginForm(user, password);
        assertNull("validateUserSuccessTest: should succeed using the same user/pass info", loginService.findLogin(form.getPassword()));
    }

    @Test
    public void findLoginCrappy2() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        final LoginForm form = new LoginForm(user, password);
        Login createUser2 = new Login();
        createUser2.setPassword(password + "2");
        createUser2.setUser(user + "2");
        createUser2.setEmail(email + "2");
        registerService.register(createUser2);
        final LoginForm form2 = new LoginForm(user + "2", password + "2");
        assertFalse("validateUserSuccessTest: should succeed using the same user/pass info", loginService.findLogin(form2.getUser()).equals(createUser));
        assertFalse("validateUserSuccessTest: should succeed using the same user/pass info", loginService.findLogin(form.getUser()).equals(createUser2));
    }

    @Test
    public void findLoginCrappy4() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        final LoginForm form = new LoginForm(user, password);
        Login createUser2 = new Login();
        createUser2.setPassword(password + "2");
        createUser2.setUser(user + "2");
        createUser2.setEmail(email + "2");
        registerService.register(createUser2);
        final LoginForm form2 = new LoginForm(user + "2", password + "2");
        Login createUser3 = new Login();
        createUser3.setPassword(password + "3");
        createUser3.setUser(user + "3");
        createUser3.setEmail(email + "3");
        registerService.register(createUser3);
        final LoginForm form3 = new LoginForm(user + "3", password + "3");
        Login createUser4 = new Login();
        createUser4.setPassword(password + "4");
        createUser4.setUser(user + "4");
        createUser4.setEmail(email + "4");
        registerService.register(createUser4);
        final LoginForm form4 = new LoginForm(user + "4", password + "4");
        assertFalse("validateUserSuccessTest: should succeed using the same user/pass info", loginService.findLogin(form4.getUser()).equals(createUser));
        assertFalse("validateUserSuccessTest: should succeed using the same user/pass info", loginService.findLogin(form3.getUser()).equals(createUser2));
        assertFalse("validateUserSuccessTest: should succeed using the same user/pass info", loginService.findLogin(form2.getUser()).equals(createUser3));
        assertFalse("validateUserSuccessTest: should succeed using the same user/pass info", loginService.findLogin(form.getUser()).equals(createUser4));
    }

}