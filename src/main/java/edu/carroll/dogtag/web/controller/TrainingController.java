package edu.carroll.dogtag.web.controller;

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

import java.util.List;

/**
 * The TrainingController handles the information that is entered in the html pages to the LoginForm
 * and calls methods to check if there are training logs in the database for the user that is logged in
 * and then adds the list to the model to be passed back to the html page to be displayed.  When a new log
 * is saved and the page is reloaded the training logs are fetched again in a list format, and it is then updated
 * on the html page.
 */
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
    public String trainingForm(HttpSession session, Model model) {
        final String user = (String) session.getAttribute("user");
        if (user == null || user.isBlank()) {
            log.error("Login user {} does not have session", user);
            return "redirect:/login";
        }
        List<Training> trainings = trainingService.fetchUserTraining(user);
        model.addAttribute("trainings", trainings);
        model.addAttribute("trainingForm", new TrainingForm());
        model.addAttribute("fname", userProfileService.fetchUserProfile(user).getFname());
        model.addAttribute("lname", userProfileService.fetchUserProfile(user).getLname());
        return "traininglog";
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
    // Order matters with paramiter that are being passed The BindingResult must come right after the
    // model object that is validated or else Spring will fail to validate the object and throw an exception.
    public String trainingPost(@Valid @ModelAttribute TrainingForm trainingForm, BindingResult result, String fname, String lname, HttpSession session, Model attr) {
        final String user = (String) session.getAttribute("user");
        if (user == null || user.isBlank()) {
            log.error("Login user {} does not have session", user);
            return "redirect:/login";
        }
        if (result.hasErrors()) {
            attr.addAttribute("trainings", trainingService.fetchUserTraining(user));
            attr.addAttribute("fname", fname);
            attr.addAttribute("lname", lname);
            log.error("Login user {} has errors in TrainingForm", user);
            return "traininglog";
        }

        Training userTraining = new Training();
        userTraining.setDate(trainingForm.getDate());
        userTraining.setTraining(trainingForm.getTraining());
        userTraining.setLocation(trainingForm.getLocation());
        userTraining.setComments(trainingForm.getComments());
        userTraining.setLogin(loginService.findLogin(user));
        trainingService.saveLog(userTraining);
        return "redirect:/traininglog";
    }

}
