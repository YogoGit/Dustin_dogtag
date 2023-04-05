package edu.carroll.dogtag.service;

import edu.carroll.dogtag.jpa.model.Training;

import java.util.List;

public interface TrainingService {

    /**
     *
     * @param trainingLog
     */

    void saveLog(Training trainingLog);

    /**
     *
     * @param user
     * @return
     */

    List<Training> fetchUserTraining(String user);
}
