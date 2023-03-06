package edu.carroll.dogtag.web.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegisterForm {
    private static final Logger log = LoggerFactory.getLogger(RegisterForm.class);
    @NotNull
    @Size(min = 6, message = "Username must be at least 6 characters long")
    @Size(max = 15, message = "Username must be less than 16 character long")
    private String user;

    @NotNull
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Size(max = 15, message = "Password must be less than 16 character long")
    private String password;

    @NotNull
    @Size(min = 6, message = "Email must be at least 6 characters long")
    @Size(max = 50, message = "Password must be less than 16 character long")
    private String email;


    public RegisterForm() {
    }

    public RegisterForm(String user, String password, String email) {
        this.user = user;
        this.password = password;
        this.email = email;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        if (user.length() < 6 || user.length() > 50) {
            log.info("Did not meet user name min or max requirements");
        }
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password.length() < 8 || password.length() > 15) {
            log.info("Did not meet password min or max requirements");
        }
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}

