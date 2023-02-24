package edu.carroll.dogtag.web.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserProfileForm {

    private String fname;
    private String lname;

    private String phone;

    public UserProfileForm() {
    }

    public UserProfileForm(String fname, String lname, String phone) {
        this.fname = fname;
        this.lname = lname;
        this.phone = phone;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
