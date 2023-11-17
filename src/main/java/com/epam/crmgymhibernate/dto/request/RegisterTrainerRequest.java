package com.epam.crmgymhibernate.dto.request;

import com.epam.crmgymhibernate.dto.universal.TrainingTypeDto;

import java.util.List;

public record RegisterTrainerRequest(
        String firstname,
        String lastname,
        List<TrainingTypeDto> specializations
) {

}
