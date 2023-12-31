package com.epam.crmgymhibernate.repository;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public abstract class AbstractGenericRepository<T> implements GenericRepository<T> {
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    protected EntityManager em;
    private Class<T> clazz;

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T getById(long id) {
        T entity;
        try {
            entity = em.createQuery(
                    "SELECT e FROM " + clazz.getName() + " e WHERE e.id = :id",
                    clazz).setParameter("id", id).getSingleResult();
        } catch (NoResultException | NonUniqueResultException exception) {
            entity = null;
        }

        return entity;
    }

    @Override
    public List<T> getAll() {
        return em.createQuery("from " +
                clazz.getName(), clazz).getResultList();
    }

    @Override
    public void insert(T entity) {
        em.persist(entity);
    }
    @Override
    public void delete(T entity) {
        em.remove(entity);
    }
    @Override
    public void update(long id, String propertyName, Object propertyValue) {
        em.createQuery("UPDATE " + clazz.getName() + " e SET e." + propertyName + " = :propertyValue WHERE e.id = :id")
                .setParameter("propertyValue", propertyValue)
                .setParameter("id", id).executeUpdate();
    }

    @Override
    public List<T> findByProperty(String propertyName, Object propertyValue) {
        return em.createQuery("SELECT e FROM " + clazz.getName() + " e WHERE e." +
                                propertyName + " = :propertyValue", clazz)
                .setParameter("propertyValue", propertyValue)
                .getResultList();
    }
}
