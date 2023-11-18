package com.epam.crmgymhibernate.service;

import com.epam.crmgymhibernate.dto.universal.TrainingTypeDto;

import java.util.List;

public interface TrainingTypeService {
    List<TrainingTypeDto> getAllTrainingTypes();
}
