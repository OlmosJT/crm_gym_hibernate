package com.epam.crmgymhibernate.dto.universal;

import com.epam.crmgymhibernate.dto.response.TraineeListResponse;

import java.util.List;

public record TrainerProfileDto(
        String username,
        String firstname,
        String lastname,
        List<TrainingTypeDto> specializations,
        boolean isActive,
        List<TraineeListResponse> traineesList
) {

}
