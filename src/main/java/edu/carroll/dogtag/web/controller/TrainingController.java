package edu.carroll.dogtag.web.controller;

import edu.carroll.dogtag.jpa.model.Login;
import edu.carroll.dogtag.jpa.model.Training;
import edu.carroll.dogtag.service.RegisterService;
import edu.carroll.dogtag.service.TrainingService;
import edu.carroll.dogtag.web.form.LoginForm;
import edu.carroll.dogtag.web.form.RegisterForm;
import edu.carroll.dogtag.web.form.TrainingForm;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller
public class TrainingController {

    private static final Logger log = LoggerFactory.getLogger(TrainingController.class);
    private final TrainingService trainingService;

    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }


    @GetMapping("/traininglog")
    public String trainingForm(Model model) {
        model.addAttribute("trainingForm", new TrainingForm());
        log.info("Successfully Mapped Register page");
        return "/traininglog";
    }

    @PostMapping("/traininglog")
    public String registerPost(@Valid @ModelAttribute TrainingForm trainingForm, BindingResult result, RedirectAttributes attrs) {

        Training userTraining = new Training();
        userTraining.setDate(trainingForm.getDate());
        userTraining.setTraining(trainingForm.getTraining());
        userTraining.setLocation(trainingForm.getLocation());
        userTraining.setComments(trainingForm.getComments());
        trainingService.trainingLog(userTraining);
//        attrs.addAttribute("user", trainingForm.getUser());
//        log.info("Registration post was successful");
        return "redirect:/traininglog";
    }

}
