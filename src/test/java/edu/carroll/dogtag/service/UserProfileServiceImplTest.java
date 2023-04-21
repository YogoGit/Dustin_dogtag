package edu.carroll.dogtag.service;

import edu.carroll.dogtag.jpa.model.Login;
import edu.carroll.dogtag.jpa.model.UserProfile;
import edu.carroll.dogtag.jpa.repo.LoginRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserProfileServiceImplTest {

    private static final String user = "usertest";
    private static final String password = "passwordtest";
    private static final String email = "emailTest@gmail.com";
    private static final Logger log = LoggerFactory.getLogger(RegisterServiceImplTest.class);
    @Autowired
    private LoginService loginService;
    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private RegisterService registerService;

    @Test
    void fetchUserProfileHappy() {
        UserProfile noUser = userProfileService.fetchUserProfile("user1");
        assertNull(noUser);

    }

    @Test
    void fetchUserProfileHappy1() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        UserProfile noUser = userProfileService.fetchUserProfile(user);
        assertNull(noUser);

    }

    @Test
    void fetchUserProfileHappy2() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        UserProfile userProfile = new UserProfile();
        userProfile.setFname("Dustin");
        userProfile.setLname("Gardner");
        userProfile.setPhone("406-980-0947");
        userProfile.setLogin(loginService.findLogin(user));
        userProfileService.setProfile(userProfile);
        UserProfile userFound = userProfileService.fetchUserProfile(user);
        assertNotNull(userFound);

    }

    @Test
    void fetchUserProfileHappyFname1() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        UserProfile userProfile = new UserProfile();
        userProfile.setFname("Dustin");
        userProfile.setLname("Gardner");
        userProfile.setPhone("406-980-0947");
        userProfile.setLogin(loginService.findLogin(user));
        userProfileService.setProfile(userProfile);
        userProfileService.fetchUserProfile(user);
        assertTrue(userProfileService.fetchUserProfile(user).equals(userProfile));
        assertTrue(userProfileService.fetchUserProfile(user).getFname().equals("Dustin"));

    }

    @Test
    void fetchUserProfileCrappyFname1() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        UserProfile userProfile = new UserProfile();
        userProfile.setFname("Dustin");
        userProfile.setLname("Gardner");
        userProfile.setPhone("406-980-0947");
        userProfile.setLogin(loginService.findLogin(user));
        userProfileService.setProfile(userProfile);
        userProfileService.fetchUserProfile(user);
        assertFalse(userProfileService.fetchUserProfile(user).getFname().equals(userProfile.getLname()));
        assertTrue(userProfileService.fetchUserProfile(user).getFname().equals("Dustin"));

    }

    @Test
    void fetchUserProfileCrappyFname2() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        UserProfile userProfile = new UserProfile();
        userProfile.setFname("Dustin");
        userProfile.setLname("Gardner");
        userProfile.setPhone("406-980-0947");
        userProfile.setLogin(loginService.findLogin(user));
        userProfileService.setProfile(userProfile);
        userProfileService.fetchUserProfile(user);
        assertTrue(userProfileService.fetchUserProfile(user).getFname().equals(userProfile.getFname()));
        assertFalse(userProfileService.fetchUserProfile(user).getFname().equals("Gardner"));

    }

    @Test
    void fetchUserProfileHappyLname1() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        UserProfile userProfile = new UserProfile();
        userProfile.setFname("Dustin");
        userProfile.setLname("Gardner");
        userProfile.setPhone("406-980-0947");
        userProfile.setLogin(loginService.findLogin(user));
        userProfileService.setProfile(userProfile);
        userProfileService.fetchUserProfile(user);
        assertTrue(userProfileService.fetchUserProfile(user).getLname().equals(userProfile.getLname()));
        assertTrue(userProfileService.fetchUserProfile(user).getLname().equals("Gardner"));

    }

    @Test
    void fetchUserProfileCrappyLname1() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        UserProfile userProfile = new UserProfile();
        userProfile.setFname("Dustin");
        userProfile.setLname("Gardner");
        userProfile.setPhone("406-980-0947");
        userProfile.setLogin(loginService.findLogin(user));
        userProfileService.setProfile(userProfile);
        userProfileService.fetchUserProfile(user);
        assertFalse(userProfileService.fetchUserProfile(user).getLname().equals(userProfile.getFname()));
        assertTrue(userProfileService.fetchUserProfile(user).getLname().equals("Gardner"));

    }

    @Test
    void fetchUserProfileCrappyLname2() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        UserProfile userProfile = new UserProfile();
        userProfile.setFname("Dustin");
        userProfile.setLname("Gardner");
        userProfile.setPhone("406-980-0947");
        userProfile.setLogin(loginService.findLogin(user));
        userProfileService.setProfile(userProfile);
        userProfileService.fetchUserProfile(user);
        assertTrue(userProfileService.fetchUserProfile(user).getLname().equals(userProfile.getLname()));
        assertFalse(userProfileService.fetchUserProfile(user).getLname().equals("Dustin"));

    }

    @Test
    void fetchUserProfileCrappyLname3() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        UserProfile userProfile = new UserProfile();
        userProfile.setFname("Dustin");
        userProfile.setLname("Gardner");
        userProfile.setPhone("406-980-0947");
        userProfile.setLogin(loginService.findLogin(user));
        userProfileService.setProfile(userProfile);
        userProfileService.fetchUserProfile(user);
        assertFalse(userProfileService.fetchUserProfile(user).getLname().equals(userProfile.getPhone()));
        assertTrue(userProfileService.fetchUserProfile(user).getLname().equals("Gardner"));

    }

    @Test
    void fetchUserProfileCrappyLname4() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        UserProfile userProfile = new UserProfile();
        userProfile.setFname("Dustin");
        userProfile.setLname("Gardner");
        userProfile.setPhone("406-980-0947");
        userProfile.setLogin(loginService.findLogin(user));
        userProfileService.setProfile(userProfile);
        userProfileService.fetchUserProfile(user);
        assertFalse(userProfileService.fetchUserProfile(user).getLname().equals(userProfile.getPhone()));
        assertFalse(userProfileService.fetchUserProfile(user).getLname().equals("Dustin"));

    }

    @Test
    void fetchUserProfileHappyPhone1() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        UserProfile userProfile = new UserProfile();
        userProfile.setFname("Dustin");
        userProfile.setLname("Gardner");
        userProfile.setPhone("406-980-0947");
        userProfile.setLogin(loginService.findLogin(user));
        userProfileService.setProfile(userProfile);
        userProfileService.fetchUserProfile(user);
        assertTrue(userProfileService.fetchUserProfile(user).getPhone().equals(userProfile.getPhone()));
        assertTrue(userProfileService.fetchUserProfile(user).getPhone().equals("406-980-0947"));

    }

    @Test
    void fetchUserProfileCrappyPhone1() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        UserProfile userProfile = new UserProfile();
        userProfile.setFname("Dustin");
        userProfile.setLname("Gardner");
        userProfile.setPhone("406-980-0947");
        userProfile.setLogin(loginService.findLogin(user));
        userProfileService.setProfile(userProfile);
        userProfileService.fetchUserProfile(user);
        assertFalse(userProfileService.fetchUserProfile(user).getPhone().equals(userProfile.getFname()));
        assertTrue(userProfileService.fetchUserProfile(user).getPhone().equals("406-980-0947"));

    }

    @Test
    void fetchUserProfileCrappyPhone2() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        UserProfile userProfile = new UserProfile();
        userProfile.setFname("Dustin");
        userProfile.setLname("Gardner");
        userProfile.setPhone("406-980-0947");
        userProfile.setLogin(loginService.findLogin(user));
        userProfileService.setProfile(userProfile);
        userProfileService.fetchUserProfile(user);
        assertTrue(userProfileService.fetchUserProfile(user).getPhone().equals(userProfile.getPhone()));
        assertFalse(userProfileService.fetchUserProfile(user).getPhone().equals("Dustin"));

    }

    @Test
    void fetchUserProfileCrappyPhone3() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        UserProfile userProfile = new UserProfile();
        userProfile.setFname("Dustin");
        userProfile.setLname("Gardner");
        userProfile.setPhone("406-980-0947");
        userProfile.setLogin(loginService.findLogin(user));
        userProfileService.setProfile(userProfile);
        userProfileService.fetchUserProfile(user);
        assertFalse(userProfileService.fetchUserProfile(user).getPhone().equals(userProfile.getFname()));
        assertTrue(userProfileService.fetchUserProfile(user).getPhone().equals("406-980-0947"));

    }

    @Test
    void fetchUserProfileCrappyPhone4() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        UserProfile userProfile = new UserProfile();
        userProfile.setFname("Dustin");
        userProfile.setLname("Gardner");
        userProfile.setPhone("406-980-0947");
        userProfile.setLogin(loginService.findLogin(user));
        userProfileService.setProfile(userProfile);
        userProfileService.fetchUserProfile(user);
        assertFalse(userProfileService.fetchUserProfile(user).getPhone().equals(userProfile.getLogin()));
        assertFalse(userProfileService.fetchUserProfile(user).getPhone().equals("Dustin"));

    }

    @Test
    void setProfileHappy() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        UserProfile userProfile = new UserProfile();
        userProfile.setFname("Dustin");
        userProfile.setLname("Gardner");
        userProfile.setPhone("406-980-0947");
        userProfile.setLogin(loginService.findLogin(user));
        assertTrue(userProfileService.setProfile(userProfile));
    }

    @Test
    void setProfileBlankFormPassedCrappy() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        UserProfile userProfile = new UserProfile();
        userProfile.setFname("Dustin");
        userProfile.setLname("Gardner");
        userProfile.setPhone("406-980-0947");
        userProfile.setLogin(loginService.findLogin(user));
        userProfileService.setProfile(userProfile);
        userProfileService.fetchUserProfile(user);
        UserProfile userProfile2 = new UserProfile();
        assertFalse(userProfileService.setProfile(userProfile2));
    }

    @Test
    void setProfileOnlyFnameFormPassedCrappy2() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        UserProfile userProfile = new UserProfile();
        userProfile.setFname("Dustin");
        userProfileService.setProfile(userProfile);
        assertFalse(userProfileService.setProfile(userProfile));
    }
}