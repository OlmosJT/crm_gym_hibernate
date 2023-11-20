package com.epam.crmgymhibernate.service.impl;

import com.epam.crmgymhibernate.dto.request.UpdateTraineeProfileRequest;
import com.epam.crmgymhibernate.dto.universal.TraineeProfileDto;
import com.epam.crmgymhibernate.model.Trainee;
import com.epam.crmgymhibernate.model.UserEntity;
import com.epam.crmgymhibernate.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TraineeServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private TraineeRepository traineeRepository;
    @Mock
    private TrainingRepository trainingRepository;

    @InjectMocks
    private TraineeServiceImpl traineeService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getTraineeProfile_success() {
        String traineeUsername = "john.doe";
        UserEntity userEntity = new UserEntity(
                1L,
                "John",
                "Doe",
                "john.doe",
                "123 Main St",
                "password1",
                true
        );
        Trainee mockTrainee = new Trainee();
        mockTrainee.setId(1L);
        mockTrainee.setDateOfBirth(LocalDate.parse("1990-01-01"));
        mockTrainee.setUser(userEntity);
        mockTrainee.setTrainings(emptyList());

        when(traineeRepository.findTraineeByUsername(traineeUsername))
                .thenReturn(Optional.of(mockTrainee));
        when(trainingRepository.findTrainersAssignedOnTrainee(traineeUsername)).thenReturn(emptyList());


        TraineeProfileDto result = traineeService.getTraineeProfile(traineeUsername);

        verify(traineeRepository, times(1)).findTraineeByUsername(traineeUsername);
        verify(trainingRepository, times(1)).findTrainersAssignedOnTrainee(traineeUsername);

        assertEquals(mockTrainee.getDateOfBirth(), result.dateOfBirth());
    }

    @Test
    void getTraineeProfile_fail() {
        String traineeUsername = "unknown.user404";

        when(traineeRepository.findTraineeByUsername(traineeUsername))
                .thenReturn(Optional.ofNullable(null));

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            traineeService.getTraineeProfile(traineeUsername);
        });

        verify(traineeRepository, times(1)).findTraineeByUsername(traineeUsername);
        verify(trainingRepository, times(0)).findTrainersAssignedOnTrainee(traineeUsername);

    }

    @Test
    void updateTraineeProfile_success() {
        UpdateTraineeProfileRequest request = new UpdateTraineeProfileRequest(
                "john.doe",
                "John",
                "Doe",
                LocalDate.parse("1990-01-01"),
                "123 Main St",
                true
        );

        UserEntity userEntity = new UserEntity(
                1L,
                "John1",
                "Doe1",
                "john.doe",
                "123 Main St",
                "password1Updated",
                true
        );
        Trainee mockTrainee = new Trainee();
        mockTrainee.setId(1L);
        mockTrainee.setDateOfBirth(LocalDate.parse("1990-01-01"));
        mockTrainee.setUser(userEntity);
        mockTrainee.setTrainings(emptyList());


        when(traineeRepository.findTraineeByUsername(request.username()))
                .thenReturn(Optional.of(mockTrainee));
        when(userRepository.existByUsername(request.username()))
                .thenReturn(true);
        when(traineeRepository.updateTrainee(any(Trainee.class)))
                .thenReturn(mockTrainee);
        when(trainingRepository.findTrainersAssignedOnTrainee(request.username())).thenReturn(emptyList());



        TraineeProfileDto result = traineeService.updateTraineeProfile(request);

        // Verify interactions with repositories
        verify(traineeRepository, times(1)).findTraineeByUsername(request.username());
        verify(traineeRepository, times(1)).updateTrainee(any(Trainee.class));
        verify(trainingRepository, times(1)).findTrainersAssignedOnTrainee(request.username());


        assertEquals(userEntity.getFirstName(), result.firstname());
    }

    @Test
    void updateTraineeProfile_fail_EntityNotFoundException() {
        UpdateTraineeProfileRequest request = new UpdateTraineeProfileRequest(
                "john.doe",
                "John",
                "Doe",
                LocalDate.parse("1990-01-01"),
                "123 Main St",
                true
        );

        when(traineeRepository.findTraineeByUsername(request.username())).thenReturn(Optional.empty());


        assertThrows(EntityNotFoundException.class, () -> traineeService.updateTraineeProfile(request));

        verify(traineeRepository, times(1)).findTraineeByUsername(anyString());
        verifyNoMoreInteractions(userRepository, traineeRepository, trainingRepository);

    }
}