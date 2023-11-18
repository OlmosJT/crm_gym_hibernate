package com.epam.crmgymhibernate.repository.impl;

import com.epam.crmgymhibernate.model.TrainingType;
import com.epam.crmgymhibernate.repository.AbstractGenericRepository;
import com.epam.crmgymhibernate.repository.TrainingTypeRepository;

public class TrainingTypeRepositoryImpl  extends AbstractGenericRepository<TrainingType> implements TrainingTypeRepository {
    public TrainingTypeRepositoryImpl() {
        setClazz(TrainingType.class);
    }

}
