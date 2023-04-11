package edu.carroll.dogtag.web.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegisterForm {
    private static final Logger log = LoggerFactory.getLogger(RegisterForm.class);
    @NotNull
    @Size(min = 6, message = "Username must be at least 6 characters long")
    @Size(max = 16, message = "Username must be less than 16 character long")
    private String user;

    @NotNull
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Size(max = 16, message = "Password must be less than 16 character long")
    private String password;

    @NotNull
    @Size(min = 6, message = "Email must be at least 6 characters long")
    @Size(max = 20, message = "Email must be less than 50 character long")
    private String email;


    public RegisterForm() {
    }

    public RegisterForm(String user, String password, String email) {
        this.user = user;
        this.password = password;
        this.email = email;
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
        if (user.length() < 6 || user.length() > 50) {
            log.info("Did not meet user name min or max requirements");
        }
        this.user = user;
    }

    /**
     * @return password to compare with what the user entered in RegisterForm
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password sets the password in the database
     */
    public void setPassword(String password) {
        if (password.length() < 8 || password.length() > 15) {
            log.info("Did not meet password min or max requirements");
        }
        this.password = password;
    }

    /**
     * @return email to compare with what the user entered in RegisterForm
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email sets the password in the database
     */
    public void setEmail(String email) {
        this.email = email;
    }


}

