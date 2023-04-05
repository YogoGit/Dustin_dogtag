package edu.carroll.dogtag.jpa.model;

import jakarta.persistence.*;

import java.util.List;

/**
 * Login is the database model. If it has not been created, yet it will do it on the first run.
 * Database Table name is "login"
 * Used in storing or checking to see if a user and password match.
 * Columns: username, password and email
 * Registering a new user also uses this template on how to enter a new entry information into the database
 */
@Entity
@Table(name = "login")

public class Login {
    private static final long serialVersionUID = 1L;
    private static final String EOL = System.lineSeparator();
    private static final String TAB = "\t";

    /**
     * @Id is stating to springboot that this an id field typically used for @GeneratedValue.
     * This creates a sequence number applied to each entry.
     */
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userProfile_id")
    private List<UserProfile> userProfiles;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "training_id")
    private List<Training> training;


    @Column(name = "username", nullable = false, unique = true)
    private String user;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "email", nullable = false, unique = true)
    private String email;


    public Login() {
    }

    public List<Training> getTrainings() {
        return training;
    }

    public void setTrainings(List<Training> trainings) {
        this.training = trainings;
    }

    public void setTraining(List<Training> training) {
        this.training = training;
    }

    public List<UserProfile> getUserProfiles() {
        return userProfiles;
    }

    public void setUserProfiles(List<UserProfile> userProfiles) {
        this.userProfiles = userProfiles;
    }

    /**
     * @return unique Long number entry in this case the primary key
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id sets the unique Long number id. This is auto generated with the above
     *           springboot implementation.
     */

    public void setId(Long id) {
        this.id = id;
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
     * @return password in hashed form to compare with what the user entered in LoginForm
     */

    public String getPassword() {
        return password;
    }

    /**
     * @param password sets the password in the database
     */

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return email is returned from the database and
     * is used to check if email is already in use
     */

    public String getEmail() {
        return email;
    }

    /**
     * @param email sets the email entry into the database
     */

    public void setEmail(String email) {

        this.email = email;

    }

    /**
     * @return the toString method for logs and debugging.
     */

    @Override
    public String toString() {
        String builder = "Login @ " + super.toString() + "[" + EOL +
                TAB + "user=" + user + EOL +
                TAB + "password=" + "****" + EOL +
                "]" + EOL;
        return builder;
    }

    /**
     * @param o the object being checked to do an equals function with.
     * @return In this case if it is the object return true.  If it is null or does not equal
     * the class return false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        final Login login = (Login) o;
        return user.equals(login.user) && password.equals(login.password);
    }
}