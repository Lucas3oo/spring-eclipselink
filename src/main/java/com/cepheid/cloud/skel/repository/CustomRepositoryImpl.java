package com.cepheid.cloud.skel.repository;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

public class CustomRepositoryImpl<T, ID extends Serializable>
extends SimpleJpaRepository<T, ID> implements CustomRepository<T, ID> {

    private final EntityManager entityManager;

    public CustomRepositoryImpl(JpaEntityInformation entityInformation, 
                                EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void setProperty(String propertyName, Object value) {
      entityManager.setProperty(propertyName, value);
    }

    @Override
    @Transactional
    public Map<String, Object> getProperties() {
      return entityManager.getProperties();
    }

}