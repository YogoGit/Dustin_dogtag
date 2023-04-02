package edu.carroll.dogtag.web.controller;

import edu.carroll.dogtag.jpa.model.Login;
import edu.carroll.dogtag.jpa.model.Training;
import edu.carroll.dogtag.jpa.repo.TrainingRepository;
import edu.carroll.dogtag.service.LoginService;
import edu.carroll.dogtag.service.TrainingService;
import edu.carroll.dogtag.service.UserProfileService;
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

import java.util.List;

@Controller
public class TrainingController {

    private static final Logger log = LoggerFactory.getLogger(TrainingController.class);
    private final TrainingService trainingService;

    private final LoginService loginService;

    private final UserProfileService userProfileService;

    private final TrainingRepository trainingRepository;

    public TrainingController(TrainingService trainingService, LoginService loginService, UserProfileService userProfileService, TrainingRepository trainingRepository) {
        this.trainingService = trainingService;

        this.loginService = loginService;


        this.userProfileService = userProfileService;

        this.trainingRepository = trainingRepository;
    }


    @GetMapping("/traininglog")
    public ModelAndView trainingForm(String fname, String lname, Model model, HttpSession session) {
        final String user = (String) session.getAttribute("user");
        if (user == null || user.isBlank()) {
        }
        Login l = loginService.findLogin(user);
        model.addAttribute("trainingForm", new TrainingForm());
        log.info("Successfully Mapped Register page");
        ModelAndView traininglogs = new ModelAndView("traininglog");
        List<Training> trainings = (trainingService.fetchUserTraining(user));
        log.info("Returned training from fetchUser {}", trainings);
        traininglogs.addObject("trainings", trainings);
        traininglogs.addObject(l);
        traininglogs.addObject("fname", userProfileService.fetchUserProfile(user).getFname());
        traininglogs.addObject("lname", userProfileService.fetchUserProfile(user).getLname());
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
        userTraining.setLogin(loginService.findLogin(user));
        trainingService.saveLog(userTraining);
        attr.addAttribute("fname", userProfileService.fetchUserProfile(user).getFname());
        attr.addAttribute("lname", userProfileService.fetchUserProfile(user).getLname());
        return "redirect:/traininglog";
    }
}
