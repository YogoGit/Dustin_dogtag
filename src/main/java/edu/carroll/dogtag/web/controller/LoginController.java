package edu.carroll.dogtag.web.controller;

import edu.carroll.dogtag.jpa.model.UserProfile;
import edu.carroll.dogtag.service.LoginService;
import edu.carroll.dogtag.service.UserProfileService;
import edu.carroll.dogtag.web.form.LoginForm;
import jakarta.servlet.http.HttpSession;
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

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Controller
public class LoginController {

    // XXX - If anything like this is used in a real application, I will hunt you down and embarrass you to all your peers.

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    private final LoginService loginService;
    private UserProfileService userProfileService;

    public LoginController(LoginService loginService, UserProfileService userProfileService) {
        this.loginService = loginService;
        this.userProfileService = userProfileService;
    }

    /**
     * @param model used to add the loginForm to be able to pass it the PostMapping to
     *              be used.
     * @return the login is requested after the post of the index page is click to the login button
     */
    @GetMapping("/login")
    public String loginGet(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }


    //add logout

    /**
     * @param loginForm the informated being entered into the form to be submitted to check the database.
     * @param result    this is to check errors on the templates and display the error message
     * @param session   this is to create a It allows the server to store and retrieve
     *                  user-specific data between requests.
     * @return there is any errors within the form or the template itself
     * if the user is not found, password is wrong, username is not found or both
     * and error is returned "Username and password do not match known users"
     * If any errors of occur the login is page is reloaded. If no errors then
     * the control check if a profile exist in the database and gets the profile page
     * if there is not one or send to traininglog page if there is one.
     */

    @PostMapping("/login")
    public String loginPost(@Valid @ModelAttribute LoginForm loginForm, BindingResult result, HttpSession session) throws InvalidKeySpecException, NoSuchAlgorithmException {
        if (result.hasErrors()) {
            return "login";
        }
        if (!loginService.validateUser(loginForm)) {
            result.addError(new ObjectError("globalError", "Username and password do not match known users"));
            return "login";
        }
        session.setAttribute("user", loginForm.getUser());
        UserProfile profile = userProfileService.fetchUserProfile(loginForm.getUser());
        if (profile == null) {
            log.info("Login user {} does not have profile and is asked to setup one", loginForm.getUser());
            return "redirect:/profilesetup";
        }
        log.info("Login user {} has a profile and is directed to traininglog page", loginForm.getUser());
        return "redirect:/traininglog";
    }

    /**
     * @return the logout button is request and then when posted return to the logout page.
     */

    @GetMapping("/logout")
    public String logoutUserGet() {
        return "redirect:/login";
    }

    /**
     * @param session is removed and all pages redirect to login page because there
     *                is no session present.
     * @return the logout button is pressed and the session is removed from the
     * logged-in user and is then redirected to the login page.
     */
    @PostMapping("/logout")
    public String logoutUserPost(@Valid @ModelAttribute LoginForm loginForm, BindingResult result, RedirectAttributes attrs, HttpSession session) {
        session.invalidate();
        log.info("User {} has ended session and returned to login page", loginForm.getUser());
        return "redirect:/login";
    }
}
