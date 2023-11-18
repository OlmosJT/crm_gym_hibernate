package com.epam.crmgymhibernate.service;

import com.epam.crmgymhibernate.dto.request.ActivateDeActivateUserRequest;
import com.epam.crmgymhibernate.dto.request.RegisterTraineeRequest;
import com.epam.crmgymhibernate.dto.request.UpdateTraineeProfileRequest;
import com.epam.crmgymhibernate.dto.response.RegisterResponse;
import com.epam.crmgymhibernate.dto.response.TrainerListResponse;
import com.epam.crmgymhibernate.dto.universal.TraineeProfileDto;

import java.util.List;

public interface TraineeService {
    TraineeProfileDto getTraineeProfile(String username);
    TraineeProfileDto updateTraineeProfile(UpdateTraineeProfileRequest request);

    List<TrainerListResponse> getActiveTrainersNotAssignedToTrainee(String traineeUsername);

    void deleteTraineeProfile(String username);
}
