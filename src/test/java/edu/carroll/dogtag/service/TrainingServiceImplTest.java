package edu.carroll.dogtag.service;
import edu.carroll.dogtag.jpa.model.Login;
import edu.carroll.dogtag.jpa.model.Training;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Collections;
import java.util.List;
import static org.springframework.test.util.AssertionErrors.*;

/**
 * Unit test to pass happy, crappy and crazy inputs to the TrainingService, and
 * it should handle all situation that are tested and return the expected results
 * a user must be registered to be in the database so that a login can be attempted
 * If no user is present in the database it will always come back with null for returning
 * a Login object. This will not allow trainingLogs to be saved into the database if no
 * user exist.
 */
@SpringBootTest
@Transactional
class TrainingServiceImplTest {

    private static final String user = "usertest";
    private static final String password = "passwordtest";
    private static final String email = "emailTest@gmail.com";

    @Autowired
    private RegisterService registerService;

    @Autowired
    private TrainingServiceImpl trainingService;

    @Autowired
    private LoginService loginService;

    @Test
    void saveTrainingLogNoLog()  {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        assertTrue("Register should return true {}",registerService.register(createUser));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertEquals("User has no training that is returned and is null", userTraining, Collections.EMPTY_LIST);
    }

    @Test
    void saveTrainingLogNoLogNullCrazy()  {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        assertTrue("Register should return true {}",registerService.register(createUser));
        assertEquals("User has no training that is returned and is null", trainingService.fetchUserTraining(null),Collections.EMPTY_LIST);
    }

