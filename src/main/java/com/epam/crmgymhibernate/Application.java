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
                (GenericRepository<UserEntity>) context.getBean("userRepository");

        UserEntity userEntity = new UserEntity(
                null,
                "Shohruh",
                "Maxmanazarov",
                "shohruh.maxmanazarov",
                "Uzo tomonlada",
                "12345pass",
                false
        );

//        repository.insert(userEntity);
//
//        System.out.println(userEntity);

//        repository.getAll().forEach(System.out::println);

    }
}
