package com.epam.crmgymhibernate.repository.impl;

import com.epam.crmgymhibernate.conf.ApplicationConf;
import com.epam.crmgymhibernate.conf.HibernateConf;
import com.epam.crmgymhibernate.model.UserEntity;
import com.epam.crmgymhibernate.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ApplicationConf.class, HibernateConf.class})
@Sql({"classpath:resources/schema.sql", "classpath:resources/data.sql"})
class UserRepositoryImplTest {

    @Autowired
    private UserRepository userRepository;


    @Test
    void testGetById_success() {
        UserEntity userEntity = userRepository.getById(2); // Jane Doe

        Assertions.assertNotNull(userEntity);

        Assertions.assertEquals("Jane", userEntity.getFirstName());
        Assertions.assertEquals("Doe", userEntity.getLastName());
        Assertions.assertEquals("jane.doe", userEntity.getUsername());
        Assertions.assertEquals("password2", userEntity.getPassword());
    }

    @Test
    void testGetById_fail_invalidId() {
        UserEntity userEntity = userRepository.getById(200); // Jane Doe

        Assertions.assertNull(userEntity);
    }

    @Test
    void testGetAll() {
        List<UserEntity> users = userRepository.getAll();
        Assertions.assertEquals(10, users.size());
    }

    @Test
    void testInsert() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(null);
        userEntity.setFirstName("Ten");
        userEntity.setLastName("Hag");
        userEntity.setUsername("Ten.Hag2023");
        userEntity.setPassword("password11");
        userEntity.setAddress("England, Manchester, St. 15");
        userEntity.setActive(true);

        userRepository.insert(userEntity);
        System.out.println(userEntity);
        Assertions.assertNotNull(userEntity.getId());
    }

    @Test
    void existByUsername_true() {
        boolean result = userRepository.existByUsername("alice.smith");
//        boolean result = userRepository.existByUsername("Ten.Hag2023");
        Assertions.assertTrue(result);
    }

    @Test
    void existByUsername_false() {
        boolean result = userRepository.existByUsername("Unexist.Username");
        Assertions.assertFalse(result);
    }

    @Test
    void testDelete() {
        UserEntity userEntity = userRepository.getById(10);
        userRepository.delete(userEntity);
        Assertions.assertFalse(userEntity.isActive());
    }

    @Test
    void testFindByProperty() {
        List<UserEntity> users = userRepository.findByProperty("username", "eva.williams");
        Assertions.assertNotNull(users);
        Assertions.assertEquals(1, users.size());
    }
}