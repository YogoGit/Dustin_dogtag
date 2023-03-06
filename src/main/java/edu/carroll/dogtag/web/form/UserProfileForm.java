package edu.carroll.dogtag.web.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserProfileForm {
    @NotNull
    @Size(min = 1, message = "First Name must be at least 1 character long")
    @Size(max = 50, message = "First Name must be less than 50 character long")
    private String fname;
    @NotNull
    @Size(min = 1, message = "First Last must be at least 1 character long")
    @Size(max = 50, message = "First Last must be less than 50 character long")
    private String lname;

    @NotNull
    @Size(min = 7, message = "Phone must be at least 7 character long")
    @Size(max = 20, message = "Phone Name must be less than 50 character long")
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
