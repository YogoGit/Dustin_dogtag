package edu.carroll.dogtag.jpa.repo;

import edu.carroll.dogtag.jpa.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegisterRepository extends JpaRepository<Login, Long> {
    /**
     * JPA throws an exception if we attempt to return a single object that doesn't exist, so return a list
     * even though we only expect either an empty list of a single element.
     *
     * @param user the user that is being looked for. This is used for check if a users exists in the database.
     * @return a List of all the users found which should be one or empty.
     */
    List<Login> findByUserIgnoreCase(String user);

    /**
     * JPA throws an exception if we attempt to return a single object that doesn't exist, so return a list
     * even though we only expect either an empty list of a single element.
     *
     * @param email the email that is being looked for. This is used for check if a email exists in the database.
     * @return a List of all the email found which should be one or empty.
     */
    List<Login> findByEmailIgnoreCase(String email);

}
