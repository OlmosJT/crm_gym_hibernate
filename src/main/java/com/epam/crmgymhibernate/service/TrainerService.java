package com.epam.crmgymhibernate.service;

import com.epam.crmgymhibernate.dto.universal.TrainerProfileDto;

public interface TrainerService {
    TrainerProfileDto getTrainerProfile(String username);

    void deleteTrainerProfile(String username);
}
