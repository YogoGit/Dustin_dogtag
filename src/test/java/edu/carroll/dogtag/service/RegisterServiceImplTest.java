package edu.carroll.dogtag.service;

import edu.carroll.dogtag.jpa.model.Login;
import edu.carroll.dogtag.jpa.repo.RegisterRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;

import static org.springframework.test.util.AssertionErrors.*;

@SpringBootTest
@Transactional
class RegisterServiceImplTest {
    private static final String username = "usertest";
    private static final String usernameShort = "user";

    private static final String password = "passwordtest";
    private static final String passwordShort = "pass";

    private static final String email = "emailTest@gmail.com";


    @Autowired
    private RegisterService registerService;

    @Autowired
    private RegisterRepository registerRepository;

    @Test
    public void userExistsTestHappy1() {
        String noUser = "noUser";
        assertFalse("userExistsTest: should succeed when a user already exists", registerService.userExists(noUser));
    }
    @Test
    public void userExistsTestHappy2() {
        Login userRegister = new Login();
        userRegister.setPassword(password);
        userRegister.setUser(username);
        userRegister.setEmail(email);
        registerService.register(userRegister);
        assertTrue("userExistsTest: should succeed when a user already exists", registerService.userExists(userRegister.getUser()));
    }

    @Test
    public void userExistsTestHappy3() {
        Login userRegister = new Login();
        userRegister.setPassword(password);
        userRegister.setUser(username);
        userRegister.setEmail(email);
        registerService.register(userRegister);
        Login userRegister2 = new Login();
        assertTrue("userExistsTest: should succeed when a user already exists", registerService.userExists(userRegister.getUser()));
        userRegister2.setPassword(password + "2");
        userRegister2.setUser(username + "2");
        userRegister2.setEmail(email + "2");
        registerService.register(userRegister2);
        assertTrue("user2ExistsTest: should succeed when user2 already exists", registerService.userExists(userRegister2.getUser()));
    }
    @Test
    public void userExistsTestHappy4() {
        Login userRegister = new Login();
        userRegister.setPassword(password);
        userRegister.setUser(username);
        userRegister.setEmail(email);
        registerService.register(userRegister);
        Login userRegister2 = new Login();
        assertTrue("userExistsTest: should succeed when a user already exists", registerService.userExists(userRegister.getUser()));
        userRegister2.setPassword(password + "2");
        userRegister2.setUser(username + "2");
        userRegister2.setEmail(email + "2");
        registerService.register(userRegister2);
        assertTrue("user2ExistsTest: should succeed when user2 already exists", registerService.userExists(userRegister2.getUser()));
            Login userRegister3 = new Login();
            userRegister3.setPassword(password + "3");
            userRegister3.setUser(username + "3");
            userRegister3.setEmail(email + "3");
            registerService.register(userRegister3);
            Login userRegister4 = new Login();
            assertTrue("userExistsTest: should succeed when a user3 already exists", registerService.userExists(userRegister.getUser()));
            userRegister4.setPassword(password + "4");
            userRegister4.setUser(username + "4");
            userRegister4.setEmail(email + "4");
            registerService.register(userRegister4);
            assertTrue("user2ExistsTest: should succeed when user4 already exists", registerService.userExists(userRegister2.getUser()));

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
    public void userExistsTestCrappy2() {
        Login userRegister = new Login();
        userRegister.setPassword(password);
        userRegister.setUser(username);
        userRegister.setEmail(email);
        registerService.register(userRegister);
        assertFalse("userExistsTest: should fail when checking for an email existing instead of user existing", registerService.userExists(userRegister.getEmail()));
    }

    @Test
    public void emailsExistsTestHappy1() {
        Login emailRegister = new Login();
        emailRegister.setPassword(password);
        emailRegister.setUser(username);
        emailRegister.setEmail(email);
        registerService.register(emailRegister);
        assertTrue("emailExistsTest: should succeed when a email already exists", registerService.emailExists(emailRegister.getEmail()));

    }
    @Test
    public void emailsExistsTestHappy2() {
        Login emailRegister = new Login();
        emailRegister.setPassword(password);
        emailRegister.setUser(username);
        emailRegister.setEmail(email);
        registerService.register(emailRegister);
        String emailNotFound = "emailNotFound";
        assertFalse("emailExistsTest: should fail when email is not found", registerService.emailExists(emailNotFound));

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
    public void deleteCreatedUserTestHappy1() {
        Login userRegisterDelete = new Login();
        userRegisterDelete.setPassword(password);
        userRegisterDelete.setEmail(email);
        userRegisterDelete.setUser(username);
        registerService.register(userRegisterDelete);
        assertTrue("deleteCreatedUserTest: should succeed creating a user", registerService.userExists(userRegisterDelete.getUser()));
        registerService.deleteByUser(userRegisterDelete.getUser());
        assertFalse("deleteCreatedUserTest: should succeeded deleting because no longer not found", registerService.userExists(userRegisterDelete.getUser()));
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
        registerService.deleteByUser(noUserToDelete);
        assertTrue("deleteCreatedUserTest: should fail because user was not deleted", registerService.userExists(userRegisterDelete.getUser()));
    }

}