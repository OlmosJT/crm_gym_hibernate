package com.epam.crmgymhibernate.conf;

import com.epam.crmgymhibernate.repository.*;
import com.epam.crmgymhibernate.repository.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@ComponentScan(basePackages = "com.epam.crmgymhibernate")
public class ApplicationConf {
    @Bean
    public UserRepository userRepository() {
        return new UserRepositoryImpl();
    }

    @Bean
    public TraineeRepository traineeRepository() {
        return new TraineeRepositoryImpl();
    }

    @Bean
    public TrainingTypeRepository trainingTypeRepository() {
        return new TrainingTypeRepositoryImpl();
    }

    @Bean
    public TrainerRepository trainerRepository() {
        return new TrainerRepositoryImpl();
    }

    @Bean
    TrainingRepository trainingRepository() {
        return new TrainingRepositoryImpl();
    }
}
