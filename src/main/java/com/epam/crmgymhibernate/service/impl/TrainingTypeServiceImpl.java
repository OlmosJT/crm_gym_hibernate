package com.epam.crmgymhibernate.service.impl;

import com.epam.crmgymhibernate.dto.universal.TrainingTypeDto;
import com.epam.crmgymhibernate.repository.TrainingTypeRepository;
import com.epam.crmgymhibernate.service.TrainingTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingTypeServiceImpl implements TrainingTypeService {

    private final TrainingTypeRepository trainingTypeRepository;

    @Autowired
    public TrainingTypeServiceImpl(TrainingTypeRepository trainingTypeRepository) {
        this.trainingTypeRepository = trainingTypeRepository;
    }

    @Override
    public List<TrainingTypeDto> getAllTrainingTypes() {
        return trainingTypeRepository.getAll()
                .stream()
                .map(trainingType -> new TrainingTypeDto(trainingType.getId(), trainingType.getName()))
                .toList();
    }
}
