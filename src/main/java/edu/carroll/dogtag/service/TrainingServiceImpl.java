package edu.carroll.dogtag.service;

import edu.carroll.dogtag.jpa.model.Login;
import edu.carroll.dogtag.jpa.model.Training;
import edu.carroll.dogtag.jpa.model.UserProfile;
import edu.carroll.dogtag.jpa.repo.LoginRepository;
import edu.carroll.dogtag.jpa.repo.TrainingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public void saveLog(Training trainingLog) {
        trainingRepository.save(trainingLog);
    }


    @Override
    public List<Training> fetchUserTraining(String user) {
        List<Login> fetchUser = loginRepository.findByUserIgnoreCase(user);
        if (fetchUser.isEmpty()) {
            return null;
        }
        List<Training> trainings = new ArrayList<Training>();
        trainings = trainingRepository.findAll();
        if (trainings.isEmpty()) {
            return null;
        }
        return trainings;
    }
}
