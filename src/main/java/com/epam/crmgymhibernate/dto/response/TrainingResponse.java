package com.epam.crmgymhibernate.dto.response;

import com.epam.crmgymhibernate.dto.universal.TrainingTypeDto;

import java.time.Duration;
import java.time.LocalDateTime;

public record TrainingResponse(
        String trainingName,
        LocalDateTime trainingDate,
        TrainingTypeDto trainingType,
        Duration trainingDuration,
        String trainer,
        String trainee
) {
}
