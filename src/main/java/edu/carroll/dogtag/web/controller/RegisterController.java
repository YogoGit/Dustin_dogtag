package edu.carroll.dogtag.web.controller;

import edu.carroll.dogtag.jpa.model.Login;
import edu.carroll.dogtag.service.RegisterService;
import edu.carroll.dogtag.web.form.RegisterForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("register", new RegisterForm());
        System.out.println("Get Passed");
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute RegisterForm register, Model model) {

        model.addAttribute("register", register);
        System.out.println("Post Passed");
        Login userRegister = new Login();
        userRegister.setPassword(register.getPassword());
        userRegister.setUser(register.getUser());
        registerService.register(userRegister);

        return "registerSuccess";
    }
}
