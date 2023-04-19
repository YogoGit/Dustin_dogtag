package edu.carroll.dogtag.service;

import edu.carroll.dogtag.jpa.model.Login;
import edu.carroll.dogtag.jpa.model.UserProfile;
import edu.carroll.dogtag.jpa.repo.LoginRepository;
import edu.carroll.dogtag.jpa.repo.RegisterRepository;
import edu.carroll.dogtag.jpa.repo.UserProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private static final Logger log = LoggerFactory.getLogger(UserProfileServiceImpl.class);
    private final UserProfileRepository userProfileRepository;

    private final LoginRepository loginRepository;

    public UserProfileServiceImpl(UserProfileRepository userProfileRepository, RegisterRepository registerRepository, LoginRepository loginRepository) {
        this.userProfileRepository = userProfileRepository;
        this.loginRepository = loginRepository;
    }

    /**
     * This is used to find the all user profiles in the database that have been
     * entered already for that user. Then returns the one that is found or that is empty
     * this is used to see if a profile already exist and detterm where to redirect the end
     * user to
     *
     * @param user the profile that is being looked for
     * @return List of UserProfiles that are for that user
     */
    @Override
    public UserProfile fetchUserProfile(String user) {
        List<Login> fetchUser = loginRepository.findByUserIgnoreCase(user);
        if (fetchUser.isEmpty()) {
            log.debug("fetchUser List was empty with size 0 for user: {}", user);
            return null;
        }
        List<UserProfile> profiles = userProfileRepository.findByLogin(fetchUser.get(0));
        if (profiles.isEmpty()) {
            log.debug("fetchUser List was empty with size 0 for user: {}", user);
            return null;
        }
        return profiles.get(0);
    }

    /**
     * @param profile is the Users information that has been set by using the form information.
     */
    @Override
    public boolean setProfile(UserProfile profile) {
        if (profile.getPhone() == null || profile.getPhone().isBlank()) {
            log.error("profile phone field was null or blank {}", profile.getPhone());
            return false;
        }
        if (profile.getLname() == null || profile.getLname().isBlank()) {
            log.error("profile Last name field was null or blank {}", profile.getLname());
            return false;
        }
        if (profile.getFname() == null || profile.getFname().isBlank()) {
            log.error("profile First name was null or blank {}", profile.getFname());
            return false;
        } else {
            userProfileRepository.save(profile);
            log.info("profile was saved successfully {}", profile.getFname() + "" + profile.getLname() +"" + profile.getPhone());
            return true;
        }
    }
}


