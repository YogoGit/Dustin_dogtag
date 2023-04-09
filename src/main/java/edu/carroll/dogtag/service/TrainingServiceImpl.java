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
     * @param trainingLog
     */

    @Override
    public void saveLog(Training trainingLog) {
        trainingRepository.save(trainingLog);
    }

    /**
     * This is used to find the trainings that have been
     * entered already for that user that is being passed to the method
     * with multiple services using them to
     * @param user
     * @return List of Trainings that are for that user
     */
    @Override
    public List<Training> fetchUserTraining(String user) {
        List<Login> fetchUser = loginRepository.findByUserIgnoreCase(user);
        if (fetchUser.isEmpty()) {
            return null;
        }
        List<Training> trainings = trainingRepository.findByLogin_Id(fetchUser.get(0).getId());
//        List<Training> trainings = trainingRepository.findAll();
        if (trainings.isEmpty()) {
            return null;
        }
        ;
        return trainings;
    }
}
