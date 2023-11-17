package com.epam.crmgymhibernate.repository.impl;

import com.epam.crmgymhibernate.model.Trainee;
import com.epam.crmgymhibernate.model.UserEntity;
import com.epam.crmgymhibernate.repository.AbstractGenericRepository;

public class TraineeRepositoryImpl extends AbstractGenericRepository<Trainee> {
    public TraineeRepositoryImpl() {
        setClazz(Trainee.class);
    }


}
