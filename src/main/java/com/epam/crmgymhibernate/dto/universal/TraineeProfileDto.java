package com.epam.crmgymhibernate.dto.universal;

import com.epam.crmgymhibernate.dto.response.TrainersListResponse;

import java.time.LocalDate;
import java.util.List;

public record TraineeProfileDto(
        String username,
        String firstname,
        String lastname,
        LocalDate dateOfBirth,
        String address,
        boolean isActive,
        List<TrainersListResponse> trainersList
) {
}
