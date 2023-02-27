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

    private static final String password = "passwordtest";

    private static final String email = "emailTest@gmail.com";


    @Autowired
    private RegisterService registerService;

    @Autowired
    private RegisterRepository registerRepository;

    @Test
    public void userExistsTest() {
        Login userRegister = new Login();
        userRegister.setPassword(password);
        userRegister.setUser(username);
        userRegister.setEmail(email);
        registerService.register(userRegister);
        assertTrue("userExistsTest: should succeed", registerService.userExists(userRegister.getUser()));
    }

    @Test
    public void emailsExistsTest() {
        Login emailRegister = new Login();
        emailRegister.setPassword(password);
        emailRegister.setUser(username);
        emailRegister.setEmail(email);
        registerService.register(emailRegister);
        assertTrue("emailExistsTest: should succeed", registerService.emailExists(emailRegister.getEmail()));

    }

    @Test
    public void deleteCreatedUserTest() {
        Login userRegisterDelete = new Login();
        userRegisterDelete.setPassword(password);
        userRegisterDelete.setEmail(email);
        userRegisterDelete.setUser(username);
        registerService.register(userRegisterDelete);
        assertTrue("deleteCreatedUserTest: should succeed", registerService.userExists(userRegisterDelete.getUser()));
        registerService.deleteUser(userRegisterDelete);
        assertFalse("deleteCreatedUserTest: should fail", registerService.userExists(userRegisterDelete.getUser()));
    }

}