package edu.carroll.dogtag.web.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TrainingForm {


    @NotBlank(message = "Enter a Date")
    private String date;


    @NotBlank(message = "")
    @Size(min = 2, message = "Location must be at least 2 character long: Example CA")
    @Size(max = 15, message = "Location Name must be less than 15 character long")
    private String location;
    //    @NotNull
    @NotBlank(message = "")
    @Size(min = 3, message = "Training must be at least 3 character long")
    @Size(max = 15, message = "Training must be less than 15 character long")
    private String training;

    @NotBlank(message = "")
    @Size(min = 3, message = "Comments be at least 3 character long")
    @Size(max = 20, message = "Comments must be less than 20 character long")

    private String comments;

    public TrainingForm() {
    }

    public TrainingForm(String date, String location, String training, String comments) {
        this.date = date;
        this.location = location;
        this.training = training;
        this.comments = comments;
    }

    /**
     * @return the date that the training took place for that entry.
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date sets the date the log was entered
     */
    public void setDate(String date) {
        this.date = date;

    }


    /**
     * @return the location that the training took place for that entry.
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location sets the location for the log was entered
     */
    public void setLocation(String location) {
        this.location = location;

    }

    /**
     * @return the training that took place for that entry.
     */
    public String getTraining() {
        return training;
    }

    /**
     * @param training sets the training that took place for the log entry.
     */
    public void setTraining(String training) {
        this.training = training;

    }

    /**
     * @return the date that the training took place for that entry.
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments sets the explanation for the training that took place for the log entry.
     */
    public void setComments(String comments) {
        this.comments = comments;

    }
}
