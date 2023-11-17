package com.epam.crmgymhibernate.service.impl;

import com.epam.crmgymhibernate.dto.request.UpdateTrainerProfileRequest;
import com.epam.crmgymhibernate.dto.response.TraineeListResponse;
import com.epam.crmgymhibernate.dto.response.TrainerListResponse;
import com.epam.crmgymhibernate.dto.universal.TrainerProfileDto;
import com.epam.crmgymhibernate.dto.universal.TrainingTypeDto;
import com.epam.crmgymhibernate.model.Trainer;
import com.epam.crmgymhibernate.model.TrainingType;
import com.epam.crmgymhibernate.repository.TrainerRepository;
import com.epam.crmgymhibernate.repository.UserRepository;
import com.epam.crmgymhibernate.service.TrainerService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class TrainerServiceImpl implements TrainerService {

    private final UserRepository userRepository;
    private final TrainerRepository trainerRepository;

    public TrainerServiceImpl(UserRepository userRepository, TrainerRepository trainerRepository) {
        this.userRepository = userRepository;
        this.trainerRepository = trainerRepository;
    }

    @Override
    public TrainerProfileDto getTrainerProfile(String username) {
        Trainer entity = trainerRepository.findTrainerByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Trainer not found with username: " + username));

        return getTrainerProfileDto(entity);
    }

    private TrainerProfileDto getTrainerProfileDto(Trainer entity) {
        return new TrainerProfileDto(
                entity.getUser().getUsername(),
                entity.getUser().getFirstName(),
                entity.getUser().getLastName(),
                entity.getSpecializations().stream().map(t -> new TrainingTypeDto(t.getId(), t.getName())).toList(),
                entity.getUser().isActive(),
                entity.getTrainees().stream().map(trainee -> new TraineeListResponse(
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
            if (!userRepository.existByUsername(request.username())) {
                trainerEntity.getUser().setUsername(request.username());
            }
        }

        trainerEntity = trainerRepository.updateTrainer(trainerEntity);

        return getTrainerProfileDto(trainerEntity);
    }

    @Override
    public List<TrainerListResponse> getNotAssignedActiveTrainersToTrainee(String username) {
        var entities = trainerRepository.findActiveTrainersNotAssignedToTrainee();
        if (entities == null || entities.isEmpty()) {
            entities = Collections.emptyList();
        }

        return entities.stream().map(trainer -> new TrainerListResponse(
                        trainer.getUser().getUsername(),
                        trainer.getUser().getFirstName(),
                        trainer.getUser().getLastName(),
                        trainer.getSpecializations().stream().map(t -> new TrainingTypeDto(t.getId(), t.getName())).toList()
                )
        ).toList();
    }

    @Override
    public void deleteTrainerProfile(String username) {
        trainerRepository.deleteTrainerByUsername(username);
    }
}
