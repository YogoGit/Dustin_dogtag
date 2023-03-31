package edu.carroll.dogtag.web.controller;

import edu.carroll.dogtag.jpa.model.Login;
import edu.carroll.dogtag.jpa.model.Training;
import edu.carroll.dogtag.jpa.model.UserProfile;
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


    public TrainingController(TrainingService trainingService, LoginService loginService, UserProfileService userProfileService) {
        this.trainingService = trainingService;

        this.loginService = loginService;


        this.userProfileService = userProfileService;
    }

//    @GetMapping("/traininglog")
//    public ModelAndView trainingForm(Model model, HttpSession session) {
//        final String user = (String) session.getAttribute("user");
//        ModelAndView login = new ModelAndView("redirect:/login");
//        if (user == null || user.isBlank()) {
//            return login;
//        }
//        Login l = loginService.findLogin(user);
//        ModelAndView noUserFound = new ModelAndView("redirect:register");
//        List<Training> trainings = trainingService.fetchTrainings(user);
//        ModelAndView traininglogs = new ModelAndView("traininglog");
//        model.addAttribute("trainingForm", new TrainingForm());
//        traininglogs.addObject("trainingForm", new TrainingForm());
//        traininglogs.addObject("trainings", trainings);
//        traininglogs.addObject("fname", l.getUserProfiles().get(0).getFname());
//        traininglogs.addObject("lname", l.getUserProfiles().get(0).getLname());
//        if(l == null){
//            return noUserFound;
//        }
//
////        log.info("Successfully Mapped Register page");
////        ModelAndView traininglogs = new ModelAndView("traininglog");
////        traininglogs.addObject("trainings", loginService.findLogin(user));
//        return traininglogs;
//    }
//
//    @PostMapping("/traininglog")
//    public String trainingPost(@Valid @ModelAttribute TrainingForm trainingForm, BindingResult result, RedirectAttributes attr, HttpSession session) {
//        final String user = (String) session.getAttribute("user");
//        if (user == null || user.isBlank()) {
//            return "redirect:/login";
//        }
//        Training userTraining = new Training();
//        userTraining.setDate(trainingForm.getDate());
//        userTraining.setTraining(trainingForm.getTraining());
//        userTraining.setLocation(trainingForm.getLocation());
//        userTraining.setComments(trainingForm.getComments());
//        userTraining.setLogin(loginService.findLogin(user));
//        trainingService.saveLog(userTraining);
//        return "redirect:/traininglog";
//    }

//    @GetMapping("/traininglog")
//    public ModelAndView trainingForm(String fname, String lname, Model model, HttpSession session) {
//        final String user = (String) session.getAttribute("user");
//        if (user == null || user.isBlank()) {
//        }
//        model.addAttribute("trainingForm", new TrainingForm());
//        log.info("Successfully Mapped Register page");
//        Login userLogin = loginService.findLogin(user);
//        ModelAndView traininglogs = new ModelAndView("traininglog");
//        traininglogs.addObject("trainings", trainingService.fetchTrainings(user));
//        traininglogs.addObject("fname", userLogin.getUserProfiles().get(0).getFname());
//        traininglogs.addObject("lname", userLogin.getUserProfiles().get(0).getLname());
//        return traininglogs;
//    }
//
//    @PostMapping("/traininglog")
//    public String trainingPost(@Valid @ModelAttribute TrainingForm trainingForm, String fname, String lname, BindingResult result, RedirectAttributes attr, HttpSession session) {
//        final String user = (String) session.getAttribute("user");
//        if (user == null || user.isBlank()) {
//            return "redirect:/login";
//        }
//        Training userTraining = new Training();
//        userTraining.setDate(trainingForm.getDate());
//        userTraining.setTraining(trainingForm.getTraining());
//        userTraining.setLocation(trainingForm.getLocation());
//        userTraining.setComments(trainingForm.getComments());
//        userTraining.setLogin(loginService.findLogin(user));
//        attr.addAttribute("fname", fname);
//        attr.addAttribute("lname", lname);
//        return "redirect:/traininglog";
//    }

//    @GetMapping("/traininglog")
//    public ModelAndView trainingForm(String fname, String lname, Model model, HttpSession session) {
//        final String user = (String) session.getAttribute("user");
//        if (user == null || user.isBlank()) {
//        }
//        model.addAttribute("trainingForm", new TrainingForm());
//        log.info("Successfully Mapped Register page");
//        Login userLogin = loginService.findLogin(user);
//        ModelAndView traininglogs = new ModelAndView("traininglog");
//        traininglogs.addObject("trainings", trainingService.fetchTrainings(userLogin.getUser()));
//        traininglogs.addObject("fname", fname);
//        traininglogs.addObject("lname", lname);
//        return traininglogs;
//    }
//
//    @PostMapping("/traininglog")
//    public String trainingPost(@Valid @ModelAttribute TrainingForm trainingForm, String fname, String lname, BindingResult result, RedirectAttributes attr, HttpSession session) {
//        final String user = (String) session.getAttribute("user");
//        if (user == null || user.isBlank()) {
//            return "redirect:/login";
//        }
//        Training userTraining = new Training();
//        userTraining.setDate(trainingForm.getDate());
//        userTraining.setTraining(trainingForm.getTraining());
//        userTraining.setLocation(trainingForm.getLocation());
//        userTraining.setComments(trainingForm.getComments());
//        userTraining.setLogin(loginService.findLogin(user));
//        attr.addAttribute("fname", fname);
//        attr.addAttribute("lname", lname);
//        return "redirect:/traininglog";
//    }
@GetMapping("/traininglog")
public ModelAndView trainingForm(String fname, String lname, Model model, HttpSession session) {
    final String user = (String) session.getAttribute("user");
    if (user == null || user.isBlank()) {
    }
    Login l = loginService.findLogin(user);
    model.addAttribute("trainingForm", new TrainingForm());
    log.info("Successfully Mapped Register page");
    ModelAndView traininglogs = new ModelAndView("traininglog");
    traininglogs.addObject("trainings", trainingService.fetchUserTraining(l.getUser()));
    log.info("return list {}", trainingService.fetchUserTraining(user));
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
        userTraining.setTrainings(trainingForm.getTraining());
        userTraining.setLocation(trainingForm.getLocation());
        userTraining.setComments(trainingForm.getComments());
        userTraining.setLogin(loginService.findLogin(user));
        trainingService.saveLog(userTraining);
        trainingService.fetchUserTraining(user);
        attr.addAttribute("fname", fname);
        attr.addAttribute("lname", lname);
        return "redirect:/traininglog";
    }
}
