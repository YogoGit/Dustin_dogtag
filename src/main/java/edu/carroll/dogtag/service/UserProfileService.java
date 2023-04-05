package edu.carroll.dogtag.service;

import edu.carroll.dogtag.jpa.model.UserProfile;

public interface UserProfileService {

    /**
     *
     * @param user
     * @return
     */
    UserProfile fetchUserProfile(String user);

    /**
     *
     * @param profile
     */
    void setProfile(UserProfile profile);

}
