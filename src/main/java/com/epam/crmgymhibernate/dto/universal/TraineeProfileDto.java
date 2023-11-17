package com.epam.crmgymhibernate.dto.universal;

import com.epam.crmgymhibernate.dto.response.TrainerListResponse;

import java.time.LocalDate;
import java.util.List;

public record TraineeProfileDto(
        String username,
        String firstname,
        String lastname,
        LocalDate dateOfBirth,
        String address,
        boolean isActive,
        List<TrainerListResponse> trainersList
) {
}
