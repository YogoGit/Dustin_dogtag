package edu.carroll.dogtag.service;

import edu.carroll.dogtag.jpa.model.Login;
import edu.carroll.dogtag.jpa.model.UserProfile;
import edu.carroll.dogtag.jpa.repo.LoginRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class UserProfileServiceImplTest {
    @Autowired
    private LoginService loginService;
    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private UserProfileService userProfileService;



    @Test
    void fetchUserProfileHappy1() {
        Login createUser = new Login();
        createUser.setPassword("password");
        createUser.setUser("user");
        createUser.setEmail("email");
        loginRepository.save(createUser);
        UserProfile userProfile = new UserProfile();
        userProfile.setFname("Dustin");
        userProfile.setLname("Gardner");
        userProfile.setPhone("4069800947");
        userProfile.setLogin(loginService.findLogin("user"));
        userProfileService.setProfile(userProfile);
        userProfileService.fetchUserProfile("user");
        assertTrue(userProfileService.fetchUserProfile("user").getFname().equals(userProfile.getFname()));
        assertTrue(userProfileService.fetchUserProfile("user").getFname().equals("Dustin"));

    }

    @Test
    void fetchUserProfileHappy2() {
        Login createUser = new Login();
        createUser.setPassword("password");
        createUser.setUser("user");
        createUser.setEmail("email");
        loginRepository.save(createUser);
        UserProfile userProfile = new UserProfile();
        userProfile.setFname("Dustin");
        userProfile.setLname("Gardner");
        userProfile.setPhone("4069800947");
        userProfile.setLogin(loginService.findLogin("user"));
        userProfileService.setProfile(userProfile);
        userProfileService.fetchUserProfile("user");
        assertFalse(userProfileService.fetchUserProfile("user").getFname().equals(userProfile.getLname()));
        assertTrue(userProfileService.fetchUserProfile("user").getFname().equals("Dustin"));

    }

    @Test
    void fetchUserProfileHappy3() {
        Login createUser = new Login();
        createUser.setPassword("password");
        createUser.setUser("user");
        createUser.setEmail("email");
        loginRepository.save(createUser);
        UserProfile userProfile = new UserProfile();
        userProfile.setFname("Dustin");
        userProfile.setLname("Gardner");
        userProfile.setPhone("4069800947");
        userProfile.setLogin(loginService.findLogin("user"));
        userProfileService.setProfile(userProfile);
        userProfileService.fetchUserProfile("user");
        assertTrue(userProfileService.fetchUserProfile("user").getFname().equals(userProfile.getFname()));
        assertFalse(userProfileService.fetchUserProfile("user").getFname().equals("Gardner"));

    }

    @Test
    void fetchUserProfileHappy4() {
        Login createUser = new Login();
        createUser.setPassword("password");
        createUser.setUser("user");
        createUser.setEmail("email");
        loginRepository.save(createUser);
        UserProfile userProfile = new UserProfile();
        userProfile.setFname("Dustin");
        userProfile.setLname("Gardner");
        userProfile.setPhone("4069800947");
        userProfile.setLogin(loginService.findLogin("user"));
        userProfileService.setProfile(userProfile);
        userProfileService.fetchUserProfile("user");
        assertTrue(userProfileService.fetchUserProfile("user").getLname().equals(userProfile.getLname()));
        assertTrue(userProfileService.fetchUserProfile("user").getLname().equals("Gardner"));

    }
    @Test
    void fetchUserProfileHappy5() {
        Login createUser = new Login();
        createUser.setPassword("password");
        createUser.setUser("user");
        createUser.setEmail("email");
        loginRepository.save(createUser);
        UserProfile userProfile = new UserProfile();
        userProfile.setFname("Dustin");
        userProfile.setLname("Gardner");
        userProfile.setPhone("4069800947");
        userProfile.setLogin(loginService.findLogin("user"));
        userProfileService.setProfile(userProfile);
        userProfileService.fetchUserProfile("user");
        assertFalse(userProfileService.fetchUserProfile("user").getLname().equals(userProfile.getFname()));
        assertTrue(userProfileService.fetchUserProfile("user").getLname().equals("Gardner"));

    }
    @Test
    void fetchUserProfileHappy6() {
        Login createUser = new Login();
        createUser.setPassword("password");
        createUser.setUser("user");
        createUser.setEmail("email");
        loginRepository.save(createUser);
        UserProfile userProfile = new UserProfile();
        userProfile.setFname("Dustin");
        userProfile.setLname("Gardner");
        userProfile.setPhone("4069800947");
        userProfile.setLogin(loginService.findLogin("user"));
        userProfileService.setProfile(userProfile);
        userProfileService.fetchUserProfile("user");
        assertTrue(userProfileService.fetchUserProfile("user").getLname().equals(userProfile.getLname()));
        assertFalse(userProfileService.fetchUserProfile("user").getLname().equals("Dustin"));

    }

    @Test
    void fetchUserProfileHappy7() {
        Login createUser = new Login();
        createUser.setPassword("password");
        createUser.setUser("user");
        createUser.setEmail("email");
        loginRepository.save(createUser);
        UserProfile userProfile = new UserProfile();
        userProfile.setFname("Dustin");
        userProfile.setLname("Gardner");
        userProfile.setPhone("4069800947");
        userProfile.setLogin(loginService.findLogin("user"));
        userProfileService.setProfile(userProfile);
        userProfileService.fetchUserProfile("user");
        assertFalse(userProfileService.fetchUserProfile("user").getLname().equals(userProfile.getPhone()));
        assertTrue(userProfileService.fetchUserProfile("user").getLname().equals("Gardner"));

    }
    @Test
    void fetchUserProfileHappy8() {
        Login createUser = new Login();
        createUser.setPassword("password");
        createUser.setUser("user");
        createUser.setEmail("email");
        loginRepository.save(createUser);
        UserProfile userProfile = new UserProfile();
        userProfile.setFname("Dustin");
        userProfile.setLname("Gardner");
        userProfile.setPhone("4069800947");
        userProfile.setLogin(loginService.findLogin("user"));
        userProfileService.setProfile(userProfile);
        userProfileService.fetchUserProfile("user");
        assertFalse(userProfileService.fetchUserProfile("user").getLname().equals(userProfile.getPhone()));
        assertFalse(userProfileService.fetchUserProfile("user").getLname().equals("Dustin"));

    }
    @Test
    void setProfile() {
    }
}