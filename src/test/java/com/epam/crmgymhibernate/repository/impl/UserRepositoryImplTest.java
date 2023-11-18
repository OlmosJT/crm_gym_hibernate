package com.epam.crmgymhibernate.repository.impl;

import com.epam.crmgymhibernate.conf.ApplicationConf;
import com.epam.crmgymhibernate.conf.HibernateConf;
import com.epam.crmgymhibernate.model.UserEntity;
import com.epam.crmgymhibernate.repository.AbstractGenericRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;


@ContextConfiguration(classes = {ApplicationConf.class, HibernateConf.class})
class UserRepositoryImplTest extends AbstractGenericRepository<UserEntity> {

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testGetById() {
    }

    @Test
    void testGetAll() {
    }

    @Test
    void testInsert() {
    }

    @Test
    void testDelete() {
    }

    @Test
    void testUpdate() {
    }

    @Test
    void testFindByProperty() {
    }

    @Test
    void existByUsername() {
    }
}