package com.epam.crmgymhibernate.dto.request;

import com.epam.crmgymhibernate.dto.universal.TrainingTypeDto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public record RegisterTrainerRequest(
        @NotEmpty @NotNull
        String firstname,
        @NotEmpty @NotNull
        String lastname,
        @NotEmpty @NotNull
        List<TrainingTypeDto> specializations
) {

}
