package com.epam.crmgymhibernate.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDateTime;

public record AddTrainingRequest(
        @NotEmpty @NotNull
        String traineeUsername,
        @NotEmpty @NotNull
        String trainerUsername,
        @NotEmpty @NotNull
        String trainingName,
        @NotEmpty @NotNull
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy HH:mm:ss")
        LocalDateTime trainingDate,
        @NotEmpty @NotNull
        Duration duration

) {
}
