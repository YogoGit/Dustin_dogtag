package edu.carroll.dogtag.web.controller;

import edu.carroll.dogtag.jpa.model.Training;
import edu.carroll.dogtag.jpa.repo.TrainingRepository;
import edu.carroll.dogtag.service.TrainingService;
import edu.carroll.dogtag.web.form.TrainingForm;
import jakarta.servlet.http.HttpSession;
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

@Controller
public class TrainingController {

    private static final Logger log = LoggerFactory.getLogger(TrainingController.class);
    private final TrainingService trainingService;

    private final TrainingRepository trainingRepository;

    public TrainingController(TrainingService trainingService, TrainingRepository trainingRepository) {
        this.trainingService = trainingService;
        this.trainingRepository = trainingRepository;
    }

    @GetMapping("/traininglog")
    public ModelAndView trainingForm(String fname, String lname, Model model, HttpSession session) {
        final String user = (String) session.getAttribute("user");
        if (user == null || user.isBlank()) {
        }
        model.addAttribute("trainingForm", new TrainingForm());
        log.info("Successfully Mapped Register page");
        ModelAndView traininglogs = new ModelAndView("traininglog");
        traininglogs.addObject("trainings", trainingRepository.findAll());
        traininglogs.addObject("fname", fname);
        traininglogs.addObject("lname", lname);
        return traininglogs;
    }

    @PostMapping("/traininglog")
    public String trainingPost(@Valid @ModelAttribute TrainingForm trainingForm, String fname, String lname, BindingResult result, RedirectAttributes attr, HttpSession session) {
        final String user = (String) session.getAttribute("user");
        if (user == null || user.isBlank()) {
            return "redirect:/login";
        }
        Training userTraining = new Training();
        userTraining.setDate(trainingForm.getDate());
        userTraining.setTraining(trainingForm.getTraining());
        userTraining.setLocation(trainingForm.getLocation());
        userTraining.setComments(trainingForm.getComments());
        trainingService.trainingLog(userTraining);
        attr.addAttribute("fname", fname);
        attr.addAttribute("lname", lname);
        return "redirect:/traininglog";
    }

}
