package edu.carroll.dogtag.web.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginForm {
    @NotBlank
    @Size(min = 6, message = "Username must be at least 6 characters long")
    @Size(max = 16, message = "Username must be less than 16 character long")
    private String user;

    @NotBlank
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Size(max = 16, message = "Password must be less than 16 character long")
    private String password;

    public LoginForm() {
    }

    public LoginForm(String user, String password) {
        this.user = user;
        this.password = password;
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
     * @return password to compare with what the user entered in LoginForm
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
}