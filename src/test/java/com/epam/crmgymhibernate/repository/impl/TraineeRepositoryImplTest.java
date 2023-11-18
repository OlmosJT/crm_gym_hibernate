package com.epam.crmgymhibernate.repository.impl;

import com.epam.crmgymhibernate.conf.ApplicationConf;
import com.epam.crmgymhibernate.conf.HibernateConf;
import com.epam.crmgymhibernate.model.Trainee;
import com.epam.crmgymhibernate.repository.TraineeRepository;
import com.epam.crmgymhibernate.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ApplicationConf.class, HibernateConf.class})
@Sql({"classpath:resources/schema.sql", "classpath:resources/data.sql"})
class TraineeRepositoryImplTest {

    @Autowired
    private TraineeRepository traineeRepository;

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
//        FIXME: After deleting Trainee, trainings records which are associated with, should be deleted.
        traineeRepository.deleteTraineeByUsername("bob.johnson");

        Optional<Trainee> trainee = traineeRepository.findTraineeByUsername("bob.johnson");
        Assertions.assertFalse(trainee.isPresent());
    }
}