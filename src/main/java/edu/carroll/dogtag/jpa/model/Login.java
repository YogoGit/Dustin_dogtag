package edu.carroll.dogtag.jpa.model;

import jakarta.persistence.*;

import java.util.Objects;


@Entity
@Table(name = "login")
public class Login {

    private static final long serialVersionUID = 1L;
    private static final String EOL = System.lineSeparator();
    private static final String TAB = "\t";
    @Id
    @GeneratedValue
    private Long id; //switch to long
    @Column(name = "username", nullable = false, unique = true)
    private String user;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "email", nullable = false)
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        String builder = "Login @ " + super.toString() + "[" + EOL +
                TAB + "user=" + user + EOL +
                TAB + "password=" + "****" + EOL +
                "]" + EOL;
        return builder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        final Login login = (Login) o;
        return user.equals(login.user) && password.equals(login.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, password);
    }


}