package com.epam.crmgymhibernate.repository.impl;

import com.epam.crmgymhibernate.conf.ApplicationConf;
import com.epam.crmgymhibernate.conf.HibernateConf;
import com.epam.crmgymhibernate.model.Trainer;
import com.epam.crmgymhibernate.repository.TrainerRepository;
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
class TrainerRepositoryImplTest {

    @Autowired
    private TrainerRepository trainerRepository;

    @Test
    void findTrainerByUsername_success() {
        Optional<Trainer> trainer = trainerRepository.findTrainerByUsername("matthew.moore");

        Assertions.assertDoesNotThrow(trainer::get);
    }

    @Test
    void findTrainerByUsername_fail_invalidUsername() {
        Optional<Trainer> trainer = trainerRepository.findTrainerByUsername("unknown.user404");

        Assertions.assertFalse(trainer.isPresent());

        Assertions.assertThrows(NoSuchElementException.class, trainer::get);
    }

    @Test
    void deleteTrainerByUsername() {
        trainerRepository.deleteTrainerByUsername("matthew.moore");

        Trainer trainer = trainerRepository.findTrainerByUsername("matthew.moore").orElse(null);

        Assertions.assertNull(trainer);
    }

    @Test
    void updateTrainer() {
        // I do not know what to check here. All logic in service.
    }
}