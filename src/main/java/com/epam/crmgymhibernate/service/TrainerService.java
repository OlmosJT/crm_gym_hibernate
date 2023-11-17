package com.epam.crmgymhibernate.service;

import com.epam.crmgymhibernate.dto.request.UpdateTrainerProfileRequest;
import com.epam.crmgymhibernate.dto.universal.TrainerProfileDto;

public interface TrainerService {
    TrainerProfileDto getTrainerProfile(String username);
    TrainerProfileDto updateTrainerProfile(UpdateTrainerProfileRequest request);

    void deleteTrainerProfile(String username);
}
