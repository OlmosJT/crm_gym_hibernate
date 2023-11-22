package com.epam.crmgymhibernate.dto.request;

import com.epam.crmgymhibernate.dto.universal.TrainingTypeDto;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record GetTrainingsOfTraineeRequest(
        @NotEmpty @NotNull
        String username,
        @NotEmpty @NotNull
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy HH:mm:ss")
        LocalDateTime periodFrom,
        @NotEmpty @NotNull
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy HH:mm:ss")
        LocalDateTime periodTo,
        @NotEmpty @NotNull
        String trainerUsername,
        @NotEmpty @NotNull
        TrainingTypeDto trainingType
) {
}


