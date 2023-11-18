package com.epam.crmgymhibernate.dto.request;

import java.time.LocalDateTime;

public record GetTrainingsOfTrainerRequest(
        String username,
        LocalDateTime periodFrom,
        LocalDateTime periodTo,
        String traineeUsername
) {
}
