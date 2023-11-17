package com.epam.crmgymhibernate.dto.universal;

import java.util.List;

public record TrainerProfileDto(
        String firstname,
        String lastname,
        List<TrainingTypeDto> specializations,
        boolean isActive,
        List<TraineeProfileDto> traineesList
) {
}
