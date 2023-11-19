package com.epam.crmgymhibernate.repository.impl;

import com.epam.crmgymhibernate.conf.ApplicationConf;
import com.epam.crmgymhibernate.conf.HibernateConf;
import com.epam.crmgymhibernate.model.Trainee;
import com.epam.crmgymhibernate.model.Trainer;
import com.epam.crmgymhibernate.model.Training;
import com.epam.crmgymhibernate.repository.TrainingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ApplicationConf.class, HibernateConf.class})
@Sql({"classpath:resources/schema.sql", "classpath:resources/data.sql"})
class TrainingRepositoryImplTest {

    @Autowired
    private TrainingRepository trainingRepository;

    @Test
    void findTraineesAssignedOnTrainer() {
        List<Trainee> traineeList = trainingRepository.findTraineesAssignedOnTrainer("sophia.wilson");
        // 6 trainings exist in database with 6 different trainee
        Assertions.assertNotNull(traineeList);
        Assertions.assertEquals(6, traineeList.size());

    }

    @Test
    void findTrainersAssignedOnTrainee() {
        List<Trainer> trainerList = trainingRepository.findTrainersAssignedOnTrainee("john.doe");
        // 2 trainings exists: TrainerId -> 2, 3
        Assertions.assertNotNull(trainerList);
        Assertions.assertEquals(2, trainerList.size());
    }

    @Test
    void findActiveTrainersNotAssignedOnTrainee() {
        List<Trainer> trainerList = trainingRepository.findActiveTrainersNotAssignedOnTrainee("john.doe");
        Assertions.assertEquals(1, trainerList.size());
        Assertions.assertEquals("daniel.miller", trainerList.get(0).getUser().getUsername());
    }

    @Test
    void findTrainingsOfTrainee_singleUsername() {
        List<Training> trainingList = trainingRepository.findTrainingsOfTrainee(
                "bob.johnson",
                null, null, null, null
        );
        Assertions.assertEquals(2, trainingList.size());
    }

    @Test
    void findTrainingsOfTrainee_username_trainerUsername() {
        List<Training> trainingList = trainingRepository.findTrainingsOfTrainee(
                "bob.johnson",
                null, null, "sophia.wilson", null
        );
        Assertions.assertEquals(1, trainingList.size());
        Assertions.assertEquals(7, trainingList.get(0).getId());
    }

    @Test
    void findTrainingsOfTrainer() {
        List<Training>  trainingList = trainingRepository.findTrainingsOfTrainer(
                "daniel.miller",
                null, null, null
        );
        Assertions.assertEquals(3, trainingList.size());
    }
}