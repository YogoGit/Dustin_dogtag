package edu.carroll.dogtag.service;

import edu.carroll.dogtag.jpa.model.Login;
import edu.carroll.dogtag.jpa.model.Training;
import edu.carroll.dogtag.jpa.repo.LoginRepository;
import edu.carroll.dogtag.jpa.repo.TrainingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * TrainingService is used as the business logic for the TrainingController. It checks if the trainingLog
 * is correctly entered before saved to the database. The service then
 * saves the users trainingLog. The fetchUserTraining is used to find the trainings that have been
 * entered already for that user.
 */
@Service
public class TrainingServiceImpl implements TrainingService {
    private static final Logger log = LoggerFactory.getLogger(TrainingServiceImpl.class);
    private final TrainingRepository trainingRepository;
    private final LoginRepository loginRepository;

    public TrainingServiceImpl(TrainingRepository trainingRepository, LoginRepository loginRepository) {
        this.trainingRepository = trainingRepository;
        this.loginRepository = loginRepository;
    }

    /**
     * saveLog is used once all the information is correct being given to
     * this method to send to the database
     *
     * @param trainingLog is the Users information that has been set by using the form information.
     * @return a boolean is returned to give feedback. If any of the trainingLog being
     * passed to the saveLog method is null or blank a false is returned.  Only a true
     * is returned if no null or blank entries are on the trainingLog that is trying
     * to be saved in the database. TrainingLogs are then sent back to the TrainingController
     * to be listed in the template traininglog.html.
     */
    @Override
    public boolean saveTrainingLog(Training trainingLog) {
        Login blank = new Login();
        if (trainingLog == null || trainingLog.equals(blank)) {
            log.error("Traininglog can not be passed as null");
            return false;
        }
        if (trainingLog.getDate() == null || trainingLog.getDate().isBlank()) {
            log.error("Traininglog date field was null or blank {}", trainingLog.getDate());
            return false;
        }
        if (trainingLog.getLocation() == null || trainingLog.getLocation().isBlank()) {
            log.error("Traininglog location was null or blank {}", trainingLog.getLocation());
            return false;
        }
        if (trainingLog.getTraining() == null || trainingLog.getTraining().isBlank()) {
            log.error("Traininglog training was null or black for {} ", trainingLog.getTraining());
            return false;
        }
        if (trainingLog.getComments() == null || trainingLog.getComments().isBlank()) {
            log.error("Traininglog comment was null or black {} ", trainingLog.getComments());
            return false;
        }
        if (trainingLog.getLogin() == null || trainingLog.equals(blank)) {
            log.error("Traininglog comment was null or black {} ", trainingLog.getComments());
            return false;
        } else {
            log.info("Traininglog {} was saved successfully", trainingLog);
            trainingRepository.save(trainingLog);
            return true;
        }
    }

    /**
     * This is used to find the trainings that have been
     * entered already for that user that is being passed to the method
     * with multiple services using them to
     *
     * @param user the training user that is being looked for to pull training that they
     *             have entered the database.
     * @return List of Trainings that are for that user if all list are returned correctly
     * from a valid user still.
     */
    @Override
    public List<Training> fetchUserTraining(String user) {
        if (user == null || user.isBlank()) {
            return Collections.EMPTY_LIST;
        }
        List<Login> fetchUser = loginRepository.findByUserIgnoreCase(user);
        if (user == null || user.isBlank()) {
            return Collections.EMPTY_LIST;
        }
        if (fetchUser.isEmpty()) {
            log.debug("fetchUser List was empty with size 0 for user: {}", user);
            return Collections.EMPTY_LIST;
        }
        List<Training> trainings = trainingRepository.findByLogin_Id(fetchUser.get(0).getId());
        if (trainings.isEmpty()) {
            log.debug("trainings List was empty with size 0 for user: {}", user);
        } else {
            log.info("fetchUser List was successfully found {}", user);
        }
        return trainings;
    }
}
