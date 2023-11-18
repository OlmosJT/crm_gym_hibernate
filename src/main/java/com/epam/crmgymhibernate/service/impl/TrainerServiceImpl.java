package com.epam.crmgymhibernate.service.impl;

import com.epam.crmgymhibernate.dto.request.UpdateTrainerProfileRequest;
import com.epam.crmgymhibernate.dto.response.TraineeListResponse;
import com.epam.crmgymhibernate.dto.universal.TrainerProfileDto;
import com.epam.crmgymhibernate.dto.universal.TrainingTypeDto;
import com.epam.crmgymhibernate.model.Trainee;
import com.epam.crmgymhibernate.model.Trainer;
import com.epam.crmgymhibernate.model.TrainingType;
import com.epam.crmgymhibernate.repository.TrainerRepository;
import com.epam.crmgymhibernate.repository.TrainingRepository;
import com.epam.crmgymhibernate.repository.UserRepository;
import com.epam.crmgymhibernate.service.TrainerService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class TrainerServiceImpl implements TrainerService {

    private final UserRepository userRepository;
    private final TrainerRepository trainerRepository;
    private final TrainingRepository trainingRepository;

    public TrainerServiceImpl(UserRepository userRepository, TrainerRepository trainerRepository, TrainingRepository trainingRepository) {
        this.userRepository = userRepository;
        this.trainerRepository = trainerRepository;
        this.trainingRepository = trainingRepository;
    }

    @Override
    public TrainerProfileDto getTrainerProfile(String username) {
        Trainer entity = trainerRepository.findTrainerByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Trainer not found with username: " + username));

        return getTrainerProfileDto(entity, trainingRepository.findTraineesAssignedOnTrainer(username));
    }

    private TrainerProfileDto getTrainerProfileDto(Trainer entity, List<Trainee> traineesAssignedOnTrainer) {
        return new TrainerProfileDto(
                entity.getUser().getUsername(),
                entity.getUser().getFirstName(),
                entity.getUser().getLastName(),
                entity.getSpecializations().stream().map(t -> new TrainingTypeDto(t.getId(), t.getName())).toList(),
                entity.getUser().isActive(),
                traineesAssignedOnTrainer.stream().map(trainee -> new TraineeListResponse(
                        trainee.getUser().getUsername(),
                        trainee.getUser().getFirstName(),
                        trainee.getUser().getLastName()
                )).toList()
        );
    }

    @Override
    public TrainerProfileDto updateTrainerProfile(UpdateTrainerProfileRequest request) throws EntityNotFoundException {
        Trainer trainerEntity = trainerRepository.findTrainerByUsername(request.username()).orElseThrow(() ->
                new EntityNotFoundException("Trainer not found with username: " + request.username()));

        trainerEntity.getUser().setFirstName(request.firstname());
        trainerEntity.getUser().setLastName(request.lastname());
        trainerEntity.getUser().setActive(request.isActive());

        // FIXME: Consider situation request has un-exist TrainingType ID
        trainerEntity.setSpecializations(request.specializations()
                .stream().map(t -> new TrainingType(t.id(), t.name())).toList()
        );

        if (!Objects.equals(trainerEntity.getUser().getUsername(), request.username())) {
            if (userRepository.existByUsername(request.username())) {
                trainerEntity.getUser().setUsername(request.username());
            }
        }

        trainerEntity = trainerRepository.updateTrainer(trainerEntity);

        return getTrainerProfileDto(trainerEntity, trainingRepository.findTraineesAssignedOnTrainer(trainerEntity.getUser().getUsername()));
    }

    @Override
    public void deleteTrainerProfile(String username) {
        trainerRepository.deleteTrainerByUsername(username);
    }
}
