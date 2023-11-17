package com.epam.crmgymhibernate.repository.impl;

import com.epam.crmgymhibernate.dto.response.TrainerListResponse;
import com.epam.crmgymhibernate.model.Trainee;
import com.epam.crmgymhibernate.model.Trainer;
import com.epam.crmgymhibernate.repository.AbstractGenericRepository;
import com.epam.crmgymhibernate.repository.TrainerRepository;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Optional;

public class TrainerRepositoryImpl extends AbstractGenericRepository<Trainer> implements TrainerRepository {
    public TrainerRepositoryImpl() {
        setClazz(Trainer.class);
    }

    @Override
    public Optional<Trainer> findTrainerByUsername(String username) {
        String jpql = "SELECT t FROM Trainer t WHERE t.user.username=:username";
        TypedQuery<Trainer> query = em.createQuery(jpql, Trainer.class);
        query.setParameter("username", username);
        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException exception) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteTrainerByUsername(String username) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaDelete<Trainer> criteriaDelete = criteriaBuilder.createCriteriaDelete(Trainer.class);
        Root<Trainer> root = criteriaDelete.from(Trainer.class);
        criteriaDelete.where(criteriaBuilder.equal(root.get("user").get("username"), username));
        int rowsDeleted = em.createQuery(criteriaDelete).executeUpdate();
        System.out.println("rows affected (deleted): " + rowsDeleted);
    }

    @Override
    public Trainer updateTrainer(Trainer entity) {
        return em.merge(entity);
    }

    @Override
    public List<Trainer> getActiveNotAssignedTrainers(String username) {
        return null;
    }

    @Override
    public List<Trainer> findActiveTrainersNotAssignedToTrainee() {
        String jpql = "SELECT t FROM Trainer t " +
                "WHERE t.user.isActive = true " +
                "AND t.trainees IS EMPTY";

        Query query = em.createQuery(jpql, Trainer.class);
        return query.getResultList();
    }
}
