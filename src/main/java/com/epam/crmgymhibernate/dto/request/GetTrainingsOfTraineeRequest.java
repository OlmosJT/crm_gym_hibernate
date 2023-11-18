package com.epam.crmgymhibernate.dto.request;

import com.epam.crmgymhibernate.dto.universal.TrainingTypeDto;

import java.time.LocalDateTime;

public record GetTrainingsOfTraineeRequest(
        String username,
        LocalDateTime periodFrom,
        LocalDateTime periodTo,
        String trainerUsername,
        TrainingTypeDto trainingType
) {
}


