package com.epam.crmgymhibernate.repository;

import com.epam.crmgymhibernate.model.UserEntity;

public interface UserRepository extends GenericRepository<UserEntity> {
    boolean existByUsername(String username);
}
