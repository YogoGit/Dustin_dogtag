package edu.carroll.dogtag.service;

import edu.carroll.dogtag.jpa.model.Login;
import edu.carroll.dogtag.jpa.repo.RegisterRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.springframework.test.util.AssertionErrors.*;

@SpringBootTest
class RegisterServiceImplTest {
    private static final String user = "usertest";

    private static final String password = "passwordtest";

    private static final String email = "emailTest@gmail.com";

    private static final String userDelete = "userdeletetest";

    private static final String passwordDelete = "passworddeletetest";

    private static final String emailDelete = "emailDeleteTest@gmail.com";

    @Autowired
    private RegisterService registerService;

    @Autowired
    private RegisterRepository registerRepository;

    private Login userRegister;
    @BeforeEach
    public void beforeTest() {
        assertNotNull("registerRepository must be injected", registerRepository);
        assertNotNull("registerService must be injected", registerService);

    }

    @BeforeEach
    public void creatUserTest(){
        userRegister = new Login();
        userRegister.setPassword(password);
        userRegister.setUser(user);
        userRegister.setEmail(email);
        registerService.register(userRegister);
    }

    @Test
    public void userExistsTest() {
        assertTrue("userExistSuccessTest: should succeed", registerService.userExists(userRegister.getUser()));
        assertTrue("emailExistSuccessTest: should succeed", registerService.emailExists(userRegister.getEmail()));
    }
    @Test
    public void deleteCreatedUserTest(){
        Login userRegisterDelete = new Login();
        userRegisterDelete.setPassword(passwordDelete);
        userRegister.setEmail(emailDelete);
        userRegister.setUser(userDelete);
        registerService.register(userRegister);
        registerService.deleteUser(userRegister);
        assertFalse("userExistSuccessTest: should succeed", registerService.userExists(userRegister.getUser()));
    }
}