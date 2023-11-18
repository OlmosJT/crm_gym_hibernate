package com.epam.crmgymhibernate.repository.impl;

import com.epam.crmgymhibernate.model.UserEntity;
import com.epam.crmgymhibernate.repository.AbstractGenericRepository;
import com.epam.crmgymhibernate.repository.UserRepository;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;


public class UserRepositoryImpl extends AbstractGenericRepository<UserEntity> implements UserRepository {

    public UserRepositoryImpl() {
        setClazz(UserEntity.class);
    }

    @Override
    public boolean existByUsername(String username) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<UserEntity> root = criteriaQuery.from(UserEntity.class);
        final Predicate predicate = cb.equal(root.get("username"), username);
        criteriaQuery.select(cb.count(root)).where(predicate);

        try {
            var count =  em.createQuery(criteriaQuery).getSingleResult();
            return count > 0;
        } catch (NoResultException | NonUniqueResultException exception) {
            return false;
        }
    }

    @Override
    public void delete(UserEntity entity) {
        entity.setActive(false);
        em.merge(entity);
    }
}
