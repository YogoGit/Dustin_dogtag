package edu.carroll.dogtag.web.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TrainingForm {

    @NotNull
    private String date;


//    @NotNull
//    @Size(min = 2, message = "Location must be at least 2 character long: Example CA")
//    @Size(max = 15, message = "Location Name must be less than 15 character long")
    private String location;
//    @NotNull
//    @Size(min = 3, message = "Training must be at least 3 character long")
//    @Size(max = 15, message = "Training must be less than 15 character long")
    private String training;
//    @NotNull
//    @Size(min = 3, message = "Comments be at least 3 character long")
//    @Size(max = 20, message = "Comments must be less than 20 character long")
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
