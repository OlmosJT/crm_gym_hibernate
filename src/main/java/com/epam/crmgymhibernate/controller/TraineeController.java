package com.epam.crmgymhibernate.controller;

import com.epam.crmgymhibernate.dto.request.ActivateDeActivateUserRequest;
import com.epam.crmgymhibernate.dto.request.GetTrainingsOfTraineeRequest;
import com.epam.crmgymhibernate.dto.request.RegisterTraineeRequest;
import com.epam.crmgymhibernate.dto.request.UpdateTraineeProfileRequest;
import com.epam.crmgymhibernate.dto.response.RegisterResponse;
import com.epam.crmgymhibernate.dto.response.TrainerListResponse;
import com.epam.crmgymhibernate.dto.response.TrainingResponse;
import com.epam.crmgymhibernate.dto.universal.TraineeProfileDto;
import com.epam.crmgymhibernate.dto.universal.TrainingTypeDto;
import com.epam.crmgymhibernate.service.AuthService;
import com.epam.crmgymhibernate.service.TraineeService;
import com.epam.crmgymhibernate.service.TrainingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/trainees")
@AllArgsConstructor
public class TraineeController {
    private final TraineeService traineeService;
    private final AuthService authService;
    private final TrainingService trainingService;

    @PostMapping
    public ResponseEntity<RegisterResponse> registerTrainee(@Valid @RequestBody RegisterTraineeRequest request) {
        var body = authService.registerTrainee(request);
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<TraineeProfileDto> getTraineeProfile(@PathVariable(name = "username") String username) {
        return ResponseEntity.ok(traineeService.getTraineeProfile(username));
    }

    @PutMapping("/{username}")
    public ResponseEntity<TraineeProfileDto> updateTraineeProfile(@Valid @RequestBody UpdateTraineeProfileRequest request) {
        var body = traineeService.updateTraineeProfile(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteTraineeProfile(@PathVariable(name = "username") String username) {
        traineeService.deleteTraineeProfile(username);
        return ResponseEntity.ok(username +" removed successfully.");
    }

    @PatchMapping("/{username}")
    public void activateDeactivateTraineeProfile(
            @PathVariable(name = "username") String username,
            @RequestParam(value = "isActive") boolean isActive
    ) {
        authService.activateDeActivateUser(new ActivateDeActivateUserRequest(username, isActive));
    }

    @GetMapping("/{username}/trainings")
    public ResponseEntity<List<TrainingResponse>> getTrainingsOfTrainee(
            @PathVariable String username,
            @RequestParam(value = "periodFrom", required = false) String periodFrom,
            @RequestParam(value = "periodTo", required = false, defaultValue = "") String periodTo,
            @RequestParam(value = "trainerUsername", required = false, defaultValue = "") String trainerUsername,
            @RequestParam(value = "trainingType", required = false, defaultValue = "") TrainingTypeDto trainingType
    ) {
        return ResponseEntity.ok(trainingService.getTrainingsOfTrainee(new GetTrainingsOfTraineeRequest(
                username,
                (!periodFrom.isEmpty()) ? LocalDateTime.parse(periodFrom) : null,
                (!periodTo.isEmpty()) ? LocalDateTime.parse(periodFrom) : null,
                (!trainerUsername.isEmpty()) ? trainerUsername : null,
                trainingType
        )));
    }

    @GetMapping("/{username}/available-trainers")
    public ResponseEntity<List<TrainerListResponse>> getActiveTrainersNotAssignedOnTrainee(
            @PathVariable(name = "username") String username
    ) {
        return ResponseEntity.ok(traineeService.getActiveTrainersNotAssignedToTrainee(username));
    }

}
