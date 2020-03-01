package com.sgcib.bank.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SgcibBankAccountApplication {

  public static void main(String[] args) {
    SpringApplication.run(SgcibBankAccountApplication.class, args);
  }
}
