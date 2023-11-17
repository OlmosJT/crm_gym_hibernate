package com.epam.crmgymhibernate.dto.universal;

import java.time.LocalDate;
import java.util.List;

public record TraineeProfileDto(
        String firstname,
        String lastname,
        LocalDate dateOfBirth,
        String address,
        boolean isActive,
        List<TrainerProfileDto> trainersList
) {
}
