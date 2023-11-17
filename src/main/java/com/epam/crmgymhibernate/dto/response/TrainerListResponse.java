package com.epam.crmgymhibernate.dto.response;



import com.epam.crmgymhibernate.dto.universal.TrainingTypeDto;

import java.util.List;

public record TrainerListResponse(
      String username,
      String firstname,
      String lastname,
      List<TrainingTypeDto> specializations
) {
}
