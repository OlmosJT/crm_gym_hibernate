package com.epam.crmgymhibernate.controller;

import com.epam.crmgymhibernate.conf.SpringWebInitializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringWebInitializer.class})
@WebAppConfiguration
class TraineeControllerTest {

    @Test
    void registerTrainee() {
    }

    @Test
    void getTraineeProfile() {
    }

    @Test
    void updateTraineeProfile() {
    }

    @Test
    void deleteTraineeProfile() {
    }

    @Test
    void activateDeactivateTraineeProfile() {
    }

    @Test
    void getTrainingsOfTrainee() {
    }

    @Test
    void getActiveTrainersNotAssignedOnTrainee() {
    }
}