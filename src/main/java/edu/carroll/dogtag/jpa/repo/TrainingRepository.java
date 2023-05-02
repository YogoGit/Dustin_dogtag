package edu.carroll.dogtag.jpa.repo;

import edu.carroll.dogtag.jpa.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, Long> {
    /**
     * JPA throws an exception if we attempt to return a single object that doesn't exist, so return a list
     * even though we only expect either an empty list of a single element.
     *
     * @param trainer is being looked for by login in id to display all training for the user.
     * @return a List of all the Trainings done for that user.
     */
    List<Training> findByLogin_Id(Long trainer);


}
