package edu.carroll.dogtag.service;

import edu.carroll.dogtag.jpa.model.UserProfile;
import edu.carroll.dogtag.jpa.repo.UserProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl implements UserProfileService {

        private static final Logger log = LoggerFactory.getLogger(RegisterServiceImpl.class);
        private final UserProfileRepository userProfileRepository;

        public UserProfileServiceImpl(UserProfileRepository userProfileRepository) {
            this.userProfileRepository = userProfileRepository;
        }

        @Override
        public void setProfile(UserProfile profile) {
            userProfileRepository.save(profile);
        }
    }


