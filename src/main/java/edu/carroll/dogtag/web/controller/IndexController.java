package edu.carroll.dogtag.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * First page that is reached once the web app is started and the user goes to the
 * local:8080.
 */
@Controller
public class IndexController {

    /**
     * This is the first page that is returned when the local:8080 is requested
     *
     * @return the index page from the templates.
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
