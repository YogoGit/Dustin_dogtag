package edu.carroll.dogtag.service;

import edu.carroll.dogtag.jpa.model.Login;
import edu.carroll.dogtag.jpa.model.Training;
import edu.carroll.dogtag.jpa.model.UserProfile;
import edu.carroll.dogtag.jpa.repo.LoginRepository;
import edu.carroll.dogtag.jpa.repo.RegisterRepository;
import edu.carroll.dogtag.jpa.repo.TrainingRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
@Transactional
class TrainingServiceImplTest {

    private static final String user = "usertest";
    private static final String password = "passwordtest";
    private static final String email = "emailTest@gmail.com";
    private static final Logger log = LoggerFactory.getLogger(RegisterServiceImplTest.class);

    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    private TrainingServiceImpl trainingService;

    @Autowired
    private LoginService loginService;
    @Autowired
    private LoginRepository loginRepository;

    @Test
    void saveLog() {
    }

    @Test
    void fetchUserTrainingHappy() {
        List<Training> noUser = trainingService.fetchUserTraining("user1");
        assertNull(noUser);
    }

    @Test
    void fetchUserTrainingHappy1() {
        Login createUser = new Login();
        createUser.setPassword("password");
        createUser.setUser("user");
        createUser.setEmail("email");
        loginRepository.save(createUser);
        List<Training> userTraining = trainingService.fetchUserTraining("user");
        assertNull(userTraining);
    }

    @Test
    void fetchUserProfileHappy2() {
        Login createUser = new Login();
        createUser.setPassword("password");
        createUser.setUser("user");
        createUser.setEmail("email");
        loginRepository.save(createUser);
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining("Walking");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin("user"));
        trainingService.saveLog(trainingLog);
        List<Training> userTraining = trainingService.fetchUserTraining("user");
        assertNotNull(userTraining);
    }

    @Test
    void fetchUserTrainingHappyDate() {
        Login createUser = new Login();
        createUser.setPassword("password");
        createUser.setUser("user");
        createUser.setEmail("email");
        loginRepository.save(createUser);
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining("Walking");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin("user"));
        trainingService.saveLog(trainingLog);
        List<Training> userTraining = trainingService.fetchUserTraining("user");
        Assertions.assertTrue(trainingService.fetchUserTraining("user").equals(userTraining));
        Assertions.assertTrue(trainingService.fetchUserTraining("user").get(0).getDate().equals("2023-04-12"));

    }
}