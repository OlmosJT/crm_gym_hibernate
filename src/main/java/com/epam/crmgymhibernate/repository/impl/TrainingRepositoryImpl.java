package com.epam.crmgymhibernate.repository.impl;

import com.epam.crmgymhibernate.model.Trainee;
import com.epam.crmgymhibernate.model.Trainer;
import com.epam.crmgymhibernate.model.Training;
import com.epam.crmgymhibernate.model.TrainingType;
import com.epam.crmgymhibernate.repository.AbstractGenericRepository;
import com.epam.crmgymhibernate.repository.TrainingRepository;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TrainingRepositoryImpl extends AbstractGenericRepository<Training> implements TrainingRepository {


    public TrainingRepositoryImpl() {
        setClazz(Training.class);
    }

    @Override
    public List<Trainee> findTraineesAssignedOnTrainer(String trainerUsername) {
        TypedQuery<Trainee> query = em.createQuery("SELECT t.trainee FROM Training t " +
                "JOIN t.trainer tr JOIN tr.user u WHERE u.username = :username", Trainee.class);
        query.setParameter("username", trainerUsername);
        return query.getResultList();
    }

    @Override
    public List<Trainer> findTrainersAssignedOnTrainee(String traineeUsername) {
        TypedQuery<Trainer> query = em.createQuery("SELECT DISTINCT t.trainer FROM Training t " +
                "JOIN t.trainee tr JOIN tr.user u WHERE u.username = :username", Trainer.class);
        query.setParameter("username", traineeUsername);
        return query.getResultList();
    }

    @Override
    public List<Trainer> findActiveTrainersNotAssignedOnTrainee(String traineeUsername) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Trainer> cq = cb.createQuery(Trainer.class);

        Subquery<Long> assignedTrainersSubquery = cq.subquery(Long.class);
        Root<Training> assignedTrainersRoot = assignedTrainersSubquery.from(Training.class);
        Join<Training, Trainer> assignedTrainersJoin = assignedTrainersRoot.join("trainer", JoinType.INNER);
        Join<Training, Trainee> traineeJoin = assignedTrainersRoot.join("trainee", JoinType.INNER);
        assignedTrainersSubquery.select(assignedTrainersJoin.get("id"))
                .where(cb.equal(traineeJoin.get("user").get("username"), traineeUsername));

        Root<Trainer> trainerRoot = cq.from(Trainer.class);
        cq.select(trainerRoot)
                .where(
                        cb.isTrue(trainerRoot.get("user").get("isActive")),
                        cb.not(trainerRoot.get("id").in(assignedTrainersSubquery))
                );

        TypedQuery<Trainer> query = em.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public List<Training> findTrainingsOfTrainee(String traineeUsername,
                                                 LocalDateTime periodFrom,
                                                 LocalDateTime periodTo,
                                                 String trainerUsername,
                                                 TrainingType trainingType) {
        if(traineeUsername == null) return Collections.emptyList();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Training> cq = cb.createQuery(Training.class);
        Root<Training> root = cq.from(Training.class);

        List<Predicate> predicates = new ArrayList<>();
        final Predicate predicateUsername = cb.equal(root.get("trainee").get("user").get("username"), traineeUsername);
        predicates.add(predicateUsername);

        if(trainingType != null) {
            final Predicate predicateTrainingType = cb.equal(root.get("trainingType"), trainingType);
            predicates.add(predicateTrainingType);
        }

        if(trainerUsername != null) {
            final Predicate predicateTrainerUsername = cb.equal(root.get("trainer").get("user").get("username"), trainerUsername);
            predicates.add(predicateTrainerUsername);
        }

        if(periodFrom != null) {
            Predicate predicatePeriodFrom = cb.greaterThan(root.get("trainingDate"), periodFrom);
            predicates.add(predicatePeriodFrom);
        }

        if(periodTo != null) {
            final Predicate predicatePeriodTo = cb.lessThan(root.get("trainingDate"), periodTo);
            predicates.add(predicatePeriodTo);
        }

        cq.select(root).where(predicates.toArray(Predicate[]::new));

        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<Training> findTrainingsOfTrainer(String trainerUsername,
                                                 LocalDateTime periodFrom,
                                                 LocalDateTime periodTo,
                                                 String traineeUsername) {

        if (trainerUsername == null) return Collections.emptyList();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Training> cq = cb.createQuery(Training.class);
        Root<Training> root = cq.from(Training.class);

        List<Predicate> predicates = new ArrayList<>();

        final Predicate predicateUsername = cb.equal(root.get("trainer").get("user").get("username"), trainerUsername);
        predicates.add(predicateUsername);

        if(traineeUsername != null) {
            final Predicate predicateTraineeUsername = cb.equal(root.get("trainee").get("user").get("username"), traineeUsername);
            predicates.add(predicateTraineeUsername);
        }
        if(periodFrom != null) {
            final Predicate predicatePeriodFrom = cb.greaterThan(root.get("trainingDate"), periodFrom);
            predicates.add(predicatePeriodFrom);
        }
        if(periodTo != null) {
            final Predicate predicatePeriodTo = cb.lessThan(root.get("trainingDate"), periodTo);
            predicates.add(predicatePeriodTo);
        }

        cq.select(root).where(predicates.toArray(Predicate[]::new));

        return em.createQuery(cq).getResultList();
    }

}
