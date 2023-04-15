package edu.carroll.dogtag.service;

import edu.carroll.dogtag.jpa.model.Login;
import edu.carroll.dogtag.jpa.repo.LoginRepository;
import edu.carroll.dogtag.web.form.LoginForm;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

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
    public void validateUserNoUsersInDatabase(){
        final LoginForm form = new LoginForm(user, password);
        assertFalse("validateUserNoUsersInDatabase: should fail with no users in database", loginService.validateUser(form));
    }

    @Test
    public void validateUserNoUsersInDatabase2(){
        final LoginForm form = new LoginForm(password, user);
        assertFalse("validateUserNoUsersInDatabase: should fail with no users in database and switched login" +
                "form inputs", loginService.validateUser(form));
    }
    @Test
    public void validateUserSuccessTest() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        final LoginForm form = new LoginForm(user, password);
        assertTrue("validateUserSuccessTest: should succeed using the same user/pass info", loginService.validateUser(form));
    }

    @Test
    public void validateUserSuccessTest2() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        final LoginForm form = new LoginForm(user, password);
        Login createUser2 = new Login();
        createUser2.setPassword(password+"2");
        createUser2.setUser(user+"2");
        createUser2.setEmail(email+"2");
        registerService.register(createUser2);
        final LoginForm form2 = new LoginForm(user+"2", password+"2");
        assertTrue("validateUserSuccessTest: should succeed using the same user/pass info", loginService.validateUser(form));
        assertTrue("validateUserSuccessTest: should succeed using the same user/pass2 info", loginService.validateUser(form2));
    }

    @Test
    public void validateUserSuccessTest4() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        final LoginForm form = new LoginForm(user, password);
        Login createUser2 = new Login();
        createUser2.setPassword(password+"2");
        createUser2.setUser(user+"2");
        createUser2.setEmail(email+"2");
        registerService.register(createUser2);
        final LoginForm form2 = new LoginForm(user+"2", password+"2");
        Login createUser3 = new Login();
        createUser3.setPassword(password+"3");
        createUser3.setUser(user+"3");
        createUser3.setEmail(email+"3");
        registerService.register(createUser3);
        final LoginForm form3 = new LoginForm(user+"3", password+"3");
        Login createUser4 = new Login();
        createUser4.setPassword(password+"4");
        createUser4.setUser(user+"4");
        createUser4.setEmail(email+"4");
        registerService.register(createUser4);
        final LoginForm form4 = new LoginForm(user+"2", password+"2");
        assertTrue("validateUserSuccessTest: should succeed using the same user/pass1 info", loginService.validateUser(form));
        assertTrue("validateUserSuccessTest: should succeed using the same user/pass2 info", loginService.validateUser(form2));
        assertTrue("validateUserSuccessTest: should succeed using the same user/pass3 info", loginService.validateUser(form3));
        assertTrue("validateUserSuccessTest: should succeed using the same user/pass4 info", loginService.validateUser(form4));

    }


    @Test
    public void validateUserFailInputSwitchTest() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        final LoginForm form = new LoginForm(password, user);
        assertFalse("validateUserFailInputSwitchTest: should fail switching user/pass info", loginService.validateUser(form));
    }
    @Test
    public void validateUserExistingUserInvalidPasswordTest() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        final LoginForm form = new LoginForm(user, password + "extra");
        assertFalse("validateUserExistingUserInvalidPasswordTest: should fail using a valid user, invalid pass", loginService.validateUser(form));
    }

    @Test
    public void validateUserInvalidUserValidPasswordTest() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        final LoginForm form = new LoginForm(user + "not", password);
        assertFalse("validateUserInvalidUserValidPasswordTest: should fail using an invalid user, valid pass", loginService.validateUser(form));
    }

    @Test
    public void validateUserInvalidUserInvalidPasswordTest() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        final LoginForm form = new LoginForm(user + "not", password + "extra");
        assertFalse("validateUserInvalidUserInvalidPasswordTest: should fail using an invalid user, valid pass", loginService.validateUser(form));
    }

    @Test
    public void findLogin(){

    }
}