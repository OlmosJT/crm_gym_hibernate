package com.epam.crmgymhibernate.controller;

import com.epam.crmgymhibernate.conf.SpringWebInitializer;
import com.epam.crmgymhibernate.dto.request.ActivateDeActivateUserRequest;
import com.epam.crmgymhibernate.dto.request.UpdateTraineeProfileRequest;
import com.epam.crmgymhibernate.dto.response.RegisterResponse;
import com.epam.crmgymhibernate.dto.universal.TraineeProfileDto;
import com.epam.crmgymhibernate.model.Trainee;
import com.epam.crmgymhibernate.service.AuthService;
import com.epam.crmgymhibernate.service.TraineeService;
import com.epam.crmgymhibernate.service.TrainingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringWebInitializer.class})
@WebAppConfiguration
class TraineeControllerTest {

    private TraineeController traineeController;
    private AuthService authService;
    private TraineeService traineeService;
    private TrainingService trainingService;
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        traineeService = Mockito.mock(TraineeService.class);
        authService = Mockito.mock(AuthService.class);
        trainingService = Mockito.mock(TrainingService.class);
        traineeController = new TraineeController(traineeService, authService, trainingService);
        mockMvc = MockMvcBuilders.standaloneSetup(traineeController).build();
    }

    @Test
    void getTraineeProfile() throws Exception {
        when(traineeService.getTraineeProfile(anyString())).thenReturn(any(TraineeProfileDto.class));

        mockMvc.perform(get("/trainees/john.doe"))
                .andExpect(status().isOk());
    }

    @Test
    void updateTraineeProfile() throws Exception {
        TraineeProfileDto dto = new TraineeProfileDto(
                "john.doe",
                "John",
                "Doe",
                LocalDate.of(1990, 1, 15),
                "123 Main Street",
                true,
                Collections.emptyList()
        );

        when(traineeService.updateTraineeProfile(any(UpdateTraineeProfileRequest.class)))
                .thenReturn(dto);

        var request = "{\n" +
                "  \"username\": \"john.doe\",\n" +
                "  \"firstname\": \"John\",\n" +
                "  \"lastname\": \"Doe\",\n" +
                "  \"dateOfBirth\": \"1990-01-15\",\n" +
                "  \"address\": \"123 Main Street\"\n" +
                "}";

        mockMvc.perform(put("/trainees/john.doe").contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());

    }

    @Test
    void deleteTraineeProfile() throws Exception {
        doNothing().when(traineeService).deleteTraineeProfile(anyString());

        mockMvc.perform(delete("/trainees/username12"))
                .andExpect(status().isOk());

    }

}