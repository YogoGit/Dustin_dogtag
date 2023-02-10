/*package edu.carroll.dogtag.jpa.repo;

import java.util.List;

import edu.carroll.dogtag.jpa.model.database.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

    public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {
    // JPA throws an exception if we attempt to return a single object that doesn't exist, so return a list
    // even though we only expect either an empty list of a single element.
    List<UserProfile> findByUserProfileIgnoreCase(String userprofile);
}
*/