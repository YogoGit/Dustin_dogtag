package edu.carroll.dogtag.service;

import edu.carroll.dogtag.jpa.model.Login;
import edu.carroll.dogtag.jpa.model.UserProfile;
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

    private final RegisterRepository registerRepository;

    public UserProfileServiceImpl(UserProfileRepository userProfileRepository, RegisterRepository registerRepository) {
        this.userProfileRepository = userProfileRepository;
        this.registerRepository = registerRepository;
    }

    @Override
    public Login userList(String user) {
        return null;
    }

    @Override
    public Login fetchLoginFromUser(String user){
        List<Login> users = registerRepository.findByUserIgnoreCase(user);
        return users.get(0);
    }
    @Override
    public void setProfile(UserProfile profile) {
        userProfileRepository.save(profile);
    }
}


