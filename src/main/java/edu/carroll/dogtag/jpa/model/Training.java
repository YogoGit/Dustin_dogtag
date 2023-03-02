package edu.carroll.dogtag.jpa.model;


import jakarta.persistence.*;

@Entity
@Table(name = "training")
public class Training {
    private static final long serialVersionUID = 1L;



    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "date", nullable = false)
    private String date;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "training", nullable = false)
    private String training;

    @Column(name = "comments", nullable = false)
    private String comments;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTraining() {
        return training;
    }

    public void setTraining(String training) {
        this.training = training;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Training{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", location='" + location + '\'' +
                ", training='" + training + '\'' +
                ", comments='" + comments + '\'' +
                '}';
    }
}