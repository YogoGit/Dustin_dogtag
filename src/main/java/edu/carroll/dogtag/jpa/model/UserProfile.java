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
    private Integer id;

    @Column(name = "fname", nullable = false)
    private String fname;

    @Column(name = "lname", nullable = false)
    private String lname;


    @Column(name = "phone", nullable = true)
    private String phone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fName) {
        this.fname = fName;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lName) {
        this.lname = lName;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

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
