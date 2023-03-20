package edu.carroll.dogtag.jpa.model;


import jakarta.persistence.*;

/**
 * Training is the database model. If it has not been created, yet it will do it on the first run.
 * Database Table name is "training"
 * Columns: date, location, training, and comments
 * date: date training took place
 * location: location of training such as Carroll Campus
 * training: What training was done such as Loose Leash Walking
 * Comments: How the training went such as bad and as why.
 */

@Entity
@Table(name = "training")
public class Training {
    private static final long serialVersionUID = 1L;

    /**
     * @Id is stating to springboot that this an id field typically used for @GeneratedValue.
     * This creates a sequence number applied to each entry.
     */
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