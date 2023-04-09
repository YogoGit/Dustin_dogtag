package edu.carroll.dogtag.web.controller;

import edu.carroll.dogtag.jpa.model.UserProfile;
import edu.carroll.dogtag.service.LoginService;
import edu.carroll.dogtag.service.UserProfileService;
import edu.carroll.dogtag.web.form.LoginForm;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    // XXX - If anything like this is used in a real application, I will hunt you down and embarrass you to all your peers.

    private final LoginService loginService;

    private UserProfileService userProfileService;

    public LoginController(LoginService loginService, UserProfileService userProfileService) {
        this.loginService = loginService;
        this.userProfileService = userProfileService;
    }

    /**
     * @param model
     * @param session
     * @return
     */
    @GetMapping("/login")
    public String loginGet(Model model, HttpSession session) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }


    //add logout

    /**
     * @param loginForm
     * @param result
     * @param attrs
     * @param session
     * @return
     */

    @PostMapping("/login")
    public String loginPost(@Valid @ModelAttribute LoginForm loginForm, BindingResult result, RedirectAttributes attrs, HttpSession session) {
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
            return "redirect:/profilesetup";
        }
        return "redirect:/traininglog";
    }

    /**
     * @return
     */
    @GetMapping("/logout")
    public String logoutUserGet() {
        return "redirect:/login";
    }

    /**
     * @param loginForm
     * @param result
     * @param attrs
     * @param session
     * @return
     */
    @PostMapping("/logout")
    public String logoutUserPost(@Valid @ModelAttribute LoginForm loginForm, BindingResult result, RedirectAttributes attrs, HttpSession session) {
        session.invalidate();
        return "redirect:/traininglog";
    }
}
