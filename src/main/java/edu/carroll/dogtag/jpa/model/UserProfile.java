package edu.carroll.dogtag.jpa.model;


import jakarta.persistence.*;

@Entity
@Table(name = "userinfo")
public class UserProfile {
    private static final long serialVersionUID = 1L;

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

    public String getfName() {
        return fname;
    }

    public void setfName(String fName) {
        this.fname = fName;
    }

    public String getlName() {
        return lname;
    }

    public void setlName(String lName) {
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
