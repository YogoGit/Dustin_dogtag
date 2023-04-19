package edu.carroll.dogtag.service;

import edu.carroll.dogtag.jpa.model.Login;
import edu.carroll.dogtag.jpa.model.Training;
import edu.carroll.dogtag.jpa.repo.LoginRepository;
import edu.carroll.dogtag.jpa.repo.TrainingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

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
     */

    @Override
    public boolean saveLog(Training trainingLog) {
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
     * @param user the training user that is being looked for
     * @return List of Trainings that are for that user
     */
    @Override
    public List<Training> fetchUserTraining(String user) {
        List<Login> fetchUser = loginRepository.findByUserIgnoreCase(user);
        if (fetchUser.isEmpty()) {
            log.debug("fetchUser List was empty with size 0 for user: {}", user);
            return null;
        }
        List<Training> trainings = trainingRepository.findByLogin_Id(fetchUser.get(0).getId());
        if (trainings.isEmpty()) {
            log.debug("trainings List was empty with size 0 for user: {}", user);
            return null;
        }
        log.info("fetchUser List was successfully found {}", user);
        return trainings;
    }
}
