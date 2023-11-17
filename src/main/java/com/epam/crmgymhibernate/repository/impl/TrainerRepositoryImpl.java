package com.epam.crmgymhibernate.repository.impl;

import com.epam.crmgymhibernate.model.Trainer;
import com.epam.crmgymhibernate.repository.AbstractGenericRepository;

public class TrainerRepositoryImpl extends AbstractGenericRepository<Trainer> {
    public TrainerRepositoryImpl() {
        setClazz(Trainer.class);
    }
}
