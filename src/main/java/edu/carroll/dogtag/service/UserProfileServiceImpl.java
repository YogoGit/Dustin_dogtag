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

    @Override
    public UserProfile fetchUserProfile(String user) {
        List<Login> fetchUser = loginRepository.findByUserIgnoreCase(user);
        if (fetchUser.isEmpty()) {
            return null;
        }
        List<UserProfile> profiles = userProfileRepository.findByLogin(fetchUser.get(0));
        if (profiles.isEmpty()) {
            return null;
        }
        return profiles.get(0);
    }

    @Override
    public void setProfile(UserProfile profile) {
        userProfileRepository.save(profile);
    }
}


