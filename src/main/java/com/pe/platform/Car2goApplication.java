package com.pe.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


/*
 * Car2Go Application
 *
 * @summary
 * It is responsible for starting the Spring Boot application.
 * It also enables JPA auditing.
 *
 * @since 1.0
 */

/**
 * The type Car 2 go application.
 */
@EnableJpaAuditing
@SpringBootApplication
public class Car2goApplication {

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(Car2goApplication.class, args);
  }
}
