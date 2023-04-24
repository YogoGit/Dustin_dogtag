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
    void saveLogNoLogNullCrazy() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        assertNull("User has no training that is returned and is null", trainingService.fetchUserTraining(null));
    }

    @Test
    void saveLogNoUser() {
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining("Walking");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin("LookingForNoUser"));
        trainingService.saveLog(trainingLog);
        List<Training> userTraining = trainingService.fetchUserTraining("LookingForNoUser");
        assertNull("User does not exist and will be null", userTraining);
    }

    @Test
    void saveLogNoUserNullCrazy() {
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining("Walking");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin("LookingForNoUser"));
        assertFalse("Training log not saved because of Null", trainingService.saveLog(null));
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
    void saveLogCrappy() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        List<Training> userTraining = trainingService.fetchUserTraining(user+"2");
        assertNull("User has training that is returned", userTraining);
    }
    @Test
    void saveLogCrazyNull() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        trainingService.saveLog(null);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertNull("User has training that is returned", userTraining);
    }
    @Test
    void saveLogCrazyBlank() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        Training trainingLog2 = new Training();
        trainingService.saveLog(trainingLog2);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertNull("User has training that is returned", userTraining);
        assertFalse("Cant send blank training log to service",trainingService.saveLog(trainingLog2));
    }
    @Test
    void saveLogDateCrappy() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        assertFalse("User training setDate is wrong", userTraining.get(0).getDate().equals("2023-04-13"));
        assertTrue("One entry for traininglog", userTraining.size() == 1);
    }

    @Test
    void saveLogLocationCrappy() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        assertFalse("User training setLocation is wrong", userTraining.get(0).getLocation().equals("Carroll2"));
        assertTrue("One entry for traininglog", userTraining.size() == 1);
    }

    @Test
    void saveLogTrainingCrappy() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        assertFalse("User training is wrong", userTraining.get(0).getTraining().equals("Walking2"));
        assertTrue("One entry for traininglog", userTraining.size() == 1);
    }

    @Test
    void saveLogCommentsCrappy() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        assertFalse("User training setComments is wrong", userTraining.get(0).getComments().equals("Good2"));
        assertTrue("One entry for traininglog", userTraining.size() == 1);
    }

    @Test
    void saveLogSetLoginCrappy() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        assertFalse("User training user is wrong", userTraining.get(0).getLogin().equals("usertest2"));
        assertTrue("One entry for traininglog", userTraining.size() == 1);
    }

    @Test
    void saveLogCrazyNullDate() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Training trainingLog = new Training();
        trainingLog.setDate(null);
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining("Walking");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertNull("User has training not saved due to null date", userTraining);
    }

    @Test
    void saveLogCrazyBlankDate() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Training trainingLog = new Training();
        trainingLog.setDate("");
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining("Walking");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertNull("User has training not saved due to null date", userTraining);
    }

    @Test
    void saveLogCrazyNullLocation() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation(null);
        trainingLog.setTraining("Walking");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertNull("User has training not saved due to null date", userTraining);
    }

    @Test
    void saveLogCrazyBlankLocation() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation("");
        trainingLog.setTraining("Walking");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertNull("User has training not saved due to null date", userTraining);
    }

    @Test
    void saveLogCrazyNullTraining() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining(null);
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertNull("User has training not saved due to null date", userTraining);
    }

    @Test
    void saveLogCrazyBlankTraining() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining("");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertNull("User has training not saved due to null date", userTraining);
    }

    @Test
    void saveLogCrazyNullComments() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining("Walking");
        trainingLog.setComments(null);
        trainingLog.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertNull("User has training not saved due to null date", userTraining);
    }

    @Test
    void saveLogCrazyBlankComments() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining("Walking");
        trainingLog.setComments("");
        trainingLog.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertNull("User has training not saved due to null date", userTraining);
    }

    @Test
    void saveLogCrazyNullSetLogin() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        trainingLog.setLogin(null);
        trainingService.saveLog(trainingLog);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertNull("User has training not saved due to null date", userTraining);
    }

    @Test
    void saveLogCrazyBlankSetLogin() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        trainingLog.setLogin(loginService.findLogin(""));
        trainingService.saveLog(trainingLog);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertNull("User has training not saved due to null date", userTraining);
    }

    @Test
    void saveLogCrazyAllNull() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Training trainingLog = new Training();
        trainingLog.setDate(null);
        trainingLog.setLocation(null);
        trainingLog.setTraining(null);
        trainingLog.setComments(null);
        trainingLog.setLogin(null);
        trainingService.saveLog(trainingLog);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertNull("User has training not saved due to null date", userTraining);
    }
    @Test
    void saveLogCrazyAllBlank() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Training trainingLog = new Training();
        trainingLog.setDate("");
        trainingLog.setLocation("");
        trainingLog.setTraining("");
        trainingLog.setComments("");
        trainingLog.setLogin(loginService.findLogin(""));
        trainingService.saveLog(trainingLog);
        Training trainingLog2 = new Training();
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertFalse("cant passed blank log to service", trainingService.saveLog(trainingLog2));
        assertNull("User has training not saved due to null date", userTraining);
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
    void saveLog2OneHappyOneCrappy() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        assertFalse("User training2 setDate is correct", userTraining2.get(1).getDate().equals("2023-04-12"));
        assertTrue("Two entry for traininglog", userTraining2.size() == 2);
    }
    @Test
    void saveLog2Crazy1Null1Blank() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        trainingService.saveLog(null);
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog2);
        Training trainingLog3 = new Training();
        List<Training> userTraining2 = trainingService.fetchUserTraining(user);
        assertNotNull("User has training that is returned", userTraining2);
        assertFalse("Cant send a null log to service",trainingService.saveLog(null));
        assertFalse("Cant send blank log to service",trainingService.saveLog(trainingLog3));
        assertFalse("User training setDate is correct", userTraining2.get(0).getDate().equals("2023-04-12"));
        assertTrue("User training2 setDate is correct", userTraining2.get(0).getDate().equals("2023-04-13"));
        assertTrue("Two entry for traininglog", userTraining2.size() == 1);
    }
    @Test
    void saveLog4TwoHappyTwoCrappy() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        assertFalse("User training3 setDate is correct", userTraining4.get(2).getDate().equals("2023-04-15"));
        assertFalse("User training4 setDate is correct", userTraining4.get(3).getDate().equals("2023-04-16"));
        assertTrue("Two entry for traininglog", userTraining4.size() == 4);
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
    void saveLog4TwoCrappyTwoHappy() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        assertFalse("User training3 setDate is correct", userTraining4.get(2).getDate().equals("2023-04-12"));
        assertFalse("User training4 setDate is correct", userTraining4.get(3).getDate().equals("2023-04-13"));
        assertTrue("Two entry for traininglog", userTraining4.size() == 4);
    }

    @Test
    void saveLog4Crazy2Null2Blank() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        trainingService.saveLog(null);
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        trainingService.saveLog(null);
        Training trainingLog3 = new Training();
        trainingLog3.setDate("2023-04-14");
        trainingLog3.setLocation("Carroll3");
        trainingLog3.setTraining("Walking3");
        trainingLog3.setComments("Good3");
        trainingLog3.setLogin(loginService.findLogin(user));
        Training blank = new Training();
        trainingService.saveLog(blank);
        Training trainingLog4 = new Training();
        trainingLog4.setDate("2023-04-15");
        trainingLog4.setLocation("Carroll4");
        trainingLog4.setTraining("Walking4");
        trainingLog4.setComments("Good4");
        trainingLog4.setLogin(loginService.findLogin(user));
        trainingService.saveLog(blank);
        List<Training> userTraining4 = trainingService.fetchUserTraining(user);
        assertNull("User has training that is returned", userTraining4);
        assertFalse("Cant send a null log to service",trainingService.saveLog(null));
        assertFalse("Cant send blank log to service",trainingService.saveLog(blank));
    }

    @Test
    void saveLog2UsersReturn1() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Login createUser2 = new Login();
        createUser2.setPassword(password + "2");
        createUser2.setUser(user + "2");
        createUser2.setEmail(email + "2");
        registerService.register(createUser2);
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
        trainingLog2.setLogin(loginService.findLogin(user + "2"));
        trainingService.saveLog(trainingLog2);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        List<Training> userTraining2 = trainingService.fetchUserTraining(user + "2");
        assertNotNull("User has training that is returned", userTraining);
        assertNotNull("User has training that is returned", userTraining2);
        assertTrue("User training setDate is correct", userTraining.get(0).getDate().equals("2023-04-12"));
        assertTrue("User training2 setDate is correct", userTraining2.get(0).getDate().equals("2023-04-13"));
        assertTrue("Two entry for traininglog", userTraining.size() == 1);
        assertTrue("Two entry for traininglog", userTraining2.size() == 1);
    }

    @Test
    void saveLog2UsersReturn1Crappy() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Login createUser2 = new Login();
        createUser2.setPassword(password + "2");
        createUser2.setUser(user + "2");
        createUser2.setEmail(email + "2");
        registerService.register(createUser2);
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
        trainingLog2.setLogin(loginService.findLogin(user + "2"));
        trainingService.saveLog(trainingLog2);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        List<Training> userTraining2 = trainingService.fetchUserTraining(user + "2");
        assertNotNull("User has training that is returned", userTraining);
        assertNotNull("User has training that is returned", userTraining2);
        assertFalse("User training setDate is correct", userTraining2.get(0).getDate().equals("2023-04-12"));
        assertFalse("User training2 setDate is correct", userTraining.get(0).getDate().equals("2023-04-13"));
        assertTrue("Two entry for traininglog", userTraining.size() == 1);
        assertTrue("Two entry for traininglog", userTraining2.size() == 1);
    }

    @Test
    void saveLog2UsersReturn1Good1NullDateCrazy() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Login createUser2 = new Login();
        createUser2.setPassword(password + "2");
        createUser2.setUser(user + "2");
        createUser2.setEmail(email + "2");
        registerService.register(createUser2);
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining("Walking");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog);
        Training trainingLog2 = new Training();
        trainingLog2.setDate(null);
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user + "2"));
        trainingService.saveLog(trainingLog2);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        List<Training> userTraining2 = trainingService.fetchUserTraining(user + "2");
        assertNotNull("User has training that is returned", userTraining);
        assertNull("User has training that is returned", userTraining2);
        assertTrue("User training2 setDate is correct", userTraining.get(0).getDate().equals("2023-04-12"));
        assertTrue("Two entry for traininglog", userTraining.size() == 1);
    }

    @Test
    void saveLog2UsersReturn1Good1LocationCrazy() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Login createUser2 = new Login();
        createUser2.setPassword(password + "2");
        createUser2.setUser(user + "2");
        createUser2.setEmail(email + "2");
        registerService.register(createUser2);
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining("Walking");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog);
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation(null);
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user + "2"));
        trainingService.saveLog(trainingLog2);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        List<Training> userTraining2 = trainingService.fetchUserTraining(user + "2");
        assertNotNull("User has training that is returned", userTraining);
        assertNull("User has training that is returned", userTraining2);
        assertTrue("User training2 setDate is correct", userTraining.get(0).getDate().equals("2023-04-12"));
        assertTrue("Two entry for traininglog", userTraining.size() == 1);
    }

    @Test
    void saveLog2UsersReturn1Good1TrainingCrazy() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Login createUser2 = new Login();
        createUser2.setPassword(password + "2");
        createUser2.setUser(user + "2");
        createUser2.setEmail(email + "2");
        registerService.register(createUser2);
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
        trainingLog2.setTraining(null);
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user + "2"));
        trainingService.saveLog(trainingLog2);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        List<Training> userTraining2 = trainingService.fetchUserTraining(user + "2");
        assertNotNull("User has training that is returned", userTraining);
        assertNull("User has training that is returned", userTraining2);
        assertTrue("User training2 setDate is correct", userTraining.get(0).getDate().equals("2023-04-12"));
        assertTrue("Two entry for traininglog", userTraining.size() == 1);
    }

    @Test
    void saveLog2UsersReturn1Good1CommentsCrazy() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Login createUser2 = new Login();
        createUser2.setPassword(password + "2");
        createUser2.setUser(user + "2");
        createUser2.setEmail(email + "2");
        registerService.register(createUser2);
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
        trainingLog2.setComments(null);
        trainingLog2.setLogin(loginService.findLogin(user + "2"));
        trainingService.saveLog(trainingLog2);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        List<Training> userTraining2 = trainingService.fetchUserTraining(user + "2");
        assertNotNull("User has training that is returned", userTraining);
        assertNull("User has training that is returned", userTraining2);
        assertTrue("User training2 setDate is correct", userTraining.get(0).getDate().equals("2023-04-12"));
        assertTrue("Two entry for traininglog", userTraining.size() == 1);
    }

    @Test
    void saveLog2UsersReturn1Good1NullCrazy() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Login createUser2 = new Login();
        createUser2.setPassword(password + "2");
        createUser2.setUser(user + "2");
        createUser2.setEmail(email + "2");
        registerService.register(createUser2);
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
        trainingLog2.setLogin(loginService.findLogin(user + "2"));
        trainingService.saveLog(null);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        List<Training> userTraining2 = trainingService.fetchUserTraining(user + "2");
        assertNotNull("User has training that is returned", userTraining);
        assertNull("User has training that is returned", userTraining2);
        assertTrue("User training2 setDate is correct", userTraining.get(0).getDate().equals("2023-04-12"));
        assertTrue("Two entry for traininglog", userTraining.size() == 1);
    }
    @Test
    void saveLog2UsersReturn1Good1BlankCrazy() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Login createUser2 = new Login();
        createUser2.setPassword(password + "2");
        createUser2.setUser(user + "2");
        createUser2.setEmail(email + "2");
        registerService.register(createUser2);
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
        trainingLog2.setLogin(loginService.findLogin(user + "2"));
        Training blank = new Training();
        trainingService.saveLog(blank);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        List<Training> userTraining2 = trainingService.fetchUserTraining(user + "2");
        assertNotNull("User has training that is returned", userTraining);
        assertNull("User has training that is returned", userTraining2);
        assertTrue("User training2 setDate is correct", userTraining.get(0).getDate().equals("2023-04-12"));
        assertTrue("Two entry for traininglog", userTraining.size() == 1);
    }

    @Test
    void fetchUserTrainingHappy() {
        List<Training> noUser = trainingService.fetchUserTraining(user);
        assertNull("Return null for a list of trainings for a user that doesnt exist", noUser);
    }
    @Test
    void fetchUserTrainingCrappy() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        List<Training> noUser = trainingService.fetchUserTraining(user+"2");
        assertNull("Return null for a list of trainings for a user that doesnt exist", noUser);
    }
    @Test
    void fetchUserTrainingCrazyNull() {
        List<Training> noUser = trainingService.fetchUserTraining(null);
        assertNull("Return null for a list of trainings for a user that doesnt exist", noUser);
    }

    @Test
    void fetchUserTrainingCrazyUserCreatePassedNull() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        List<Training> noUser = trainingService.fetchUserTraining(null);
        assertTrue("User created successfully",loginService.findLogin(user).equals(createUser));
        assertNull("Return null for a list of trainings for a user that doesnt exist", noUser);
    }

    @Test
    void fetchUserTrainingCrazyUserCreatePassedBlank() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        List<Training> noUser = trainingService.fetchUserTraining("");
        assertTrue("User created successfully",loginService.findLogin(user).equals(createUser));
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
    void fetchUserTrainingHappyDate4() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
    void fetchUserTrainingHappyLocation4() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
    void fetchUserTrainingHappyTraining4() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
    void fetchUserTrainingHappyComments4() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
    void fetchUserTrainingCrazyOneGoodOneNull() throws NoSuchAlgorithmException, InvalidKeySpecException {
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

        List<Training> userTraining = trainingService.fetchUserTraining(null);
        List<Training> userTraining2 = trainingService.fetchUserTraining(user + "2");
        assertNull("", userTraining);
        assertNotNull("", userTraining2);
        assertTrue("One entry for traininglog and not for both", userTraining2.size() == 1);
        assertTrue("Should not return the first user training = the second user training",
                userTraining2.get(0).getTraining().equals(userTraining2.get(0).getTraining()));
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
    void fetchUserTrainingCrazyTwoGoodTwoNull() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        List<Training> userTraining2 = trainingService.fetchUserTraining(null);

        assertNotNull("", userTraining);
        assertTrue("Two entry for createUser", userTraining.size() == 2);
        assertNull("", userTraining2);
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
    void fetchUserTrainingCrazyDate() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Training trainingLog = new Training();
        trainingLog.setDate(null);
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining("Walking");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
       assertNull("", userTraining);
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
    void fetchUserTrainingCrazeOneGoodOneNull() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        trainingLog2.setDate(null);
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog2);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("", trainingService.fetchUserTraining(user).get(0).getDate().equals("2023-04-12"));
        assertTrue("", userTraining.size()==1);
        assertFalse("",trainingService.saveLog(trainingLog2));

    }


    @Test
    void fetchUserTrainingCrappyDate4() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
    void fetchUserTrainingCrazyTwoGoodTwoNullDate4() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        trainingLog3.setDate(null);
        trainingLog3.setLocation("Carroll3");
        trainingLog3.setTraining("Walking3");
        trainingLog3.setComments("Good3");
        trainingLog3.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog3);
        Training trainingLog4 = new Training();
        trainingLog4.setDate(null);
        trainingLog4.setLocation("Carroll4");
        trainingLog4.setTraining("Walking4");
        trainingLog4.setComments("Good4");
        trainingLog4.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog4);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("", trainingService.fetchUserTraining(user).get(0).getDate().equals("2023-04-12"));
        assertTrue("", trainingService.fetchUserTraining(user).get(1).getDate().equals("2023-04-13"));
        assertTrue("", userTraining.size()==2);
        assertFalse("",trainingService.saveLog(trainingLog3));
        assertFalse("",trainingService.saveLog(trainingLog4));
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
    void fetchUserTrainingCrazyLocation() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation(null);
        trainingLog.setTraining("Walking");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertNull("", userTraining);
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
    void fetchUserTrainingCrazyLocationOneGoodOneNull() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
        trainingLog2.setDate("2023-04-12");
        trainingLog2.setLocation(null);
        trainingLog2.setTraining("Walking");
        trainingLog2.setComments("Good");
        trainingLog.setLogin(loginService.findLogin(user));
        trainingService.saveLog(trainingLog);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertNotNull("", userTraining);
        assertTrue("", userTraining.size()==1);
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