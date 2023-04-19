package edu.carroll.dogtag.service;

import edu.carroll.dogtag.jpa.model.Login;
import edu.carroll.dogtag.jpa.model.Training;
import edu.carroll.dogtag.jpa.repo.LoginRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;
import static org.springframework.test.util.AssertionErrors.assertNull;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

@SpringBootTest
@Transactional
class TrainingServiceImplTest {

    private static final String user = "usertest";
    private static final String password = "passwordtest";
    private static final String email = "emailTest@gmail.com";
    private static final Logger log = LoggerFactory.getLogger(RegisterServiceImplTest.class);

    @Autowired
    private RegisterService registerService;
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
        List<Training> noUser = trainingService.fetchUserTraining(user);
        assertNull(noUser);
    }

    @Test
    void fetchUserTrainingHappy1() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertNull("error",userTraining);
    }

    @Test
    void fetchUserTrainingHappy2() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining("Walking");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertNotNull(userTraining);
        assertTrue("One entry for traininglog", userTraining.size() == 1);
    }

    @Test
    void fetchUserTrainingHappy3() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining("Walking");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog);
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog2);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertNotNull(userTraining);
        assertTrue("Two entry for traininglog", userTraining.size() == 2);
    }

    @Test
    void fetchUserTrainingHappy4() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining("Walking");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog);
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog2);
        Training trainingLog3 = new Training();
        trainingLog3.setDate("2023-04-14");
        trainingLog3.setLocation("Carroll3");
        trainingLog3.setTraining("Walking3");
        trainingLog3.setComments("Good3");
        trainingLog3.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog3);
        Training trainingLog4 = new Training();
        trainingLog4.setDate("2023-04-15");
        trainingLog4.setLocation("Carroll4");
        trainingLog4.setTraining("Walking4");
        trainingLog4.setComments("Good4");
        trainingLog4.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog4);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertNotNull(userTraining);
        assertTrue("Two entry for traininglog", userTraining.size() == 4);
    }

    @Test
    void fetchUserTrainingHappyDate() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining("Walking");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        Assertions.assertTrue(trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue(trainingService.fetchUserTraining(user).get(0).getDate().equals("2023-04-12"));

    }

    @Test
    void fetchUserTrainingHappyDate2() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining("Walking");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog);
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog2);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        Assertions.assertTrue(trainingService.fetchUserTraining(user).equals(userTraining));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(0).getDate().equals("2023-04-12"));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(1).getDate().equals("2023-04-13"));

    }

    @Test
    void fetchUserTrainingHappyDate3() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining("Walking");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog);
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog2);
        Training trainingLog3 = new Training();
        trainingLog3.setDate("2023-04-14");
        trainingLog3.setLocation("Carroll3");
        trainingLog3.setTraining("Walking3");
        trainingLog3.setComments("Good3");
        trainingLog3.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog3);
        Training trainingLog4 = new Training();
        trainingLog4.setDate("2023-04-15");
        trainingLog4.setLocation("Carroll4");
        trainingLog4.setTraining("Walking4");
        trainingLog4.setComments("Good4");
        trainingLog4.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog4);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        Assertions.assertTrue(trainingService.fetchUserTraining(user).equals(userTraining));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(0).getDate().equals("2023-04-12"));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(1).getDate().equals("2023-04-13"));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(2).getDate().equals("2023-04-14"));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(3).getDate().equals("2023-04-15"));
    }

    @Test
    void fetchUserTrainingHappyLocation() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining("Walking");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        Assertions.assertTrue(trainingService.fetchUserTraining(user).equals(userTraining));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(0).getLocation().equals("Carroll"));

    }

    @Test
    void fetchUserTrainingHappyLocation2() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining("Walking");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog);
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog2);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        Assertions.assertTrue(trainingService.fetchUserTraining(user).equals(userTraining));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(0).getLocation().equals("Carroll"));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(1).getLocation().equals("Carroll2"));

    }

    @Test
    void fetchUserTrainingHappyLocation3() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining("Walking");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog);
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog2);
        Training trainingLog3 = new Training();
        trainingLog3.setDate("2023-04-14");
        trainingLog3.setLocation("Carroll3");
        trainingLog3.setTraining("Walking3");
        trainingLog3.setComments("Good3");
        trainingLog3.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog3);
        Training trainingLog4 = new Training();
        trainingLog4.setDate("2023-04-15");
        trainingLog4.setLocation("Carroll4");
        trainingLog4.setTraining("Walking4");
        trainingLog4.setComments("Good4");
        trainingLog4.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog4);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        Assertions.assertTrue(trainingService.fetchUserTraining(user).equals(userTraining));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(0).getLocation().equals("Carroll"));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(1).getLocation().equals("Carroll2"));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(2).getLocation().equals("Carroll3"));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(3).getLocation().equals("Carroll4"));
    }

    @Test
    void fetchUserTrainingHappyTraining() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining("Walking");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        Assertions.assertTrue(trainingService.fetchUserTraining(user).equals(userTraining));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(0).getTraining().equals("Walking"));

    }

    @Test
    void fetchUserTrainingHappyTraining2() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining("Walking");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog);
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog2);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        Assertions.assertTrue(trainingService.fetchUserTraining(user).equals(userTraining));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(0).getTraining().equals("Walking"));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(1).getTraining().equals("Walking2"));

    }

    @Test
    void fetchUserTrainingHappyTraining3() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining("Walking");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog);
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog2);
        Training trainingLog3 = new Training();
        trainingLog3.setDate("2023-04-14");
        trainingLog3.setLocation("Carroll3");
        trainingLog3.setTraining("Walking3");
        trainingLog3.setComments("Good3");
        trainingLog3.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog3);
        Training trainingLog4 = new Training();
        trainingLog4.setDate("2023-04-15");
        trainingLog4.setLocation("Carroll4");
        trainingLog4.setTraining("Walking4");
        trainingLog4.setComments("Good4");
        trainingLog4.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog4);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        Assertions.assertTrue(trainingService.fetchUserTraining(user).equals(userTraining));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(0).getTraining().equals("Walking"));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(1).getTraining().equals("Walking2"));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(2).getTraining().equals("Walking3"));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(3).getTraining().equals("Walking4"));
    }

    @Test
    void fetchUserTrainingHappyComments() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining("Walking");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        Assertions.assertTrue(trainingService.fetchUserTraining(user).equals(userTraining));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(0).getComments().equals("Good"));

    }

    @Test
    void fetchUserTrainingHappyComments2() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining("Walking");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog);
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog2);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        Assertions.assertTrue(trainingService.fetchUserTraining(user).equals(userTraining));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(0).getComments().equals("Good"));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(1).getComments().equals("Good2"));

    }

    @Test
    void fetchUserTrainingHappyComments3() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining("Walking");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog);
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog2);
        Training trainingLog3 = new Training();
        trainingLog3.setDate("2023-04-14");
        trainingLog3.setLocation("Carroll3");
        trainingLog3.setTraining("Walking3");
        trainingLog3.setComments("Good3");
        trainingLog3.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog3);
        Training trainingLog4 = new Training();
        trainingLog4.setDate("2023-04-15");
        trainingLog4.setLocation("Carroll4");
        trainingLog4.setTraining("Walking4");
        trainingLog4.setComments("Good4");
        trainingLog4.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog4);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        Assertions.assertTrue(trainingService.fetchUserTraining(user).equals(userTraining));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(0).getComments().equals("Good"));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(1).getComments().equals("Good2"));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(2).getComments().equals("Good3"));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(3).getComments().equals("Good4"));
    }

    @Test
    void fetchUserTrainingCrappy1() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining("Walking");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog);

        Login createUser2 = new Login();
        createUser2.setPassword(password + "2");
        createUser2.setUser(user + "2");
        createUser2.setEmail(email + "2");
        registerService.register(createUser2);
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user + "2"));
        trainingService.saveLog(trainingLog2);

        List<Training> userTraining = trainingService.fetchUserTraining(user);
        List<Training> userTraining2 = trainingService.fetchUserTraining(user + "2");
        assertNotNull(userTraining);
        assertFalse("One entry for traininglog and not for both", userTraining.size() == 2);
        assertFalse("Should not return the first user training = the second user training",
                userTraining.get(0).getTraining().equals(userTraining2.get(0).getTraining()));
    }


    @Test
    void fetchUserTrainingCrappy2() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining("Walking");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog);
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog2);

        Login createUser2 = new Login();
        createUser2.setPassword(password + "2");
        createUser2.setUser(user + "2");
        createUser2.setEmail(email + "2");
        registerService.register(createUser2);
        Training trainingLog3 = new Training();
        trainingLog3.setDate("2023-04-14");
        trainingLog3.setLocation("Carroll3");
        trainingLog3.setTraining("Walking3");
        trainingLog3.setComments("Good3");
        trainingLog3.setLogin(loginService.findLogin(user + "2"));
        trainingService.saveLog(trainingLog3);
        Training trainingLog4 = new Training();
        trainingLog4.setDate("2023-04-15");
        trainingLog4.setLocation("Carroll4");
        trainingLog4.setTraining("Walking4");
        trainingLog4.setComments("Good4");
        trainingLog4.setLogin(loginService.findLogin(user + "2"));
        trainingService.saveLog(trainingLog4);
        List<Training> userTraining = trainingService.fetchUserTraining(user + "2");
        assertNotNull(userTraining);
        assertFalse("Two entry for createUser", userTraining.size() == 4);
    }

    @Test
    void fetchUserTrainingCrappyDate() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining("Walking");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        Assertions.assertTrue(trainingService.fetchUserTraining(user).equals(userTraining));
        Assertions.assertFalse(trainingService.fetchUserTraining(user).get(0).getDate().equals("2023-04-13"));

    }

    @Test
    void fetchUserTrainingCrappyDate2() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining("Walking");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog);
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog2);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        Assertions.assertTrue(trainingService.fetchUserTraining(user).equals(userTraining));
        Assertions.assertFalse(trainingService.fetchUserTraining(user).get(0).getDate().equals("2023-04-13"));
        Assertions.assertFalse(trainingService.fetchUserTraining(user).get(1).getDate().equals("2023-04-14"));
        Assertions.assertFalse(trainingService.fetchUserTraining(user).get(1).getDate()
                .equals(trainingService.fetchUserTraining(user).get(0).getDate()));
    }

    @Test
    void fetchUserTrainingCrappyDate3() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining("Walking");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog);
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog2);


        Training trainingLog3 = new Training();
        trainingLog3.setDate("2023-04-14");
        trainingLog3.setLocation("Carroll3");
        trainingLog3.setTraining("Walking3");
        trainingLog3.setComments("Good3");
        trainingLog3.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog3);
        Training trainingLog4 = new Training();
        trainingLog4.setDate("2023-04-15");
        trainingLog4.setLocation("Carroll4");
        trainingLog4.setTraining("Walking4");
        trainingLog4.setComments("Good4");
        trainingLog4.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog4);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        Assertions.assertTrue(trainingService.fetchUserTraining(user).equals(userTraining));
        Assertions.assertFalse(trainingService.fetchUserTraining(user).get(0).getDate().equals("2023-04-13"));
        Assertions.assertFalse(trainingService.fetchUserTraining(user).get(1).getDate().equals("2023-04-14"));
        Assertions.assertFalse(trainingService.fetchUserTraining(user).get(2).getDate().equals("2023-04-15"));
        Assertions.assertFalse(trainingService.fetchUserTraining(user).get(3).getDate().equals("2023-04-16"));
        Assertions.assertFalse(trainingService.fetchUserTraining(user).get(3).getDate()
                .equals(trainingService.fetchUserTraining(user).get(0).getDate()));
    }

    @Test
    void fetchUserTrainingCrappyLocation() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining("Walking");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        Assertions.assertTrue(trainingService.fetchUserTraining(user).equals(userTraining));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(0).getLocation().equals("Carroll"));

    }

    @Test
    void fetchUserTrainingCrappyLocation2() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining("Walking");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog);
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog2);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        Assertions.assertTrue(trainingService.fetchUserTraining(user).equals(userTraining));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(0).getLocation().equals("Carroll"));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(1).getLocation().equals("Carroll2"));

    }

    @Test
    void fetchUserTrainingCrappyLocation3() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining("Walking");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog);
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog2);
        Training trainingLog3 = new Training();
        trainingLog3.setDate("2023-04-14");
        trainingLog3.setLocation("Carroll3");
        trainingLog3.setTraining("Walking3");
        trainingLog3.setComments("Good3");
        trainingLog3.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog3);
        Training trainingLog4 = new Training();
        trainingLog4.setDate("2023-04-15");
        trainingLog4.setLocation("Carroll4");
        trainingLog4.setTraining("Walking4");
        trainingLog4.setComments("Good4");
        trainingLog4.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog4);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        Assertions.assertTrue(trainingService.fetchUserTraining(user).equals(userTraining));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(0).getLocation().equals("Carroll"));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(1).getLocation().equals("Carroll2"));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(2).getLocation().equals("Carroll3"));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(3).getLocation().equals("Carroll4"));
    }

    @Test
    void fetchUserTrainingCrappyTraining() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining("Walking");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        Assertions.assertTrue(trainingService.fetchUserTraining(user).equals(userTraining));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(0).getTraining().equals("Walking"));

    }

    @Test
    void fetchUserTrainingCrappyTraining2() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining("Walking");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog);
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog2);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        Assertions.assertTrue(trainingService.fetchUserTraining(user).equals(userTraining));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(0).getTraining().equals("Walking"));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(1).getTraining().equals("Walking2"));

    }

    @Test
    void fetchUserTrainingCrappyTraining3() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining("Walking");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog);
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog2);
        Training trainingLog3 = new Training();
        trainingLog3.setDate("2023-04-14");
        trainingLog3.setLocation("Carroll3");
        trainingLog3.setTraining("Walking3");
        trainingLog3.setComments("Good3");
        trainingLog3.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog3);
        Training trainingLog4 = new Training();
        trainingLog4.setDate("2023-04-15");
        trainingLog4.setLocation("Carroll4");
        trainingLog4.setTraining("Walking4");
        trainingLog4.setComments("Good4");
        trainingLog4.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog4);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        Assertions.assertTrue(trainingService.fetchUserTraining(user).equals(userTraining));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(0).getTraining().equals("Walking"));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(1).getTraining().equals("Walking2"));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(2).getTraining().equals("Walking3"));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(3).getTraining().equals("Walking4"));
    }

    @Test
    void fetchUserTrainingCrappyComments() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining("Walking");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        Assertions.assertTrue(trainingService.fetchUserTraining(user).equals(userTraining));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(0).getComments().equals("Good"));

    }

    @Test
    void fetchUserTrainingCrappyComments2() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining("Walking");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog);
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog2);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        Assertions.assertTrue(trainingService.fetchUserTraining(user).equals(userTraining));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(0).getComments().equals("Good"));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(1).getComments().equals("Good2"));

    }

    @Test
    void fetchUserTrainingCrappyComments3() {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining("Walking");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog);
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog2);
        Training trainingLog3 = new Training();
        trainingLog3.setDate("2023-04-14");
        trainingLog3.setLocation("Carroll3");
        trainingLog3.setTraining("Walking3");
        trainingLog3.setComments("Good3");
        trainingLog3.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog3);
        Training trainingLog4 = new Training();
        trainingLog4.setDate("2023-04-15");
        trainingLog4.setLocation("Carroll4");
        trainingLog4.setTraining("Walking4");
        trainingLog4.setComments("Good4");
        trainingLog4.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog4);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        Assertions.assertTrue(trainingService.fetchUserTraining(user).equals(userTraining));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(0).getComments().equals("Good"));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(1).getComments().equals("Good2"));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(2).getComments().equals("Good3"));
        Assertions.assertTrue(trainingService.fetchUserTraining(user).get(3).getComments().equals("Good4"));
    }
}