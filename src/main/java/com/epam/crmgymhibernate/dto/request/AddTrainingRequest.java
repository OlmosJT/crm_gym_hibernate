package com.epam.crmgymhibernate.dto.request;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record AddTrainingRequest(
        String traineeUsername,
        String trainerUsername,
        String trainingName,
        LocalDateTime trainingDate,
        Duration duration

) {
}
