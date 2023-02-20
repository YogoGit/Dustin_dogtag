package edu.carroll.dogtag.web.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RegisterForm {

    @NotNull
    @Size(min = 6, message = "Username must be at least 6 characters long")
    private String user;

    @NotNull
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
    public RegisterForm() {
    }

    public RegisterForm(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

