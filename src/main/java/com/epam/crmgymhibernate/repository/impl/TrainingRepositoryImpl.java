package com.epam.crmgymhibernate.repository.impl;

import com.epam.crmgymhibernate.model.Trainee;
import com.epam.crmgymhibernate.model.Trainer;
import com.epam.crmgymhibernate.model.Training;
import com.epam.crmgymhibernate.model.TrainingType;
import com.epam.crmgymhibernate.repository.AbstractGenericRepository;
import com.epam.crmgymhibernate.repository.TrainingRepository;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.time.LocalDateTime;
import java.util.List;

public class TrainingRepositoryImpl extends AbstractGenericRepository<Training> implements TrainingRepository {


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
        TypedQuery<Trainer> query = em.createQuery("SELECT t FROM Trainer t " +
                "LEFT JOIN t.trainings tr " +
                "WHERE t.user.isActive = true AND tr.trainee IS NULL AND tr.trainee.user.username = :username", Trainer.class);
        query.setParameter("username", traineeUsername);
        return query.getResultList();
    }

    @Override
    public List<Training> findTrainingsOfTrainee(String traineeUsername,
                                                 LocalDateTime periodFrom,
                                                 LocalDateTime periodTo,
                                                 String trainerUsername,
                                                 TrainingType trainingType) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Training> cq = cb.createQuery(Training.class);
        Root<Training> root = cq.from(Training.class);
        final Predicate predicateUsername = cb.equal(root.get("trainee"), traineeUsername);
        cq.select(root).where(predicateUsername);

        if(trainingType != null) {
            cq.select(root).where(cb.equal(root.get("trainingType"), trainingType));
        }
        if(trainerUsername != null) {
            cq.select(root).where(cb.equal(root.get("trainer").get("user").get("username"), trainerUsername));
        }
        if(periodFrom != null) {
            final Predicate predicatePeriodFrom = cb.greaterThan(root.get("trainingDate"), periodFrom);
            cq.select(root).where(predicatePeriodFrom);
        }
        if(periodTo != null) {
            final Predicate predicatePeriodTo = cb.lessThan(root.get("trainingDate"), periodFrom);
            cq.select(root).where(predicatePeriodTo);
        }

        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<Training> findTrainingsOfTrainer(String trainerUsername, LocalDateTime periodFrom, LocalDateTime periodTo, String traineeUsername) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Training> cq = cb.createQuery(Training.class);
        Root<Training> root = cq.from(Training.class);
        final Predicate predicateUsername = cb.equal(root.get("trainer"), trainerUsername);
        cq.select(root).where(predicateUsername);

        if(traineeUsername != null) {
            cq.select(root).where(cb.equal(root.get("trainee").get("user").get("username"), traineeUsername));
        }
        if(periodFrom != null) {
            final Predicate predicatePeriodFrom = cb.greaterThan(root.get("trainingDate"), periodFrom);
            cq.select(root).where(predicatePeriodFrom);
        }
        if(periodTo != null) {
            final Predicate predicatePeriodTo = cb.lessThan(root.get("trainingDate"), periodFrom);
            cq.select(root).where(predicatePeriodTo);
        }

        return em.createQuery(cq).getResultList();
    }

}
