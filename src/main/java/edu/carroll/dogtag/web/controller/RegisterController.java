package edu.carroll.dogtag.web.controller;

import edu.carroll.dogtag.jpa.model.Login;
import edu.carroll.dogtag.service.RegisterService;
import edu.carroll.dogtag.web.form.RegisterForm;
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

@Controller
public class RegisterController {
    private static final Logger log = LoggerFactory.getLogger(RegisterController.class);
    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    /**
     * @param model used to add the RegisterFom to be able to pass it the PostMapping to
     *              be used.
     * @return the register is requested after the post of the button for register is clicked
     */
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        log.info("Register page loaded");
        return "register";
    }

    /**
     * @param registerForm the information being entered into the form to be submitted to database.
     * @param result this is to check errors on the templates and display the error message
     * @param attrs used to redirect user info to the register success pages to display the registered
     *              name entered.
     * @return there is any errors within the form or the template itself
     *      if the user is found, email is found, or required entry are not met
     *      and error is returned for using a different username or email.
     *      If any errors of occur the register is page is reloaded. If no errors then
     *      the control check if a user or email exist in the database.
     *      Once the checks are complete it allows the controller to submit the information to the
     *      RegisterService and then is sent to the register success page.
     */
    @PostMapping("/register")
    public String registerPost(@Valid @ModelAttribute RegisterForm registerForm, BindingResult result, RedirectAttributes attrs) {
        log.info("Logging info for {} registration", registerForm.getUser());
        if (result.hasErrors()) {
            log.debug("{} could not be registered and was not stopped by @Notnull and @Size", registerForm.getUser());
            return "register";
        }

        if (registerService.userExists(registerForm.getUser()) && registerService.emailExists(registerForm.getEmail())) {
            result.addError(new ObjectError("email", "Can not register: Please use a different Email"));
            log.info("Email {} exists in database already", registerForm.getEmail());//show what email failed vaidation
            result.addError(new ObjectError("user", "Can not register: Please use a different User Name"));
            log.info("User {} exists in database already", registerForm.getUser());//show what user failed to register
            return "register";
        }

        if (registerService.userExists(registerForm.getUser())) {
            result.addError(new ObjectError("user", "Can not register: Please use a different User Name"));
            log.info("User {} exists in database already", registerForm.getUser());//show what user failed to register
            return "register";
        }

        if (registerService.emailExists(registerForm.getEmail())) {
            result.addError(new ObjectError("email", "Can not register: Please use a different Email"));
            log.info("Email {} exists in database already", registerForm.getEmail());//show what email failed vaidation
            return "register";
        }

        Login userRegister = new Login();
        userRegister.setUser(registerForm.getUser());
        userRegister.setEmail(registerForm.getEmail());
        userRegister.setPassword(registerForm.getPassword());
        registerService.register(userRegister);
        attrs.addAttribute("user", registerForm.getUser());
        log.info("User {} was able to register");
        log.debug("List of info in registerForm user:{} email{}", registerForm.getUser(), registerForm.getEmail());
        return "redirect:/registerSuccess";
    }

    /**
     *
     * @param model used to send the users new register information to the register page
     * @param session this is to create a allows the server to store and retrieve
     *                      user-specific data between requests.
     * @return
     */
    @GetMapping("/registerSuccess")
    public String registerSuccess(Model model, HttpSession session) {
        final String user = (String) session.getAttribute("user");
        if (user == null || user.isBlank()) {
            return "redirect:/register";
        }
        model.addAttribute("user", user);
        return "registerSuccess";
    }

    /**
     * @return this is once the login button is click and returns the user to
     * login page to enter the new login information submited to the database
     */
    @PostMapping("/registerSuccess")
    public String registerSuccessPost() {
        return "redirect:/login";
    }
}

