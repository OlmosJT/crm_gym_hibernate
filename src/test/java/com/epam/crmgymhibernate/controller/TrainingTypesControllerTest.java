package com.epam.crmgymhibernate.controller;

import com.epam.crmgymhibernate.conf.ApplicationConf;
import com.epam.crmgymhibernate.conf.HibernateConf;
import com.epam.crmgymhibernate.conf.SpringWebInitializer;
import com.epam.crmgymhibernate.repository.TrainingTypeRepository;
import com.epam.crmgymhibernate.service.TrainingTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringWebInitializer.class})
@WebAppConfiguration
class TrainingTypesControllerTest {

    private TrainingTypesController trainingTypesController;
    private final TrainingTypeService trainingTypeService = Mockito.mock(TrainingTypeService.class);
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        trainingTypesController = new TrainingTypesController(trainingTypeService);
        mockMvc = MockMvcBuilders.standaloneSetup(trainingTypesController).build();
    }

    @Test
    void getTrainingTypes() throws Exception {
        when(trainingTypeService.getAllTrainingTypes()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/training-types")).andExpect(MockMvcResultMatchers.status().isOk());
        verify(trainingTypeService, times(1)).getAllTrainingTypes();
    }
}