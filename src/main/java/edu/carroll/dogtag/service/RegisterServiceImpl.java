package edu.carroll.dogtag.service;

import edu.carroll.dogtag.jpa.model.Login;
import edu.carroll.dogtag.jpa.repo.RegisterRepository;
import edu.carroll.dogtag.web.form.RegisterForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService{

    private static final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);
    private final RegisterRepository registerRepo;

    public RegisterServiceImpl(RegisterRepository registerRepo) {
        this.registerRepo = registerRepo;
    }

    @Override
    public boolean validateUser(RegisterForm registerForm) {
        return false;
    }

    @Override
    public void register(Login register) {
        registerRepo.save(register);
    }
}



