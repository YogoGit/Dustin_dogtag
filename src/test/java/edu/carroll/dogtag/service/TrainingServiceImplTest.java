package edu.carroll.dogtag.service;

import edu.carroll.dogtag.jpa.model.Login;
import edu.carroll.dogtag.jpa.model.Training;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.*;

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

    @Test
    void saveLogNoLog() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertNull("User has no training that is returned and is null", userTraining);
    }
    @Test
    void saveLogNoUser(){
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining("Walking");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog);
        List<Training> userTraining = trainingService.fetchUserTraining("LookingForNoUser");
        assertNull("User does not exist and will be null", userTraining);
    }

    @Test
    void saveLog() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        assertNotNull("User has training that is returned", userTraining);
        assertTrue("User training setDate is correct", userTraining.get(0).getDate().equals("2023-04-12"));
        assertTrue("One entry for traininglog", userTraining.size() == 1);
    }
    @Test
    void saveLog2() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        List<Training> userTraining2 = trainingService.fetchUserTraining(user);
        assertNotNull("User has training that is returned", userTraining2);
        assertTrue("User training setDate is correct", userTraining2.get(0).getDate().equals("2023-04-12"));
        assertTrue("User training2 setDate is correct", userTraining2.get(1).getDate().equals("2023-04-13"));
        assertTrue("Two entry for traininglog", userTraining2.size() == 2);
    }
    @Test
    void saveLog4() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        List<Training> userTraining4 = trainingService.fetchUserTraining(user);
        assertNotNull("User has training that is returned", userTraining4);
        assertTrue("User training setDate is correct", userTraining4.get(0).getDate().equals("2023-04-12"));
        assertTrue("User training2 setDate is correct", userTraining4.get(1).getDate().equals("2023-04-13"));
        assertTrue("User training3 setDate is correct", userTraining4.get(2).getDate().equals("2023-04-14"));
        assertTrue("User training4 setDate is correct", userTraining4.get(3).getDate().equals("2023-04-15"));
        assertTrue("Two entry for traininglog", userTraining4.size() == 4);
    }
    @Test
    void fetchUserTrainingHappy() {
        List<Training> noUser = trainingService.fetchUserTraining(user);
        assertNull("Return null for a list of trainings for a user that doesnt exist", noUser);
    }

    @Test
    void fetchUserTrainingHappy1() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertNull("User has no training", userTraining);
    }

    @Test
    void fetchUserTrainingHappy2() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        assertNotNull("User has training that is returned", userTraining);
        assertTrue("One entry for traininglog", userTraining.size() == 1);
    }

    @Test
    void fetchUserTrainingHappy3() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        assertNotNull("Has training that is returned", userTraining);
        assertTrue("Two entry for traininglog", userTraining.size() == 2);
    }

    @Test
    void fetchUserTrainingHappy4() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        assertNotNull("Has training that is returned", userTraining);
        assertTrue("Two entry for traininglog", userTraining.size() == 4);
    }

    @Test
    void fetchUserTrainingHappyDate() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        assertTrue("Training user fetched correct entry's", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("Training user fetched had correct value for Dated", trainingService.fetchUserTraining(user).get(0).getDate().equals("2023-04-12"));

    }

    @Test
    void fetchUserTrainingHappyDate2() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("Training user fetched had correct value for Date on the first entry", trainingService.fetchUserTraining(user).get(0).getDate().equals("2023-04-12"));
        assertTrue("Training user fetched had correct value for Date on the second entry", trainingService.fetchUserTraining(user).get(1).getDate().equals("2023-04-13"));

    }

    @Test
    void fetchUserTrainingHappyDate3() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("Training user fetched had correct value for Date on the first entry", trainingService.fetchUserTraining(user).get(0).getDate().equals("2023-04-12"));
        assertTrue("Training user fetched had correct value for Date on the second entry", trainingService.fetchUserTraining(user).get(1).getDate().equals("2023-04-13"));
        assertTrue("Training user fetched had correct value for Date on the third entry", trainingService.fetchUserTraining(user).get(2).getDate().equals("2023-04-14"));
        assertTrue("Training user fetched had correct value for Date on the four entry", trainingService.fetchUserTraining(user).get(3).getDate().equals("2023-04-15"));
    }

    @Test
    void fetchUserTrainingHappyLocation() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        assertTrue("Training user fetched correct entry's for users trainingLog", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("", trainingService.fetchUserTraining(user).get(0).getLocation().equals("Carroll"));

    }

    @Test
    void fetchUserTrainingHappyLocation2() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("", trainingService.fetchUserTraining(user).get(0).getLocation().equals("Carroll"));
        assertTrue("", trainingService.fetchUserTraining(user).get(1).getLocation().equals("Carroll2"));

    }

    @Test
    void fetchUserTrainingHappyLocation3() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("", trainingService.fetchUserTraining(user).get(0).getLocation().equals("Carroll"));
        assertTrue("", trainingService.fetchUserTraining(user).get(1).getLocation().equals("Carroll2"));
        assertTrue("", trainingService.fetchUserTraining(user).get(2).getLocation().equals("Carroll3"));
        assertTrue("", trainingService.fetchUserTraining(user).get(3).getLocation().equals("Carroll4"));
    }

    @Test
    void fetchUserTrainingHappyTraining() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("", trainingService.fetchUserTraining(user).get(0).getTraining().equals("Walking"));

    }

    @Test
    void fetchUserTrainingHappyTraining2() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("", trainingService.fetchUserTraining(user).get(0).getTraining().equals("Walking"));
        assertTrue("", trainingService.fetchUserTraining(user).get(1).getTraining().equals("Walking2"));

    }

    @Test
    void fetchUserTrainingHappyTraining3() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("", trainingService.fetchUserTraining(user).get(0).getTraining().equals("Walking"));
        assertTrue("", trainingService.fetchUserTraining(user).get(1).getTraining().equals("Walking2"));
        assertTrue("", trainingService.fetchUserTraining(user).get(2).getTraining().equals("Walking3"));
        assertTrue("", trainingService.fetchUserTraining(user).get(3).getTraining().equals("Walking4"));
    }

    @Test
    void fetchUserTrainingHappyComments() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("", trainingService.fetchUserTraining(user).get(0).getComments().equals("Good"));

    }

    @Test
    void fetchUserTrainingHappyComments2() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("", trainingService.fetchUserTraining(user).get(0).getComments().equals("Good"));
        assertTrue("", trainingService.fetchUserTraining(user).get(1).getComments().equals("Good2"));

    }

    @Test
    void fetchUserTrainingHappyComments3() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("", trainingService.fetchUserTraining(user).get(0).getComments().equals("Good"));
        assertTrue("", trainingService.fetchUserTraining(user).get(1).getComments().equals("Good2"));
        assertTrue("", trainingService.fetchUserTraining(user).get(2).getComments().equals("Good3"));
        assertTrue("", trainingService.fetchUserTraining(user).get(3).getComments().equals("Good4"));
    }

    @Test
    void fetchUserTrainingCrappy1() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        assertNotNull("", userTraining);
        assertFalse("One entry for traininglog and not for both", userTraining.size() == 2);
        assertFalse("Should not return the first user training = the second user training",
                userTraining.get(0).getTraining().equals(userTraining2.get(0).getTraining()));
    }


    @Test
    void fetchUserTrainingCrappy2() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        assertNotNull("", userTraining);
        assertFalse("Two entry for createUser", userTraining.size() == 4);
    }

    @Test
    void fetchUserTrainingCrappyDate() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertFalse("", trainingService.fetchUserTraining(user).get(0).getDate().equals("2023-04-13"));

    }

    @Test
    void fetchUserTrainingCrappyDate2() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertFalse("", trainingService.fetchUserTraining(user).get(0).getDate().equals("2023-04-13"));
        assertFalse("", trainingService.fetchUserTraining(user).get(1).getDate().equals("2023-04-14"));
        assertFalse("", trainingService.fetchUserTraining(user).get(1).getDate()
                .equals(trainingService.fetchUserTraining(user).get(0).getDate()));
    }

    @Test
    void fetchUserTrainingCrappyDate3() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertFalse("", trainingService.fetchUserTraining(user).get(0).getDate().equals("2023-04-13"));
        assertFalse("", trainingService.fetchUserTraining(user).get(1).getDate().equals("2023-04-14"));
        assertFalse("", trainingService.fetchUserTraining(user).get(2).getDate().equals("2023-04-15"));
        assertFalse("", trainingService.fetchUserTraining(user).get(3).getDate().equals("2023-04-16"));
        assertFalse("", trainingService.fetchUserTraining(user).get(3).getDate()
                .equals(trainingService.fetchUserTraining(user).get(0).getDate()));
    }

    @Test
    void fetchUserTrainingCrappyLocation() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("", trainingService.fetchUserTraining(user).get(0).getLocation().equals("Carroll"));

    }

    @Test
    void fetchUserTrainingCrappyLocation2() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("", trainingService.fetchUserTraining(user).get(0).getLocation().equals("Carroll"));
        assertTrue("", trainingService.fetchUserTraining(user).get(1).getLocation().equals("Carroll2"));

    }

    @Test
    void fetchUserTrainingCrappyLocation3() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("", trainingService.fetchUserTraining(user).get(0).getLocation().equals("Carroll"));
        assertTrue("", trainingService.fetchUserTraining(user).get(1).getLocation().equals("Carroll2"));
        assertTrue("", trainingService.fetchUserTraining(user).get(2).getLocation().equals("Carroll3"));
        assertTrue("", trainingService.fetchUserTraining(user).get(3).getLocation().equals("Carroll4"));
    }

    @Test
    void fetchUserTrainingCrappyTraining() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("", trainingService.fetchUserTraining(user).get(0).getTraining().equals("Walking"));

    }

    @Test
    void fetchUserTrainingCrappyTraining2() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("", trainingService.fetchUserTraining(user).get(0).getTraining().equals("Walking"));
        assertTrue("", trainingService.fetchUserTraining(user).get(1).getTraining().equals("Walking2"));

    }

    @Test
    void fetchUserTrainingCrappyTraining3() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("", trainingService.fetchUserTraining(user).get(0).getTraining().equals("Walking"));
        assertTrue("", trainingService.fetchUserTraining(user).get(1).getTraining().equals("Walking2"));
        assertTrue("", trainingService.fetchUserTraining(user).get(2).getTraining().equals("Walking3"));
        assertTrue("", trainingService.fetchUserTraining(user).get(3).getTraining().equals("Walking4"));
    }

    @Test
    void fetchUserTrainingCrappyComments() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("", trainingService.fetchUserTraining(user).get(0).getComments().equals("Good"));

    }

    @Test
    void fetchUserTrainingCrappyComments2() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("", trainingService.fetchUserTraining(user).get(0).getComments().equals("Good"));
        assertTrue("", trainingService.fetchUserTraining(user).get(1).getComments().equals("Good2"));

    }

    @Test
    void fetchUserTrainingCrappyComments3() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("", trainingService.fetchUserTraining(user).get(0).getComments().equals("Good"));
        assertTrue("", trainingService.fetchUserTraining(user).get(1).getComments().equals("Good2"));
        assertTrue("", trainingService.fetchUserTraining(user).get(2).getComments().equals("Good3"));
        assertTrue("", trainingService.fetchUserTraining(user).get(3).getComments().equals("Good4"));
    }
}