    @Test
    void saveTrainingLogNoUser() {
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining("Walking");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin("LookingForNoUser"));
        assertFalse("Training log saved", trainingService.saveTrainingLog(trainingLog));
        List<Training> userTraining = trainingService.fetchUserTraining("LookingForNoUser");
        assertEquals("User does not exist and will be a empty list", userTraining, Collections.EMPTY_LIST);
    }

    @Test
    void saveTrainingLogNoUserNullCrazy() {
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining("Walking");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin("LookingForNoUser"));
        assertFalse("Training log not saved because of Null", trainingService.saveTrainingLog(null));
        List<Training> userTraining = trainingService.fetchUserTraining("LookingForNoUser");
        assertEquals("User does not exist and will be a empty list", userTraining, Collections.EMPTY_LIST);
    }

    @Test
    void saveTrainingLog()  {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        assertTrue("Register should return true {}",registerService.register(createUser));
        Training trainingLog = new Training();
        trainingLog.setDate("2023-04-12");
        trainingLog.setLocation("Carroll");
        trainingLog.setTraining("Walking");
        trainingLog.setComments("Good");
        trainingLog.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertNotEquals("User has training that is returned", userTraining,Collections.EMPTY_LIST);
        assertTrue("User training setDate is correct", userTraining.get(0).getDate().equals("2023-04-12"));
        assertTrue("One entry for traininglog", userTraining.size() == 1);
    }

    @Test
    void saveTrainingLogCrappy()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        List<Training> userTraining = trainingService.fetchUserTraining(user + "2");
        assertEquals("User has training that is returned", userTraining, Collections.EMPTY_LIST);
    }

    @Test
    void saveTrainingLogCrazyNull()  {
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
        assertFalse("SaveTraining Log should return false for null {}", trainingService.saveTrainingLog(null));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertEquals("User has training that is returned", userTraining, Collections.EMPTY_LIST);
    }

    @Test
    void saveTrainingLogCrazyBlank()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertNotEquals("Training log passed as blank and should return an empty list", userTraining, Collections.EMPTY_LIST);
        assertFalse("Cant send blank training log to service", trainingService.saveTrainingLog(trainingLog2));
    }

    @Test
    void saveTrainingLogDateCrappy()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertNotNull("User has training that is returned", userTraining);
        assertFalse("User training setDate is wrong", userTraining.get(0).getDate().equals("2023-04-13"));
        assertTrue("One entry for traininglog", userTraining.size() == 1);
    }

    @Test
    void saveTrainingLogLocationCrappy()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertNotNull("User has training that is returned", userTraining);
        assertFalse("User training setLocation is wrong", userTraining.get(0).getLocation().equals("Carroll2"));
        assertTrue("One entry for traininglog", userTraining.size() == 1);
    }

    @Test
    void saveTrainingLogTrainingCrappy()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertNotNull("User has training that is returned", userTraining);
        assertFalse("User training is wrong", userTraining.get(0).getTraining().equals("Walking2"));
        assertTrue("One entry for traininglog", userTraining.size() == 1);
    }

    @Test
    void saveTrainingLogCommentsCrappy()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertNotNull("User has training that is returned", userTraining);
        assertFalse("User training setComments is wrong", userTraining.get(0).getComments().equals("Good2"));
        assertTrue("One entry for traininglog", userTraining.size() == 1);
    }

    @Test
    void saveTrainingLogSetLoginCrappy()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertNotNull("User has training that is returned", userTraining);
        assertFalse("User training user is wrong", userTraining.get(0).getLogin().equals("usertest2"));
        assertTrue("One entry for traininglog", userTraining.size() == 1);
    }

    @Test
    void saveTrainingLogCrazyNullDate()  {
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
        assertFalse("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertEquals("Date passed as null and should return an empty list", userTraining, Collections.EMPTY_LIST);
    }

    @Test
    void saveTrainingLogCrazyBlankDate()  {
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
        assertFalse("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertEquals("User has training not saved due to null date", userTraining, Collections.EMPTY_LIST);
    }

    @Test
    void saveTrainingLogCrazyNullLocation()  {
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
        assertFalse("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertEquals("User has training not saved due to null date", userTraining, Collections.EMPTY_LIST);
    }

    @Test
    void saveTrainingLogCrazyBlankLocation()  {
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
        assertFalse("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertEquals("User has training not saved due to null date", userTraining, Collections.EMPTY_LIST);
    }

    @Test
    void saveTrainingLogCrazyNullTraining()  {
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
        assertFalse("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertEquals("User has training not saved due to null date", userTraining, Collections.EMPTY_LIST);
    }

    @Test
    void saveTrainingLogCrazyBlankTraining()  {
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
        assertFalse("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));;
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertEquals("User has training not saved due to null date", userTraining, Collections.EMPTY_LIST);
    }

    @Test
    void saveTrainingLogCrazyNullComments()  {
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
        assertFalse("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertEquals("Date passed as null and should return an empty list", userTraining, Collections.EMPTY_LIST);
    }

    @Test
    void saveTrainingLogCrazyBlankComments()  {
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
        assertFalse("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertEquals("Comments passed as blank and should return an empty list", userTraining, Collections.EMPTY_LIST);
    }

    @Test
    void saveTrainingLogCrazyNullSetLogin()  {
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
        assertFalse("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertEquals("Login passed as null and should return an empty list", userTraining, Collections.EMPTY_LIST);
    }

    @Test
    void saveTrainingLogCrazyBlankSetLogin()  {
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
        assertFalse("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertEquals("User has training not saved due to null date", userTraining, Collections.EMPTY_LIST);
    }

    @Test
    void saveTrainingLogCrazyAllNull()  {
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
        assertFalse("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertEquals("All sets set to null and passed as null and should return an empty list", userTraining, Collections.EMPTY_LIST);
    }

    @Test
    void saveTrainingLogCrazyAllBlank()  {
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
        assertFalse("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        Training trainingLog2 = new Training();
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertFalse("cant passed blank log to service", trainingService.saveTrainingLog(trainingLog2));
        assertEquals("User has training not saved due to null date", userTraining,Collections.EMPTY_LIST);
    }

    @Test
    void saveTrainingLog2()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog2));
        List<Training> userTraining2 = trainingService.fetchUserTraining(user);
        assertNotNull("User has training that is returned", userTraining2);
        assertTrue("User training setDate is correct", userTraining2.get(0).getDate().equals("2023-04-12"));
        assertTrue("User training2 setDate is correct", userTraining2.get(1).getDate().equals("2023-04-13"));
        assertTrue("Two entry for traininglog", userTraining2.size() == 2);
    }

    @Test
    void saveTrainingLog2OneHappyOneCrappy()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog2));
        List<Training> userTraining2 = trainingService.fetchUserTraining(user);
        assertNotNull("User has training that is returned", userTraining2);
        assertTrue("User training setDate is correct", userTraining2.get(0).getDate().equals("2023-04-12"));
        assertFalse("User training2 setDate is correct", userTraining2.get(1).getDate().equals("2023-04-12"));
        assertTrue("Two entry for traininglog", userTraining2.size() == 2);
    }

    @Test
    void saveTrainingLog2Crazy1Null1Blank()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        Training trainingLog3 = new Training();
        List<Training> userTraining2 = trainingService.fetchUserTraining(user);
        assertNotNull("User has training that is returned", userTraining2);
        assertFalse("Cant send a null log to service", trainingService.saveTrainingLog(null));
        assertFalse("Cant send blank log to service", trainingService.saveTrainingLog(trainingLog3));
        assertTrue("User training setDate is correct", userTraining2.get(0).getDate().equals("2023-04-12"));
        assertFalse("User training2 setDate is correct", userTraining2.get(0).getDate().equals("2023-04-13"));
        assertTrue("Two entry for traininglog", userTraining2.size() == 1);
    }

    @Test
    void saveTrainingLog4TwoHappyTwoCrappy()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog2));
        Training trainingLog3 = new Training();
        trainingLog3.setDate("2023-04-14");
        trainingLog3.setLocation("Carroll3");
        trainingLog3.setTraining("Walking3");
        trainingLog3.setComments("Good3");
        trainingLog3.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog3));
        Training trainingLog4 = new Training();
        trainingLog4.setDate("2023-04-15");
        trainingLog4.setLocation("Carroll4");
        trainingLog4.setTraining("Walking4");
        trainingLog4.setComments("Good4");
        trainingLog4.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog4));
        List<Training> userTraining4 = trainingService.fetchUserTraining(user);
        assertNotNull("User has training that is returned", userTraining4);
        assertTrue("User training setDate is correct", userTraining4.get(0).getDate().equals("2023-04-12"));
        assertTrue("User training2 setDate is correct", userTraining4.get(1).getDate().equals("2023-04-13"));
        assertFalse("User training3 setDate is correct", userTraining4.get(2).getDate().equals("2023-04-15"));
        assertFalse("User training4 setDate is correct", userTraining4.get(3).getDate().equals("2023-04-16"));
        assertTrue("Two entry for traininglog", userTraining4.size() == 4);
    }

    @Test
    void saveTrainingLog4()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog2));
        Training trainingLog3 = new Training();
        trainingLog3.setDate("2023-04-14");
        trainingLog3.setLocation("Carroll3");
        trainingLog3.setTraining("Walking3");
        trainingLog3.setComments("Good3");
        trainingLog3.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog3));
        Training trainingLog4 = new Training();
        trainingLog4.setDate("2023-04-15");
        trainingLog4.setLocation("Carroll4");
        trainingLog4.setTraining("Walking4");
        trainingLog4.setComments("Good4");
        trainingLog4.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog4));
        List<Training> userTraining4 = trainingService.fetchUserTraining(user);
        assertNotNull("User has training that is returned", userTraining4);
        assertTrue("User training setDate is correct", userTraining4.get(0).getDate().equals("2023-04-12"));
        assertTrue("User training2 setDate is correct", userTraining4.get(1).getDate().equals("2023-04-13"));
        assertTrue("User training3 setDate is correct", userTraining4.get(2).getDate().equals("2023-04-14"));
        assertTrue("User training4 setDate is correct", userTraining4.get(3).getDate().equals("2023-04-15"));
        assertTrue("Two entry for traininglog", userTraining4.size() == 4);
    }

    @Test
    void saveTrainingLog4TwoCrappyTwoHappy()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog2));
        Training trainingLog3 = new Training();
        trainingLog3.setDate("2023-04-14");
        trainingLog3.setLocation("Carroll3");
        trainingLog3.setTraining("Walking3");
        trainingLog3.setComments("Good3");
        trainingLog3.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog3));
        Training trainingLog4 = new Training();
        trainingLog4.setDate("2023-04-15");
        trainingLog4.setLocation("Carroll4");
        trainingLog4.setTraining("Walking4");
        trainingLog4.setComments("Good4");
        trainingLog4.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog4));
        List<Training> userTraining4 = trainingService.fetchUserTraining(user);
        assertNotNull("User has training that is returned", userTraining4);
        assertTrue("User training setDate is correct", userTraining4.get(0).getDate().equals("2023-04-12"));
        assertTrue("User training2 setDate is correct", userTraining4.get(1).getDate().equals("2023-04-13"));
        assertFalse("User training3 setDate is correct", userTraining4.get(2).getDate().equals("2023-04-12"));
        assertFalse("User training4 setDate is correct", userTraining4.get(3).getDate().equals("2023-04-13"));
        assertTrue("Two entry for traininglog", userTraining4.size() == 4);
    }

    @Test
    void saveTrainingLog4Crazy2Null2Blank()  {
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
        assertFalse("SaveTraining Log should return true {}", trainingService.saveTrainingLog(null));
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        assertFalse("SaveTraining Log should return true {}", trainingService.saveTrainingLog(null));
        Training trainingLog3 = new Training();
        trainingLog3.setDate("2023-04-14");
        trainingLog3.setLocation("Carroll3");
        trainingLog3.setTraining("Walking3");
        trainingLog3.setComments("Good3");
        trainingLog3.setLogin(loginService.findLogin(user));
        Training blank = new Training();
        assertFalse("SaveTraining Log should return true {}", trainingService.saveTrainingLog(blank));
        assertFalse("SaveTraining Log should return true {}", trainingService.saveTrainingLog(blank));
        Training trainingLog4 = new Training();
        trainingLog4.setDate("2023-04-15");
        trainingLog4.setLocation("Carroll4");
        trainingLog4.setTraining("Walking4");
        trainingLog4.setComments("Good4");
        trainingLog4.setLogin(loginService.findLogin(user));
        List<Training> userTraining4 = trainingService.fetchUserTraining(user);
        assertEquals("Location passed as null and should return an empty list", userTraining4, Collections.EMPTY_LIST);
    }

    @Test
    void saveTrainingLog2UsersReturn1()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user + "2"));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog2));
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
    void saveTrainingLog2UsersReturn1Crappy()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        trainingService.saveTrainingLog(trainingLog);
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user + "2"));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog2));
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
    void saveTrainingLog2UsersReturn1Good1NullDateCrazy()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        Training trainingLog2 = new Training();
        trainingLog2.setDate(null);
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user + "2"));
        assertFalse("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog2));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        List<Training> userTraining2 = trainingService.fetchUserTraining(user + "2");
        assertNotNull("User has training that is returned", userTraining);
        assertEquals("User has training that is returned", userTraining2, Collections.EMPTY_LIST);
        assertTrue("User training2 setDate is correct", userTraining.get(0).getDate().equals("2023-04-12"));
        assertTrue("Two entry for traininglog", userTraining.size() == 1);
    }

    @Test
    void saveTrainingLog2UsersReturn1Good1LocationCrazy()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation(null);
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user + "2"));
        assertFalse("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog2));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        List<Training> userTraining2 = trainingService.fetchUserTraining(user + "2");
        assertNotNull("User has training that is returned", userTraining);
        assertEquals("User has training that is returned", userTraining2, Collections.EMPTY_LIST);
        assertTrue("User training2 setDate is correct", userTraining.get(0).getDate().equals("2023-04-12"));
        assertTrue("Two entry for traininglog", userTraining.size() == 1);
    }

    @Test
    void saveTrainingLog2UsersReturn1Good1TrainingCrazy()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining(null);
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user + "2"));
        assertFalse("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog2));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        List<Training> userTraining2 = trainingService.fetchUserTraining(user + "2");
        assertNotNull("User has training that is returned", userTraining);
        assertEquals("Training passed as null and should return an empty list", userTraining2, Collections.EMPTY_LIST);
        assertTrue("User training2 setDate is correct", userTraining.get(0).getDate().equals("2023-04-12"));
        assertTrue("Two entry for traininglog", userTraining.size() == 1);
    }

    @Test
    void saveTrainingLog2UsersReturn1Good1CommentsCrazy()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments(null);
        trainingLog2.setLogin(loginService.findLogin(user + "2"));
        assertFalse("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog2));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        List<Training> userTraining2 = trainingService.fetchUserTraining(user + "2");
        assertNotNull("User has training that is returned", userTraining);
        assertEquals("Location2 passed as null and should return an empty list", userTraining2, Collections.EMPTY_LIST);
        assertTrue("User training2 setDate is correct", userTraining.get(0).getDate().equals("2023-04-12"));
        assertTrue("Two entry for traininglog", userTraining.size() == 1);
    }

    @Test
    void saveTrainingLog2UsersReturn1Good1NullCrazy()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user + "2"));
        assertFalse("SaveTraining Log should return true {}", trainingService.saveTrainingLog(null));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        List<Training> userTraining2 = trainingService.fetchUserTraining(user + "2");
        assertNotNull("User has training that is returned", userTraining);
        assertEquals("User has training that is returned", userTraining2, Collections.EMPTY_LIST);
        assertTrue("User training2 setDate is correct", userTraining.get(0).getDate().equals("2023-04-12"));
        assertTrue("Two entry for traininglog", userTraining.size() == 1);
    }

    @Test
    void saveTrainingLog2UsersReturn1Good1BlankCrazy()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user + "2"));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog2));
        Training blank = new Training();
        assertFalse("SaveTraining Log should return true {}", trainingService.saveTrainingLog(blank));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        List<Training> userTraining2 = trainingService.fetchUserTraining(user + "2");
        assertNotNull("User has training that is returned", userTraining);
        assertNotEquals("Training Log passed as blank and should return null", userTraining2, Collections.EMPTY_LIST);
        assertTrue("User training2 setDate is correct", userTraining.get(0).getDate().equals("2023-04-12"));
        assertTrue("Two entry for traininglog", userTraining.size() == 1);
    }

    @Test
    void fetchUserTrainingHappy() {
        List<Training> noUser = trainingService.fetchUserTraining(user);
        assertEquals("Return null for a list of trainings for a user that doesnt exist", noUser, Collections.EMPTY_LIST);
    }

    @Test
    void fetchUserTrainingCrappy()  {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        List<Training> noUser = trainingService.fetchUserTraining(user + "2");
        assertEquals("Return null for a list of trainings for a user that doesnt exist", noUser, Collections.EMPTY_LIST);
    }

    @Test
    void fetchUserTrainingCrazyNull() {
        List<Training> noUser = trainingService.fetchUserTraining(null);
        assertEquals("Return null for a list of trainings for a user that doesnt exist", noUser, Collections.EMPTY_LIST);
    }

    @Test
    void fetchUserTrainingCrazyUserCreatePassedNull()  {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        List<Training> noUser = trainingService.fetchUserTraining(null);
        assertTrue("User created successfully", loginService.findLogin(user).equals(createUser));
        assertEquals("Location passed as null and should return an empty list", noUser, Collections.EMPTY_LIST);
    }

    @Test
    void fetchUserTrainingCrazyUserCreatePassedBlank()  {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        List<Training> noUser = trainingService.fetchUserTraining("");
        assertTrue("User created successfully", loginService.findLogin(user).equals(createUser));
        assertEquals("Return null for a list of trainings for a user that doesnt exist", noUser, Collections.EMPTY_LIST);
    }


    @Test
    void fetchUserTrainingHappy1()  {
        Login createUser = new Login();
        createUser.setPassword(password);
        createUser.setUser(user);
        createUser.setEmail(email);
        registerService.register(createUser);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertEquals("User has no training", userTraining, Collections.EMPTY_LIST);
    }

    @Test
    void fetchUserTrainingHappy2()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertNotNull("User has training that is returned", userTraining);
        assertTrue("One entry for traininglog", userTraining.size() == 1);
    }

    @Test
    void fetchUserTrainingHappy3()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog2));
        trainingService.saveTrainingLog(trainingLog2);
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertNotNull("Has training that is returned", userTraining);
        assertTrue("Two entry for traininglog", userTraining.size() == 2);
    }

    @Test
    void fetchUserTrainingHappy4()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog2));
        Training trainingLog3 = new Training();
        trainingLog3.setDate("2023-04-14");
        trainingLog3.setLocation("Carroll3");
        trainingLog3.setTraining("Walking3");
        trainingLog3.setComments("Good3");
        trainingLog3.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog3));
        Training trainingLog4 = new Training();
        trainingLog4.setDate("2023-04-15");
        trainingLog4.setLocation("Carroll4");
        trainingLog4.setTraining("Walking4");
        trainingLog4.setComments("Good4");
        trainingLog4.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog4));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertNotNull("Has training that is returned", userTraining);
        assertTrue("Two entry for traininglog", userTraining.size() == 4);
    }

    @Test
    void fetchUserTrainingHappyDate()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertTrue("Training user fetched correct entry's", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("Training user fetched had correct value for Dated", trainingService.fetchUserTraining(user).get(0).getDate().equals("2023-04-12"));

    }

    @Test
    void fetchUserTrainingHappyDate2()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog2));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("Training user fetched had correct value for Date on the first entry", trainingService.fetchUserTraining(user).get(0).getDate().equals("2023-04-12"));
        assertTrue("Training user fetched had correct value for Date on the second entry", trainingService.fetchUserTraining(user).get(1).getDate().equals("2023-04-13"));

    }

    @Test
    void fetchUserTrainingHappyDate4()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog2));
        Training trainingLog3 = new Training();
        trainingLog3.setDate("2023-04-14");
        trainingLog3.setLocation("Carroll3");
        trainingLog3.setTraining("Walking3");
        trainingLog3.setComments("Good3");
        trainingLog3.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog3));
        Training trainingLog4 = new Training();
        trainingLog4.setDate("2023-04-15");
        trainingLog4.setLocation("Carroll4");
        trainingLog4.setTraining("Walking4");
        trainingLog4.setComments("Good4");
        trainingLog4.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog4));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("Training user fetched had correct value for Date on the first entry", trainingService.fetchUserTraining(user).get(0).getDate().equals("2023-04-12"));
        assertTrue("Training user fetched had correct value for Date on the second entry", trainingService.fetchUserTraining(user).get(1).getDate().equals("2023-04-13"));
        assertTrue("Training user fetched had correct value for Date on the third entry", trainingService.fetchUserTraining(user).get(2).getDate().equals("2023-04-14"));
        assertTrue("Training user fetched had correct value for Date on the four entry", trainingService.fetchUserTraining(user).get(3).getDate().equals("2023-04-15"));
    }

    @Test
    void fetchUserTrainingHappyLocation()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertTrue("Training user fetched correct entry's for users trainingLog", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("", trainingService.fetchUserTraining(user).get(0).getLocation().equals("Carroll"));

    }

    @Test
    void fetchUserTrainingHappyLocation2()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog2));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("", trainingService.fetchUserTraining(user).get(0).getLocation().equals("Carroll"));
        assertTrue("", trainingService.fetchUserTraining(user).get(1).getLocation().equals("Carroll2"));

    }

    @Test
    void fetchUserTrainingHappyLocation4()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog2));
        Training trainingLog3 = new Training();
        trainingLog3.setDate("2023-04-14");
        trainingLog3.setLocation("Carroll3");
        trainingLog3.setTraining("Walking3");
        trainingLog3.setComments("Good3");
        trainingLog3.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog3));
        Training trainingLog4 = new Training();
        trainingLog4.setDate("2023-04-15");
        trainingLog4.setLocation("Carroll4");
        trainingLog4.setTraining("Walking4");
        trainingLog4.setComments("Good4");
        trainingLog4.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog4));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("", trainingService.fetchUserTraining(user).get(0).getLocation().equals("Carroll"));
        assertTrue("", trainingService.fetchUserTraining(user).get(1).getLocation().equals("Carroll2"));
        assertTrue("", trainingService.fetchUserTraining(user).get(2).getLocation().equals("Carroll3"));
        assertTrue("", trainingService.fetchUserTraining(user).get(3).getLocation().equals("Carroll4"));
    }

    @Test
    void fetchUserTrainingHappyTraining()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("", trainingService.fetchUserTraining(user).get(0).getTraining().equals("Walking"));

    }

    @Test
    void fetchUserTrainingHappyTraining2()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog2));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("", trainingService.fetchUserTraining(user).get(0).getTraining().equals("Walking"));
        assertTrue("", trainingService.fetchUserTraining(user).get(1).getTraining().equals("Walking2"));

    }

    @Test
    void fetchUserTrainingHappyTraining4()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog2));
        Training trainingLog3 = new Training();
        trainingLog3.setDate("2023-04-14");
        trainingLog3.setLocation("Carroll3");
        trainingLog3.setTraining("Walking3");
        trainingLog3.setComments("Good3");
        trainingLog3.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog3));
        Training trainingLog4 = new Training();
        trainingLog4.setDate("2023-04-15");
        trainingLog4.setLocation("Carroll4");
        trainingLog4.setTraining("Walking4");
        trainingLog4.setComments("Good4");
        trainingLog4.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog4));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("", trainingService.fetchUserTraining(user).get(0).getTraining().equals("Walking"));
        assertTrue("", trainingService.fetchUserTraining(user).get(1).getTraining().equals("Walking2"));
        assertTrue("", trainingService.fetchUserTraining(user).get(2).getTraining().equals("Walking3"));
        assertTrue("", trainingService.fetchUserTraining(user).get(3).getTraining().equals("Walking4"));
    }

    @Test
    void fetchUserTrainingHappyComments()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("", trainingService.fetchUserTraining(user).get(0).getComments().equals("Good"));

    }

    @Test
    void fetchUserTrainingHappyComments2()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog2));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("", trainingService.fetchUserTraining(user).get(0).getComments().equals("Good"));
        assertTrue("", trainingService.fetchUserTraining(user).get(1).getComments().equals("Good2"));

    }

    @Test
    void fetchUserTrainingHappyComments4()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog2));
        Training trainingLog3 = new Training();
        trainingLog3.setDate("2023-04-14");
        trainingLog3.setLocation("Carroll3");
        trainingLog3.setTraining("Walking3");
        trainingLog3.setComments("Good3");
        trainingLog3.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog3));
        Training trainingLog4 = new Training();
        trainingLog4.setDate("2023-04-15");
        trainingLog4.setLocation("Carroll4");
        trainingLog4.setTraining("Walking4");
        trainingLog4.setComments("Good4");
        trainingLog4.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog4));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("", trainingService.fetchUserTraining(user).get(0).getComments().equals("Good"));
        assertTrue("", trainingService.fetchUserTraining(user).get(1).getComments().equals("Good2"));
        assertTrue("", trainingService.fetchUserTraining(user).get(2).getComments().equals("Good3"));
        assertTrue("", trainingService.fetchUserTraining(user).get(3).getComments().equals("Good4"));
    }

    @Test
    void fetchUserTrainingCrappy1()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));

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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog2));

        List<Training> userTraining = trainingService.fetchUserTraining(user);
        List<Training> userTraining2 = trainingService.fetchUserTraining(user + "2");
        assertNotNull("", userTraining);
        assertFalse("One entry for traininglog and not for both", userTraining.size() == 2);
        assertFalse("Should not return the first user training = the second user training",
                userTraining.get(0).getTraining().equals(userTraining2.get(0).getTraining()));
    }

    @Test
    void fetchUserTrainingCrazyOneGoodOneNull()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));

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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog2));

        List<Training> userTraining = trainingService.fetchUserTraining(null);
        List<Training> userTraining2 = trainingService.fetchUserTraining(user + "2");
        assertEquals("", userTraining,Collections.EMPTY_LIST);
        assertNotNull("", userTraining2);
        assertTrue("One entry for traininglog and not for both", userTraining2.size() == 1);
        assertTrue("Should not return the first user training = the second user training",
                userTraining2.get(0).getTraining().equals(userTraining2.get(0).getTraining()));
    }


    @Test
    void fetchUserTrainingCrappy2()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog2));

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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog3));
        Training trainingLog4 = new Training();
        trainingLog4.setDate("2023-04-15");
        trainingLog4.setLocation("Carroll4");
        trainingLog4.setTraining("Walking4");
        trainingLog4.setComments("Good4");
        trainingLog4.setLogin(loginService.findLogin(user + "2"));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        List<Training> userTraining = trainingService.fetchUserTraining(user + "2");
        assertNotNull("", userTraining);
        assertFalse("Two entry for createUser", userTraining.size() == 4);
    }

    @Test
    void fetchUserTrainingCrazyTwoGoodTwoNull()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog2));

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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog3));
        Training trainingLog4 = new Training();
        trainingLog4.setDate("2023-04-15");
        trainingLog4.setLocation("Carroll4");
        trainingLog4.setTraining("Walking4");
        trainingLog4.setComments("Good4");
        trainingLog4.setLogin(loginService.findLogin(user + "2"));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog4));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        List<Training> userTraining2 = trainingService.fetchUserTraining(null);

        assertNotNull("", userTraining);
        assertTrue("Two entry for createUser", userTraining.size() == 2);
        assertEquals("Passed Null for listing userTraining2", userTraining2,Collections.EMPTY_LIST);
    }

    @Test
    void fetchUserTrainingCrappyDate()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertFalse("", trainingService.fetchUserTraining(user).get(0).getDate().equals("2023-04-13"));

    }

    @Test
    void fetchUserTrainingCrazyDate()  {
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
        assertFalse("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertEquals("Set date passed as null and should return an empty list", userTraining, Collections.EMPTY_LIST);
    }

    @Test
    void fetchUserTrainingCrappyDate2()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog2));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertFalse("", trainingService.fetchUserTraining(user).get(0).getDate().equals("2023-04-13"));
        assertFalse("", trainingService.fetchUserTraining(user).get(1).getDate().equals("2023-04-14"));
        assertFalse("", trainingService.fetchUserTraining(user).get(1).getDate()
                .equals(trainingService.fetchUserTraining(user).get(0).getDate()));
    }

    @Test
    void fetchUserTrainingCrazeOneGoodOneNull()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        Training trainingLog2 = new Training();
        trainingLog2.setDate(null);
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        assertFalse("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog2));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("", trainingService.fetchUserTraining(user).get(0).getDate().equals("2023-04-12"));
        assertTrue("", userTraining.size() == 1);
        assertFalse("", trainingService.saveTrainingLog(trainingLog2));

    }


    @Test
    void fetchUserTrainingCrappyDate4()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog2));


        Training trainingLog3 = new Training();
        trainingLog3.setDate("2023-04-14");
        trainingLog3.setLocation("Carroll3");
        trainingLog3.setTraining("Walking3");
        trainingLog3.setComments("Good3");
        trainingLog3.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog3));
        Training trainingLog4 = new Training();
        trainingLog4.setDate("2023-04-15");
        trainingLog4.setLocation("Carroll4");
        trainingLog4.setTraining("Walking4");
        trainingLog4.setComments("Good4");
        trainingLog4.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog4));
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
    void fetchUserTrainingCrazyTwoGoodTwoNullDate4()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog2));


        Training trainingLog3 = new Training();
        trainingLog3.setDate(null);
        trainingLog3.setLocation("Carroll3");
        trainingLog3.setTraining("Walking3");
        trainingLog3.setComments("Good3");
        trainingLog3.setLogin(loginService.findLogin(user));
        assertFalse("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog3));
        Training trainingLog4 = new Training();
        trainingLog4.setDate(null);
        trainingLog4.setLocation("Carroll4");
        trainingLog4.setTraining("Walking4");
        trainingLog4.setComments("Good4");
        trainingLog4.setLogin(loginService.findLogin(user));
        assertFalse("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog4));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("", trainingService.fetchUserTraining(user).get(0).getDate().equals("2023-04-12"));
        assertTrue("", trainingService.fetchUserTraining(user).get(1).getDate().equals("2023-04-13"));
        assertTrue("", userTraining.size() == 2);
        assertFalse("", trainingService.saveTrainingLog(trainingLog3));
        assertFalse("", trainingService.saveTrainingLog(trainingLog4));
    }

    @Test
    void fetchUserTrainingCrappyLocation()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("", trainingService.fetchUserTraining(user).get(0).getLocation().equals("Carroll"));

    }

    @Test
    void fetchUserTrainingCrazyLocation()  {
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
        assertFalse("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertEquals("Location passed as null and should return an empty list", userTraining, Collections.EMPTY_LIST);
    }

    @Test
    void fetchUserTrainingCrappyLocation2()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog2));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("", trainingService.fetchUserTraining(user).get(0).getLocation().equals("Carroll"));
        assertTrue("", trainingService.fetchUserTraining(user).get(1).getLocation().equals("Carroll2"));

    }

    @Test
    void fetchUserTrainingCrazyLocationOneGoodOneNull()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-12");
        trainingLog2.setLocation(null);
        trainingLog2.setTraining("Walking");
        trainingLog2.setComments("Good");
        trainingLog.setLogin(loginService.findLogin(user));
        assertFalse("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog2));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertNotNull("", userTraining);
        assertTrue("", userTraining.size() == 1);
    }

    @Test
    void fetchUserTrainingCrappyLocation3()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog2));
        Training trainingLog3 = new Training();
        trainingLog3.setDate("2023-04-14");
        trainingLog3.setLocation("Carroll3");
        trainingLog3.setTraining("Walking3");
        trainingLog3.setComments("Good3");
        trainingLog3.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog3));
        Training trainingLog4 = new Training();
        trainingLog4.setDate("2023-04-15");
        trainingLog4.setLocation("Carroll4");
        trainingLog4.setTraining("Walking4");
        trainingLog4.setComments("Good4");
        trainingLog4.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog4));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("", trainingService.fetchUserTraining(user).get(0).getLocation().equals("Carroll"));
        assertTrue("", trainingService.fetchUserTraining(user).get(1).getLocation().equals("Carroll2"));
        assertTrue("", trainingService.fetchUserTraining(user).get(2).getLocation().equals("Carroll3"));
        assertTrue("", trainingService.fetchUserTraining(user).get(3).getLocation().equals("Carroll4"));
    }

    @Test
    void fetchUserTrainingCrappyTraining()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("", trainingService.fetchUserTraining(user).get(0).getTraining().equals("Walking"));

    }

    @Test
    void fetchUserTrainingCrappyTraining2()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog2));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("", trainingService.fetchUserTraining(user).get(0).getTraining().equals("Walking"));
        assertTrue("", trainingService.fetchUserTraining(user).get(1).getTraining().equals("Walking2"));

    }

    @Test
    void fetchUserTrainingCrappyTraining3()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog2));
        Training trainingLog3 = new Training();
        trainingLog3.setDate("2023-04-14");
        trainingLog3.setLocation("Carroll3");
        trainingLog3.setTraining("Walking3");
        trainingLog3.setComments("Good3");
        trainingLog3.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog3));
        Training trainingLog4 = new Training();
        trainingLog4.setDate("2023-04-15");
        trainingLog4.setLocation("Carroll4");
        trainingLog4.setTraining("Walking4");
        trainingLog4.setComments("Good4");
        trainingLog4.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog4));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("", trainingService.fetchUserTraining(user).get(0).getTraining().equals("Walking"));
        assertTrue("", trainingService.fetchUserTraining(user).get(1).getTraining().equals("Walking2"));
        assertTrue("", trainingService.fetchUserTraining(user).get(2).getTraining().equals("Walking3"));
        assertTrue("", trainingService.fetchUserTraining(user).get(3).getTraining().equals("Walking4"));
    }

    @Test
    void fetchUserTrainingCrappyComments()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("", trainingService.fetchUserTraining(user).get(0).getComments().equals("Good"));

    }

    @Test
    void fetchUserTrainingCrappyComments2()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog2));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("", trainingService.fetchUserTraining(user).get(0).getComments().equals("Good"));
        assertTrue("", trainingService.fetchUserTraining(user).get(1).getComments().equals("Good2"));

    }

    @Test
    void fetchUserTrainingCrappyComments3()  {
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
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog));
        Training trainingLog2 = new Training();
        trainingLog2.setDate("2023-04-13");
        trainingLog2.setLocation("Carroll2");
        trainingLog2.setTraining("Walking2");
        trainingLog2.setComments("Good2");
        trainingLog2.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog2));
        Training trainingLog3 = new Training();
        trainingLog3.setDate("2023-04-14");
        trainingLog3.setLocation("Carroll3");
        trainingLog3.setTraining("Walking3");
        trainingLog3.setComments("Good3");
        trainingLog3.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog3));
        Training trainingLog4 = new Training();
        trainingLog4.setDate("2023-04-15");
        trainingLog4.setLocation("Carroll4");
        trainingLog4.setTraining("Walking4");
        trainingLog4.setComments("Good4");
        trainingLog4.setLogin(loginService.findLogin(user));
        assertTrue("SaveTraining Log should return true {}", trainingService.saveTrainingLog(trainingLog4));
        List<Training> userTraining = trainingService.fetchUserTraining(user);
        assertTrue("Training user fetched correct entry's for userTraining", trainingService.fetchUserTraining(user).equals(userTraining));
        assertTrue("", trainingService.fetchUserTraining(user).get(0).getComments().equals("Good"));
        assertTrue("", trainingService.fetchUserTraining(user).get(1).getComments().equals("Good2"));
        assertTrue("", trainingService.fetchUserTraining(user).get(2).getComments().equals("Good3"));
        assertTrue("", trainingService.fetchUserTraining(user).get(3).getComments().equals("Good4"));
    }
}