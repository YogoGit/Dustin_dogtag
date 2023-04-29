package edu.carroll.dogtag;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The project was developed to support the need of the non-profit Dog Tag Buddies support veterans
 * that need additional support. The web application develop fills a gap between training logs electronically
 * and paper. Eliminates the need for having a paper log to record training and is easy to have on the go
 * Easy to use Web APP for keeping records for trainer and instructors.
 * This allows the trainers to be more likely to record every training event big or small
 */
@SpringBootApplication
public class DogtagAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(DogtagAppApplication.class, args);
    }

}
