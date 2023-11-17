package com.epam.crmgymhibernate.repository.impl;

import com.epam.crmgymhibernate.model.UserEntity;
import com.epam.crmgymhibernate.repository.AbstractGenericRepository;
import com.epam.crmgymhibernate.repository.GenericRepository;


public class UserRepositoryImpl extends AbstractGenericRepository<UserEntity> {

    public UserRepositoryImpl() {
        setClazz(UserEntity.class);
    }
}
