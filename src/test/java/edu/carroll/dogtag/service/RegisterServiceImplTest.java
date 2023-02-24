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


//    @BeforeEach
//    public void creatUserTest(){
//        userRegister = new Login();
//        userRegister.setPassword(password);
//        userRegister.setUser(user);
//        userRegister.setEmail(email);
//        registerService.register(userRegister);
//    }

    @Test
    public void userExistsTest() {
        Login userRegister = new Login();
        userRegister.setPassword(password);
        userRegister.setUser(user);
        userRegister.setEmail(email);
        registerService.register(userRegister);
        assertTrue("userExistsTest: should succeed", registerService.userExists(userRegister.getUser()));
        assertTrue("emailExistsTest: should succeed", registerService.emailExists(userRegister.getEmail()));
    }
    @Test
    public void deleteCreatedUserTest(){
        Login userRegisterDelete = new Login();
        userRegisterDelete.setPassword(passwordDelete);
        userRegisterDelete.setEmail(emailDelete);
        userRegisterDelete.setUser(userDelete);
        registerService.register(userRegisterDelete);
        assertTrue("deleteCreatedUserTest: should succeed", registerService.userExists(userRegisterDelete.getUser()));
        registerService.deleteUser(userRegisterDelete);
        assertFalse("deleteCreatedUserTest: should succeed", registerService.userExists(userRegisterDelete.getUser()));
    }
}