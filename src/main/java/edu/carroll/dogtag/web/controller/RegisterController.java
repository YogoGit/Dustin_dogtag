package edu.carroll.dogtag.web.controller;

import edu.carroll.dogtag.jpa.model.Login;
import edu.carroll.dogtag.service.RegisterService;
import edu.carroll.dogtag.web.form.RegisterForm;
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

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        log.info("Register page loaded");
        return "register";
    }

    @PostMapping("/registerSuccess")
    public String registerSuccessPost() {
        return "redirect:/login";
    }

    @PostMapping("/register")
    public String registerPost(@Valid @ModelAttribute RegisterForm registerForm, BindingResult result, RedirectAttributes attrs) {
        log.info("Logging info for {} registration", registerForm.getUser());
        if (result.hasErrors()) {
            log.debug("{} could not be registered and was not stopped by @Notnull and @Size", registerForm.getUser());
            return "register";
        }

        if(registerService.userExists(registerForm.getUser())&&registerService.emailExists(registerForm.getEmail())){
            result.addError(new ObjectError("email", "Can not register: Please use a different Email"));
            log.info("Email {} exists in database already", registerForm.getEmail());//show what email failed vaidation
            result.addError(new ObjectError("user", "Can not register: Please use a different User Name"));
            log.info("User {} exists in database already", registerForm.getUser() );//show what user failed to register
            return "register";
        }

        if (registerService.userExists(registerForm.getUser())) {
            result.addError(new ObjectError("user", "Can not register: Please use a different User Name"));
            log.info("User {} exists in database already", registerForm.getUser() );//show what user failed to register
            return "register";
        }

        if (registerService.emailExists(registerForm.getEmail())) {
            result.addError(new ObjectError("email", "Can not register: Please use a different Email"));
            log.info("Email {} exists in database already", registerForm.getEmail());//show what email failed vaidation
            return "register";
        }


        //add if both user and email show error message indicating both

        Login userRegister = new Login();
        userRegister.setUser(registerForm.getUser());
        userRegister.setEmail(registerForm.getEmail());
        userRegister.setPassword(registerForm.getPassword());
        registerService.register(userRegister);
        attrs.addAttribute("user", registerForm.getUser());
        log.info("User {} was able to register");
        log.debug("List of info in registerForm user:{} email{}", registerForm.getUser(),registerForm.getEmail());
        return "redirect:/registerSuccess";
    }

    @GetMapping("/registerSuccess")
    public String registerSuccess(String user, Model model) {
        model.addAttribute("user", user);
        return "registerSuccess";
    }

    @GetMapping("/registerFailure")
    public String registerFailure() {
        return "registerFailure";
    }
}
