package edu.carroll.dogtag.web.controller;

import edu.carroll.dogtag.jpa.model.UserProfile;
import edu.carroll.dogtag.service.LoginService;
import edu.carroll.dogtag.service.UserProfileService;
import edu.carroll.dogtag.web.form.UserProfileForm;
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

@Controller
public class UserProfileController {
    private static final Logger log = LoggerFactory.getLogger(UserProfileController.class);
    private final UserProfileService userProfileService;
    private final LoginService loginService;


    public UserProfileController(UserProfileService userProfileService, LoginService loginService) {
        this.userProfileService = userProfileService;
        this.loginService = loginService;
    }

    /**
     * @param model   used to add the TrainingForm to be able to pass it the PostMapping to
     *                be used.
     * @param session It allows the server to store and retrieve
     *                user-specific data between requests.
     * @return the UserProfileController is requested after the post of the button for submit is clicked
     */
    @GetMapping("/profilesetup")
    public String profileForm(Model model, HttpSession session, String fname, String lname) {
        final String user = (String) session.getAttribute("user");
        if (user == null || user.isBlank()) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        model.addAttribute("userProfileForm", new UserProfileForm(user));
        log.info("Successfully Mapped Register page");
        return "/profilesetup";
    }

    /**
     * @param userProfileForm the information being entered into the form to be submitted to database.
     * @param result          this is to check errors on the templates and display the error message
     * @param session         It allows the server to store and retrieve
     *                        user-specific data between requests.
     * @return there is any errors within the form or the template itself
     * Once the checks are complete it allows the controller to submit the
     * information to the UserProfileService.
     */
    @PostMapping("/profilesetup")
    public String profilePost(@Valid @ModelAttribute UserProfileForm userProfileForm, BindingResult result, HttpSession session, Model model, String fname, String lname) {
        final String user = (String) session.getAttribute("user");
        if (user == null || user.isBlank()) {
            return "redirect:/login";
        }
        if (result.hasErrors()) {
            model.addAttribute("user", loginService.findLogin(user).getUser());
            return "profilesetup";
        }
        UserProfile userProfile = new UserProfile();
        userProfile.setFname(userProfileForm.getFname());
        userProfile.setLname(userProfileForm.getLname());
        userProfile.setPhone(userProfileForm.getPhone());
        userProfile.setLogin(loginService.findLogin(user));
        userProfileService.setProfile(userProfile);
        model.addAttribute("fname", userProfileService.fetchUserProfile(user).getFname());
        model.addAttribute("lname", userProfileService.fetchUserProfile(user).getLname());
        log.info("Registration post was successful");
        return "redirect:/traininglog";
    }
}
