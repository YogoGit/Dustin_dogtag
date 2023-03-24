package edu.carroll.dogtag.service;

import edu.carroll.dogtag.jpa.model.Login;
import edu.carroll.dogtag.jpa.model.UserProfile;

public interface UserProfileService {

    Login userList(String user);

    Login fetchLoginFromUser(String user);

    void setProfile(UserProfile profile);

}
