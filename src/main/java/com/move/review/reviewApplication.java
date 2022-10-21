package com.move.review;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class reviewApplication {

  public static void main(String[] args) {
    SpringApplication.run(reviewApplication.class, args);
  }

}
