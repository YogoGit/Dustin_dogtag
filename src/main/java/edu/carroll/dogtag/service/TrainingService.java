package edu.carroll.dogtag.service;

import edu.carroll.dogtag.jpa.model.Login;
import edu.carroll.dogtag.jpa.model.Training;
import edu.carroll.dogtag.jpa.model.UserProfile;

import java.util.List;

public interface TrainingService {

    void saveLog(Training trainingLog);

    List<Training> fetchUserTraining(String user);
}
