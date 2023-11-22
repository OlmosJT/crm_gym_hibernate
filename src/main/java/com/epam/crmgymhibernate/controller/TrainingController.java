package com.epam.crmgymhibernate.controller;


import com.epam.crmgymhibernate.dto.request.AddTrainingRequest;
import com.epam.crmgymhibernate.service.TrainingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/trainings")
@AllArgsConstructor
public class TrainingController {

    private final TrainingService trainingService;

    @PostMapping
    public void addTraining(@Valid @RequestBody AddTrainingRequest request) {
        trainingService.addTraining(request);
    }

}
