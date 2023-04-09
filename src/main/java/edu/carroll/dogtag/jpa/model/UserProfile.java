package edu.carroll.dogtag.jpa.model;


import jakarta.persistence.*;

/**
 * UserProfile is the database model. If it has not been created, yet it will do it on the first run.
 * Database Table name is "userinfo"
 * Columns: fname = first name, lname = lastname,and phone.
 * This model is used to set a users information and link it to their username that they have created.d
 */

@Entity
@Table(name = "userinfo")
public class UserProfile {
    private static final long serialVersionUID = 1L;

    /**
     * @Id is stating to springboot that this an id field typically used for @GeneratedValue.
     * This creates a sequence number applied to each entry.
     */
    @Id
    @GeneratedValue
    private Long userProfile_id;

    /*
    This creates a linking database table between Training and Login
    Table and is connected through the "login_id"
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "login_id")
    private Login login;
    @Column(name = "fname", nullable = false)
    private String fname;
    @Column(name = "lname", nullable = false)
    private String lname;
    @Column(name = "phone", nullable = true)
    private String phone;

    /**
     * Allows for the userProfile_id for the event to be shown.
     *
     * @return the userProfile_id for that profile. Not used right now
     */
    public Long getUserProfile_id() {
        return userProfile_id;
    }

    /**
     * This set the userProfile_id but is done automatically with
     *
     * @param userProfile_id
     * @GeneratedValue
     */
    public void setUserProfile_id(Long userProfile_id) {
        this.userProfile_id = userProfile_id;
    }

    /**
     * A link to utilize the Login method
     *
     * @return login information for the user
     * this allows the use to link the two tables
     */
    public Login getLogin() {
        return login;
    }

    /**
     * This allows to set the login id for the training tables foreigner key
     *
     * @param login
     */
    public void setLogin(Login login) {
        this.login = login;
    }

    /**
     * @return unique Long number entry in this case the primary key
     */

    /**
     * @return the users First Name in their profile.
     */

    public String getFname() {
        return fname;
    }

    /**
     * @param fName this sets the users First Name for the profile being created.
     */

    public void setFname(String fName) {
        this.fname = fName;
    }

    /**
     * @return the users Last Name in their profile.
     */

    public String getLname() {
        return lname;
    }

    /**
     * @param lName this sets the users First Name for the profile being created.
     */

    public void setLname(String lName) {
        this.lname = lName;
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

    /**
     * @return the toString method for logs and debugging.
     */
    @Override
    public String toString() {
        return "UserProfile{" +
                "id=" + userProfile_id +
                ", fName='" + fname + '\'' +
                ", lName='" + lname + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
