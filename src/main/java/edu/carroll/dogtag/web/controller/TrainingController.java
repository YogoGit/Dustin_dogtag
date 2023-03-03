package edu.carroll.dogtag.web.controller;

import edu.carroll.dogtag.jpa.model.Training;
import edu.carroll.dogtag.jpa.model.UserProfile;
import edu.carroll.dogtag.jpa.repo.TrainingRepository;
import edu.carroll.dogtag.service.TrainingService;
import edu.carroll.dogtag.web.form.TrainingForm;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class TrainingController {

    private static final Logger log = LoggerFactory.getLogger(TrainingController.class);
    private final TrainingService trainingService;

    private final TrainingRepository trainingRepository;

    public TrainingController(TrainingService trainingService, TrainingRepository trainingRepository) {
        this.trainingService = trainingService;
        this.trainingRepository = trainingRepository;
    }

//    @GetMapping("/traininglog")
//    public ModelAndView getAllTrainings()
//    {
//        ModelAndView mav = new ModelAndView("traininglog");
//        mav.addObject("trainings", trainingRepository.findAll());
//        System.out.println(mav);
//        return mav;
//
//    }
//    @GetMapping({"/traininglog"})
//    public ModelAndView getAllEmployees() {
//        ModelAndView mav = new ModelAndView("traininglog");
//        mav.addObject("trainings", trainingService.traingins());
//        System.out.println(mav);
//        return mav;
//    }
    @GetMapping("/traininglog")
    public ModelAndView trainingForm(String fname, String lname, Model model) {
        model.addAttribute("trainingForm", new TrainingForm());
        log.info("Successfully Mapped Register page");
        ModelAndView traininglogs = new ModelAndView("traininglog");
        traininglogs.addObject("trainings", trainingRepository.findAll());
        model.addAttribute("fname", fname);
        model.addAttribute("lname", lname);
//        model.addAttribute("fname", fname);
//        model.addAttribute("lname", lname);
//        mav.addObject("fname",fname);
//        mav.addObject("lname",lname);
        return traininglogs;
    }
    @PostMapping("/traininglog")
    public String registerPost(@Valid @ModelAttribute TrainingForm trainingForm, BindingResult result, RedirectAttributes attrs,String fname, String lname) {
        Training userTraining = new Training();
        userTraining.setDate(trainingForm.getDate());
        userTraining.setTraining(trainingForm.getTraining());
        userTraining.setLocation(trainingForm.getLocation());
        userTraining.setComments(trainingForm.getComments());
        trainingService.trainingLog(userTraining);
//        attrs.addAttribute("trainingForm", new TrainingForm());
        attrs.addAttribute("fname",fname);
        attrs.addAttribute("lname", lname);
//        attrs.addAttribute("user", trainingForm.getUser());
//        log.info("Registration post was successful");
        return "redirect:/traininglog";
    }

}
