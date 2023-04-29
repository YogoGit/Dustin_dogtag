package edu.carroll.dogtag.service;

import edu.carroll.dogtag.jpa.model.UserProfile;

/**
 * This is used to find the all user profiles in the database that have been
 * entered already for that user. setProfile is used once all the information is correct information has
 * being given to been handed to the method and saves the information in the
 * database using the UserProfileRepository.
 */
public interface UserProfileService {

    /**
     * This is used to find the all user profiles in the database that have been
     * entered already for that user. Then returns the one that is found or that is empty
     * this is used to see if a profile already exist and determine where to redirect the end
     * user to
     *
     * @param user that's profile is being looked for
     * @return List of UserProfiles that are for that user
     */
    UserProfile fetchUserProfile(String user);

    /**
     * setProfile is used once all the information is correct information has
     * being given to been handed to the method and saves the information in the
     * database using the UserProfileRepository.
     *
     * @param profile is the Users information that has been set by using the form information.
     */
    boolean setProfile(UserProfile profile);
}
