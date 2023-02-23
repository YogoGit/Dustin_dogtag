package edu.carroll.dogtag.service;

import edu.carroll.dogtag.jpa.model.Login;
import edu.carroll.dogtag.jpa.repo.RegisterRepository;
import edu.carroll.dogtag.web.form.RegisterForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.test.util.AssertionErrors;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.springframework.test.util.AssertionErrors.*;

@SpringBootTest
class RegisterServiceImplTest {
    private static final String user = "usertest";

    private static final String password = "passwordtest";

    private static final String userDelete = "userdeletetest";

    private static final String passwordDelete = "passworddeletetest";

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
        registerService.register(userRegister);
    }

    @Test
    public void userExistsTest() {
        final List<Login> user =
                registerRepository.findByUserIgnoreCase(userRegister.getUser());
        assertTrue("userExistSuccessTest: should succeed", registerService.userExists(userRegister.getUser()));

    }

    @Test
    public void deleteCreatedUserTest(){
        Login userRegisterDelete = new Login();
        userRegisterDelete.setPassword(passwordDelete);
        userRegister.setUser(userDelete);
        registerService.register(userRegister);
        registerService.deleteUser(userRegister);
        assertFalse("userExistSuccessTest: should succeed", registerService.userExists(userRegister.getUser()));
    }

}