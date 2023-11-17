package com.epam.crmgymhibernate.repository.impl;

import com.epam.crmgymhibernate.model.Trainee;
import com.epam.crmgymhibernate.repository.AbstractGenericRepository;
import com.epam.crmgymhibernate.repository.TraineeRepository;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.Root;

import java.util.Optional;

public class TraineeRepositoryImpl extends AbstractGenericRepository<Trainee> implements TraineeRepository {
    public TraineeRepositoryImpl() {
        setClazz(Trainee.class);
    }


    @Override
    public Optional<Trainee> findTraineeByUsername(String username) {
        String jpql = "SELECT t from Trainee t WHERE t.user.username=:username";
        TypedQuery<Trainee> query = em.createQuery(jpql, Trainee.class);
        query.setParameter("username", username);

        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException exception) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteTraineeByUsername(String username) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaDelete<Trainee> criteriaDelete = criteriaBuilder.createCriteriaDelete(Trainee.class);
        Root<Trainee> root = criteriaDelete.from(Trainee.class);
        criteriaDelete.where(criteriaBuilder.equal(root.get("user").get("username"), username));
        int rowsDeleted = em.createQuery(criteriaDelete).executeUpdate();
        System.out.println("rows affected (deleted): " + rowsDeleted);
    }

    @Override
    public Trainee updateTrainee(Trainee entity) {
        return em.merge(entity);
    }
}
