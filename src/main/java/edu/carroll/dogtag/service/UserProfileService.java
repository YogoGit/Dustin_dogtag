package edu.carroll.dogtag.service;

import edu.carroll.dogtag.jpa.model.UserProfile;

import java.util.List;

public interface UserProfileService {

    UserProfile fetchUserProfile(String user);

    void setProfile(UserProfile profile);

}
