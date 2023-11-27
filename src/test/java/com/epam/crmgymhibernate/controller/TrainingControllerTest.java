package com.epam.crmgymhibernate.controller;

import com.epam.crmgymhibernate.conf.SpringWebInitializer;
import com.epam.crmgymhibernate.dto.request.AddTrainingRequest;
import com.epam.crmgymhibernate.service.TrainingService;
import com.epam.crmgymhibernate.service.TrainingTypeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.hypersistence.utils.hibernate.type.json.internal.JacksonUtil;
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

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringWebInitializer.class})
@WebAppConfiguration
class TrainingControllerTest {

    private TrainingController trainingController;
    private TrainingService trainingService;
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        trainingService = Mockito.mock(TrainingService.class);
        trainingController = new TrainingController(trainingService);
        mockMvc = MockMvcBuilders.standaloneSetup(trainingController).build();
    }

    @Test
    void addTraining() throws Exception {
        String request = "{\n" +
                "  \"traineeUsername\": \"John.Doe\",\n" +
                "  \"trainerUsername\": \"sofia.wilson\",\n" +
                "  \"trainingName\": \"weight loss\",\n" +
                "  \"trainingDate\": \"11/24/2023 16:20:00\",\n" +
                "  \"duration\": \"PT2H\"\n" +
                "}";

        Mockito.doNothing().when(trainingService).addTraining(any(AddTrainingRequest.class));

        mockMvc.perform(post("/trainings").contentType(MediaType.APPLICATION_JSON)
                        .content(request)).andExpect(status().isOk());
    }

    private static String asJsonString(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }
}