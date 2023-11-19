package com.epam.crmgymhibernate.repository.impl;

import com.epam.crmgymhibernate.conf.ApplicationConf;
import com.epam.crmgymhibernate.conf.HibernateConf;
import com.epam.crmgymhibernate.model.Trainee;
import com.epam.crmgymhibernate.repository.TraineeRepository;
import com.epam.crmgymhibernate.repository.TrainingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ApplicationConf.class, HibernateConf.class})
@Sql({"classpath:resources/schema.sql", "classpath:resources/data.sql"})
class TraineeRepositoryImplTest {

    @Autowired
    private TraineeRepository traineeRepository;
    @Autowired
    private TrainingRepository trainingRepository;

    @Test
    void findTraineeByUsername_success() {
        Optional<Trainee> trainee = traineeRepository.findTraineeByUsername("bob.johnson");

        Assertions.assertDoesNotThrow(trainee::get);

    }

    @Test
    void findTraineeByUsername_fail() {
        Optional<Trainee> trainee = traineeRepository.findTraineeByUsername("unknown.user404");

        Assertions.assertFalse(trainee.isPresent());

        Assertions.assertThrows(NoSuchElementException.class, trainee::get);

    }

    @Test
    void deleteTraineeByUsername() {
        traineeRepository.deleteTraineeByUsername("john.doe");

        Optional<Trainee> trainee = traineeRepository.findTraineeByUsername("john.doe");
        Assertions.assertFalse(trainee.isPresent());

        var trainings = trainingRepository.getAll();
        Assertions.assertEquals(12, trainings.size());
    }
}