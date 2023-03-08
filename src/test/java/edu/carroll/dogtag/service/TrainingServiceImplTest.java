package edu.carroll.dogtag.service;

import edu.carroll.dogtag.jpa.repo.RegisterRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class TrainingServiceImplTest {

    @Autowired
    private RegisterService registerService;

    @Autowired
    private RegisterRepository registerRepository;

    @Test
    void trainingLog() {
    }

    @Test
    void traingins() {
    }
}