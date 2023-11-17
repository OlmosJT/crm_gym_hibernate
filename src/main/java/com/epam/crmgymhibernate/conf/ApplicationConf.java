package com.epam.crmgymhibernate.conf;

import com.epam.crmgymhibernate.model.Trainee;
import com.epam.crmgymhibernate.model.Trainer;
import com.epam.crmgymhibernate.model.TrainingType;
import com.epam.crmgymhibernate.model.UserEntity;
import com.epam.crmgymhibernate.repository.GenericRepository;
import com.epam.crmgymhibernate.repository.TraineeRepository;
import com.epam.crmgymhibernate.repository.impl.TraineeRepositoryImpl;
import com.epam.crmgymhibernate.repository.impl.TrainerRepositoryImpl;
import com.epam.crmgymhibernate.repository.impl.TrainingTypeRepositoryImpl;
import com.epam.crmgymhibernate.repository.impl.UserRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.epam.crmgymhibernate")
public class ApplicationConf {
    @Bean
    public GenericRepository<UserEntity> userRepository() {
        return new UserRepositoryImpl();
    }

    @Bean
    public TraineeRepository traineeRepository() {
        return new TraineeRepositoryImpl();
    }

    @Bean
    public GenericRepository<TrainingType> trainingTypeRepository() {
        return new TrainingTypeRepositoryImpl();
    }

    @Bean
    public GenericRepository<Trainer> trainerRepository() {
        return new TrainerRepositoryImpl();
    }
}
