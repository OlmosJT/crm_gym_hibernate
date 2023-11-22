package com.epam.crmgymhibernate.controller;

import com.epam.crmgymhibernate.dto.request.*;
import com.epam.crmgymhibernate.dto.response.RegisterResponse;
import com.epam.crmgymhibernate.dto.response.TrainingResponse;
import com.epam.crmgymhibernate.dto.universal.TrainerProfileDto;
import com.epam.crmgymhibernate.service.AuthService;
import com.epam.crmgymhibernate.service.TrainerService;
import com.epam.crmgymhibernate.service.TrainingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/trainers")
@AllArgsConstructor
public class TrainerController {

    private final TrainerService trainerService;
    private final AuthService authService;
    private final TrainingService trainingService;

    @PostMapping
    public ResponseEntity<RegisterResponse> registerTrainer(@Valid @RequestBody RegisterTrainerRequest request) {
        RegisterResponse body = authService.registerTrainer(request);
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<TrainerProfileDto> getTrainerProfile(@PathVariable(name = "username") String username) {
        return ResponseEntity.ok(trainerService.getTrainerProfile(username));
    }

    @PutMapping("/{username}")
    public ResponseEntity<TrainerProfileDto> updateTrainerProfile(@Valid @RequestBody UpdateTrainerProfileRequest request) {
        var body = trainerService.updateTrainerProfile(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteTraineeProfile(@PathVariable(name = "username") String username) {
        trainerService.deleteTrainerProfile(username);
        return ResponseEntity.ok(username + " removed successfully.");
    }

    @PatchMapping("/{username}")
    public void activateDeactivateTrainerProfile(
            @PathVariable(name = "username") String username,
            @RequestParam(value = "isActive") boolean isActive
    ) {
        authService.activateDeActivateUser(new ActivateDeActivateUserRequest(username, isActive));
    }

    @GetMapping("/{username}/trainings")
    public ResponseEntity<List<TrainingResponse>> getTrainingsOfTrainer(
            @PathVariable String username,
            @RequestParam(value = "periodFrom", required = false) String periodFrom,
            @RequestParam(value = "periodTo", required = false) String periodTo,
            @RequestParam(value = "traineeUsername", required = false) String traineeUsername
    ) {
        return ResponseEntity.ok(trainingService.getTrainingsOfTrainer(new GetTrainingsOfTrainerRequest(
                username,
                (!periodFrom.isEmpty()) ? LocalDateTime.parse(periodFrom) : null,
                (!periodTo.isEmpty()) ? LocalDateTime.parse(periodFrom) : null,
                (!traineeUsername.isEmpty()) ? traineeUsername : null
        )));
    }


}
