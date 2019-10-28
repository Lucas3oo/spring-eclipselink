package com.cepheid.cloud.skel;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.jta.JtaTransactionManager;

@Configuration
public class EclipseLink extends JpaBaseConfiguration {

  protected EclipseLink(DataSource dataSource, JpaProperties properties,
      ObjectProvider<JtaTransactionManager> jtaTransactionManager,
      ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
    super(dataSource, properties, jtaTransactionManager, transactionManagerCustomizers);
  }

  @Bean
  @Primary
  protected EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("TENANT", "");
    map.put("ADMINACCESS", 1);
    return entityManagerFactory.createEntityManager(map);
  }
  
  @Override
  protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
    return new EclipseLinkJpaVendorAdapter();
  }

  @Override
  protected Map<String, Object> getVendorProperties() {
    HashMap<String, Object> map = new HashMap<>();
    // must start with $ java
    // -javaagent:/Users/lpersson/.gradle/caches/modules-2/files-2.1/org.springframework/spring-instrument/5.1.9.RELEASE/f60d26957cbb8bd30d2a19b90ad0b3652a27b2d/spring-instrument-5.1.9.RELEASE.jar
    // You need the org.springframework.instrument library together with aspectjrt.jar and
    // aspectjweaver.jar libraries.
    // https://stackoverflow.com/questions/10769051/eclipselinkjpavendoradapter-instead-of-hibernatejpavendoradapter-issue
    map.put(PersistenceUnitProperties.WEAVING, detectWeavingMode());
    // map.put(PersistenceUnitProperties.DDL_GENERATION, "drop-and-create-tables");
    // map.put(PersistenceUnitProperties.BATCH_WRITING, BatchWriting.JDBC);
    // map.put(PersistenceUnitProperties.LOGGING_LEVEL, "ALL");
    return map;
  }

  private String detectWeavingMode() {
    String weaving = InstrumentationLoadTimeWeaver.isInstrumentationAvailable() ? "true" : "static";
    return weaving;
  }

//  // needed for map.put(PersistenceUnitProperties.WEAVING, "true");
//  @Bean
//  public InstrumentationLoadTimeWeaver loadTimeWeaver() {
//    return new InstrumentationLoadTimeWeaver();
//  }

  // multitenant https://wiki.eclipse.org/EclipseLink/Examples/JPA/Multitenant
  // https://wiki.eclipse.org/EclipseLink/UserGuide/JPA/Advanced_JPA_Development/Single-Table_Multi-Tenancy
  /* Persistence Context per Tenant: Using a single persistence unit definition in the
   * persistence.xml and a shared persistence unit (EntityManagerFactory and cache) the tenant
   * context is specified per persistence Context (EntityManager) at runtime using the
   * createEntityManager(Map) API. This approach can be used with @PersistenceUnit injection but
   * not with container managed @PersistenceContext injection. */

}
