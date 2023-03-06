package edu.carroll.dogtag.web.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LoginForm {
    @NotNull
    @Size(min = 6, message = "Username must be at least 6 characters long")
    @Size(max = 15, message = "Username must be less than 16 character long")
    private String user;

    @NotNull
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Size(max = 15, message = "Password must be less than 16 character long")
    private String password;

    public LoginForm() {
    }

    public LoginForm(String user, String password) {
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