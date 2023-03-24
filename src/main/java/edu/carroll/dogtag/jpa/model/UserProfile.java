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
public class UserProfile{
    private static final long serialVersionUID = 1L;

    /**
     * @Id is stating to springboot that this an id field typically used for @GeneratedValue.
     * This creates a sequence number applied to each entry.
     */
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "login_id")
    private Login login;

    private Long user_id;
    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    @Column(name = "fname", nullable = false)
    private String fname;

    @Column(name = "lname", nullable = false)
    private String lname;


    @Column(name = "phone", nullable = true)
    private String phone;

    /**
     * @return unique Long number entry in this case the primary key
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id sets the unique Long number id. This is auto generated with the above
     *           springboot implementation.
     */

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return the users First Name in their profile.
     */

    public String getFname() {
        return fname;
    }

    /**
     *
     * @param fName this sets the users First Name for the profile being created.
     */

    public void setFname(String fName) {
        this.fname = fName;
    }

    /**
     *
     * @return the users Last Name in their profile.
     */

    public String getLname() {
        return lname;
    }

    /**
     *
     * @param lName this sets the users First Name for the profile being created.
     */

    public void setLname(String lName) {
        this.lname = lName;
    }

    /**
     *
     * @return the users Phone Number in their profile.
     */

    public String getPhone() {
        return phone;
    }

    /**
     *
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
                "id=" + id +
                ", fName='" + fname + '\'' +
                ", lName='" + lname + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
