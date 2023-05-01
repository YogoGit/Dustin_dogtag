package edu.carroll.dogtag.web.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * The UserProfileForm is used to take the information from the controller that the html page
 * posted and organized it in way that is usable for java. This form also has set min and
 * max for the three variables that can be used.  Once the correct information is added to the form
 * it can then be used to set attributes in the UserProfile model which is then sent to the UserProfileService
 * to be verified before saving it in the database.
 */
public class UserProfileForm {
    @NotBlank
    @Size(min = 1, message = "First Name must be at least 1 character long")
    @Size(max = 10, message = "First Name must be less than 10 character long")
    private String fname;
    @NotBlank
    @Size(min = 1, message = "Last Name must be at least 1 character long")
    @Size(max = 11, message = "Last Name must be less than 11 character long")
    private String lname;
    @NotBlank
    @Size(min = 12, message = "Phone must be at least 10 character long")
    @Size(max = 12, message = "Phone must be less than 10 character long")
    private String phone;

    private String user;

    public UserProfileForm() {
    }

    public UserProfileForm(String user) {
        this.user = user;
    }

    public UserProfileForm(String fname, String lname, String phone) {
        this.fname = fname;
        this.lname = lname;
        this.phone = phone;
    }

    /**
     * @return the user that is stored in the database.
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user sets the user in the database.
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the users First Name in their profile.
     */
    public String getFname() {
        return fname;
    }

    /**
     * @param fname this sets the users First Name for the profile being created.
     */
    public void setFname(String fname) {
        this.fname = fname;
    }

    /**
     * @return the users Last Name in their profile.
     */
    public String getLname() {
        return lname;
    }

    /**
     * @param lname this sets the users First Name for the profile being created.
     */
    public void setLname(String lname) {
        this.lname = lname;
    }

    /**
     * @return the users Phone Number in their profile.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone this sets the users First Name for the profile being created.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
