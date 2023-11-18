package com.epam.crmgymhibernate.repository.impl;

import com.epam.crmgymhibernate.model.UserEntity;
import com.epam.crmgymhibernate.repository.AbstractGenericRepository;
import com.epam.crmgymhibernate.repository.UserRepository;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;


public class UserRepositoryImpl extends AbstractGenericRepository<UserEntity> implements UserRepository {

    public UserRepositoryImpl() {
        setClazz(UserEntity.class);
    }

    @Override
    public boolean existByUsername(String username) {
        String jpql = "SELECT COUNT(u) FROM UserEntity u WHERE u.username = :username";
        TypedQuery<Long> query = em.createQuery(jpql, Long.class);
        query.setParameter("username", username);

        try {
            Long count = query.getSingleResult();
            return count <= 0;
        } catch (NoResultException e) {
            return true;
        }
    }
}
