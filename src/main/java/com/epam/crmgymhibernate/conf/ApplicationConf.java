package com.epam.crmgymhibernate.conf;

import com.epam.crmgymhibernate.model.UserEntity;
import com.epam.crmgymhibernate.repository.GenericRepository;
import com.epam.crmgymhibernate.repository.impl.UserRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.epam.crmgymhibernate")
public class ApplicationConf {
    @Bean(name = "userDao")
    public GenericRepository<UserEntity> userRepository() {
        return new UserRepositoryImpl();
    }
}
