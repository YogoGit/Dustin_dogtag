package edu.carroll.dogtag.jpa.repo;

import java.util.List;

import edu.carroll.dogtag.jpa.model.database.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Auth, Integer> {
    // JPA throws an exception if we attempt to return a single object that doesn't exist, so return a list
    // even though we only expect either an empty list of a single element.
    List<Auth> findByUserIgnoreCase(String user);
}
