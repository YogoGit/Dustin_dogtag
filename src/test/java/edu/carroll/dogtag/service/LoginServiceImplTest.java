/*package edu.carroll.dogtag.service;

import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertTrue;

import java.util.List;

import edu.carroll.dogtag.jpa.model.database.Login;
import edu.carroll.dogtag.jpa.repo.LoginRepository;
import edu.carroll.dogtag.web.form.LoginForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LoginServiceImplTest {
    private static final String user = "testuser";
    private static final String password = "testpass";

    @Autowired
    private LoginService loginService;

    @Autowired
    private LoginRepository loginRepo;

    private final Login fakeUser = new Login(user, password);

    @BeforeEach
    public void beforeTest() {
        assertNotNull("loginRepository must be injected", loginRepo);
        assertNotNull("loginService must be injected", loginService);

        // Ensure dummy record is in the DB
        final List<Login> user =
                loginRepo.findByUserIgnoreCase(user);
        if (user.isEmpty())
            loginRepo.save(fakeUser);
    }

    @Test
    public void validateUserSuccessTest() {
        final LoginForm form = new LoginForm(user, password);
        assertTrue("validateUserSuccessTest: should succeed using the same user/pass info", loginService.validateUser(form));
    }

    @Test
    public void validateUserExistingUserInvalidPasswordTest() {
        final LoginForm form = new LoginForm(user, password + "extra");
        assertFalse("validateUserExistingUserInvalidPasswordTest: should fail using a valid user, invalid pass", loginService.validateUser(form));
    }

    @Test
    public void validateUserInvalidUserValidPasswordTest() {
        final LoginForm form = new LoginForm(user + "not", password);
        assertFalse("validateUserInvalidUserValidPasswordTest: should fail using an invalid user, valid pass", loginService.validateUser(form));
    }

    @Test
    public void validateUserInvalidUserInvalidPasswordTest() {
        final LoginForm form = new LoginForm(user + "not", password + "extra");
        assertFalse("validateUserInvalidUserInvalidPasswordTest: should fail using an invalid user, valid pass", loginService.validateUser(form));
    }
}*/