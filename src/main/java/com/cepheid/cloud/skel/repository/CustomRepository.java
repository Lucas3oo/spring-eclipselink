package com.cepheid.cloud.skel.repository;

import java.io.Serializable;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CustomRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
  void setProperty(String propertyName, Object value);

  Map<String, Object> getProperties();
}