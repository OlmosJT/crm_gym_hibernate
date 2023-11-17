package com.epam.crmgymhibernate;

import com.epam.crmgymhibernate.conf.ApplicationConf;
import com.epam.crmgymhibernate.conf.HibernateConf;
import com.epam.crmgymhibernate.model.UserEntity;
import com.epam.crmgymhibernate.repository.GenericRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConf.class, HibernateConf.class);
        GenericRepository<UserEntity> repository =
                (GenericRepository<UserEntity>) context.getBean("userDao");

        repository.insert(new UserEntity(
                null,
                "Olmos",
                "Davronov",
                "olmos.davronv",
                "tashkent somewhere",
                "12345pass",
                false)
        );

        repository.getAll().forEach(System.out::println);

    }
}
