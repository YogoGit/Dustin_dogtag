package edu.carroll.dogtag.service;

import edu.carroll.dogtag.jpa.model.Login;
import edu.carroll.dogtag.jpa.model.UserProfile;

public interface UserProfileService {

    void setProfile(UserProfile profile);

}
