package com.epam.crmgymhibernate.service;

import com.epam.crmgymhibernate.dto.request.RegisterTraineeRequest;
import com.epam.crmgymhibernate.dto.request.UpdateTraineeProfileRequest;
import com.epam.crmgymhibernate.dto.response.RegisterResponse;
import com.epam.crmgymhibernate.dto.universal.TraineeProfileDto;

public interface TraineeService {
    TraineeProfileDto getTraineeProfile(String username);
    TraineeProfileDto updateTraineeProfile(UpdateTraineeProfileRequest request);

    void deleteTraineeProfile(String username);
}
