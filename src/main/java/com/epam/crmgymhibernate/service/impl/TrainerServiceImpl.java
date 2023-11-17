package com.epam.crmgymhibernate.service.impl;

import com.epam.crmgymhibernate.dto.request.UpdateTrainerProfileRequest;
import com.epam.crmgymhibernate.dto.universal.TrainerProfileDto;
import com.epam.crmgymhibernate.repository.TrainerRepository;
import com.epam.crmgymhibernate.repository.UserRepository;
import com.epam.crmgymhibernate.service.TrainerService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TrainerServiceImpl implements TrainerService {

    private final UserRepository userRepository;
    private final TrainerRepository trainerRepository;

    public TrainerServiceImpl(UserRepository userRepository, TrainerRepository trainerRepository) {
        this.userRepository = userRepository;
        this.trainerRepository = trainerRepository;
    }

    @Override
    public TrainerProfileDto getTrainerProfile(String username) {
        return null;
    }

    @Override
    public TrainerProfileDto updateTrainerProfile(UpdateTrainerProfileRequest request) {
        return null;
    }

    @Override
    public void deleteTrainerProfile(String username) {

    }
}
