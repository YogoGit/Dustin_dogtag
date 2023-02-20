package edu.carroll.dogtag.web.controller;

import edu.carroll.dogtag.jpa.model.Login;
import edu.carroll.dogtag.service.LoginServiceImpl;
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
        log.info("Successfully Mapped Register page");
        return "register";
    }

    @PostMapping("/register")
    public String registerPost(@Valid @ModelAttribute RegisterForm registerForm, BindingResult result, RedirectAttributes attrs) {
        log.info("B");
        if(result.hasErrors()){
            log.info("User could not be registered");
            return "register";
        }
//        if(!registerService.validateUser(register)){
//            result.addError(new ObjectError("globalError", "User already exists"));
//            log.info("User failed validation");
//            return "register";
//        }

        Login userRegister = new Login();
        userRegister.setPassword(registerForm.getPassword());
        userRegister.setUser(registerForm.getUser());
        registerService.register(userRegister);
        attrs.addAttribute("user", registerForm.getUser());
        log.info("Registration post was successful");
        return "redirect:/registerSuccess";
    }

    @GetMapping("/registerSuccess")
    public String registerSuccess(String user, Model model){
        model.addAttribute("user", user);
        return "registerSuccess";
    }
    @GetMapping("/registerFailure")
    public String registerFailure(){return "registerFailure";}
}
