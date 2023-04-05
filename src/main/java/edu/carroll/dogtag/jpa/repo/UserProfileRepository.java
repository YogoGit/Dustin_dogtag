package edu.carroll.dogtag.jpa.repo;

import edu.carroll.dogtag.jpa.model.Login;
import edu.carroll.dogtag.jpa.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    /**
     * JPA throws an exception if we attempt to return a single object that doesn't exist, so return a list
     * even though we only expect either an empty list of a single element.
     *
     * @param login  is being looked for by login in id to find if a profile exist for the user.
     * @return List of profile found. Returns either 0 or 1.
     */

    List<UserProfile> findByLogin(Login login);
}
