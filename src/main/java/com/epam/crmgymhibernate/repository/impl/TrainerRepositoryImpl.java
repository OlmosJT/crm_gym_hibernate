package com.epam.crmgymhibernate.repository.impl;

import com.epam.crmgymhibernate.model.Trainer;
import com.epam.crmgymhibernate.repository.AbstractGenericRepository;
import com.epam.crmgymhibernate.repository.TrainerRepository;

public class TrainerRepositoryImpl extends AbstractGenericRepository<Trainer> implements TrainerRepository {
    public TrainerRepositoryImpl() {
        setClazz(Trainer.class);
    }
}
