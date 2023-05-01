package edu.carroll.dogtag.service;

import edu.carroll.dogtag.jpa.model.Training;

import java.util.List;

/**
 * TrainingService is used as the business logic for the TrainingController. It checks if the trainingLog
 * is correctly entered before saved to the database. The service then
 * saves the users trainingLog. The fetchUserTraining is used to find the trainings that have been
 * entered already for that user.
 */
public interface TrainingService {

    /**
     * saveLog is used once all the information is correct being given to
     * this method to send to the database
     *
     * @param trainingLog is the Users information that has been set by using the form information.
     */
    boolean saveTrainingLog(Training trainingLog);

    /**
     * This is used to find the trainings that have been
     * entered already for that user that is being passed to the method
     * with multiple services using them to
     *
     * @param user the training user that is being looked for
     * @return List of Trainings that are for that user
     */
    List<Training> fetchUserTraining(String user);
}
