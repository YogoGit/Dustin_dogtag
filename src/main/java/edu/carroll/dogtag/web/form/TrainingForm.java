package edu.carroll.dogtag.web.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TrainingForm {
    @NotNull
    private String date;

    @NotNull
    private String location;

    @NotNull
    private String training;

    @NotNull
    private String comments;

    public TrainingForm() {
    }

    public TrainingForm(String date, String location, String training, String comments) {
        this.date = date;
        this.location = location;
        this.training = training;
        this.comments = comments;
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
}
