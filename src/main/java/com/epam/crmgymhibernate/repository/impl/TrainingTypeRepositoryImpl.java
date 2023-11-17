package com.epam.crmgymhibernate.repository.impl;

import com.epam.crmgymhibernate.model.TrainingType;
import com.epam.crmgymhibernate.repository.AbstractGenericRepository;

public class TrainingTypeRepositoryImpl  extends AbstractGenericRepository<TrainingType> {
    public TrainingTypeRepositoryImpl() {
        setClazz(TrainingType.class);
    }
}
