package com.epam.crmgymhibernate.service.impl;

import com.epam.crmgymhibernate.model.TrainingType;
import com.epam.crmgymhibernate.repository.TrainingTypeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrainingTypeServiceImplTest {

    @Mock
    private TrainingTypeRepository trainingTypeRepository;

    @InjectMocks
    private TrainingTypeServiceImpl trainingTypeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllTrainingTypes() {
        List<TrainingType> trainingTypeList = List.of(
                new TrainingType(1L, "Boxing"),
                new TrainingType(2L, "Karate"),
                new TrainingType(3L, "Bodybuilding")
        );

        Mockito.doReturn(trainingTypeList).when(trainingTypeRepository).getAll();

        var result = trainingTypeService.getAllTrainingTypes();

        Assertions.assertEquals(trainingTypeList.size(), result.size());
    }
}