package com.cepheid.cloud.skel;

import java.util.stream.Stream;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.cepheid.cloud.skel.controller.ItemController;
import com.cepheid.cloud.skel.model.Item;
import com.cepheid.cloud.skel.repository.CustomRepositoryImpl;
import com.cepheid.cloud.skel.repository.ItemRepository;

@SpringBootApplication(scanBasePackageClasses = { ItemController.class, SkelApplication.class })
@EnableJpaRepositories(basePackageClasses = { ItemRepository.class }, repositoryBaseClass = CustomRepositoryImpl.class)
@EntityScan(basePackageClasses = { Item.class })
public class SkelApplication {

  public static void main(String[] args) {
    SpringApplication.run(SkelApplication.class, args);
  }

  @Bean
  ApplicationRunner initItems(ItemRepository repository) {
    

    repository.setProperty("ADMINACCESS", 1);
    repository.setProperty("TENANT", null);
    System.out.println(repository.getProperties());
    
    return args -> {
      
      Stream.of("Lord of the rings", "Hobbit", "Silmarillion", "Unfinished Tales and The History of Middle-earth")
          .forEach(name -> {
            Item item = new Item();
            item.setName(name);
            if (name.equals("Hobbit")) {
              item.setTenant(1);
            }
            else {
              item.setTenant(2);
            }
            repository.save(item);
          });
      System.out.println(repository.getProperties());
      repository.findAll().forEach(System.out::println);
    };
  }

}
