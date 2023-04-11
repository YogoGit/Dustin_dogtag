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

    /**
     * @param session It allows the server to store and retrieve
     *                user-specific data between requests.
     * @return This class merely holds both to make it possible for a controller
     * to return both model and view in a single return value. ModelAndView is a value object designed
     * to hold model and view making it possible for a handler to return both model
     * and view in a single return value. In this case primarily a list of trainings for
     * the intended user that is being passed to fetchUserTraining(user).  This allows the
     * template to immediately display the new information that has been entered.
     */
    @GetMapping("/traininglog")
    public ModelAndView trainingForm(HttpSession session) {
        final String user = (String) session.getAttribute("user");
        if (user == null || user.isBlank()) {
            ModelAndView noSession = new ModelAndView("redirect:/login");
            noSession.addObject("modelAttribute", noSession);
            return noSession;
        }
        Login l = loginService.findLogin(user);

        log.info("Successfully Mapped Register page");
        ModelAndView traininglogs = new ModelAndView("traininglog");
        List<Training> trainings = trainingService.fetchUserTraining(user);
        log.info("Returned training from fetchUser {}", trainings.size());
        traininglogs.addObject("trainingForm", new TrainingForm());
        traininglogs.addObject("trainings", trainings);
        traininglogs.addObject("fname", userProfileService.fetchUserProfile(user).getFname());
        traininglogs.addObject("lname", userProfileService.fetchUserProfile(user).getLname());
        return traininglogs;
    }

    /**
     * @param trainingForm the information being entered into the form to be submitted to database.
     * @param result       this is to check errors on the templates and display the error message
     * @param attr         used to add the TrainingForm to be able to pass it the PostMapping to
     *                     be used.
     * @param session      It allows the server to store and retrieve
     *                     user-specific data between requests.
     * @return there is any errors within the form or the template itself
     * Once the checks are complete it allows the controller to submit the
     * information to the TrainingService.
     */

    @PostMapping("/traininglog")
    public String trainingPost(@Valid @ModelAttribute TrainingForm trainingForm, String fname, String lname, BindingResult result, RedirectAttributes attr, HttpSession session) {
        final String user = (String) session.getAttribute("user");
        if (user == null || user.isBlank()) {
            return "redirect:/login";
        }
        if (result.hasErrors()) {
            return "traininglog";
        }
//
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
