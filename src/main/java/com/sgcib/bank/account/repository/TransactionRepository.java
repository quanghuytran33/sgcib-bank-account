package com.sgcib.bank.account.repository;

import com.sgcib.bank.account.entity.BankAccount;
import com.sgcib.bank.account.entity.Transaction;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

  List<Transaction> findByBankAccountAndValueDateBetween(
      BankAccount account,
      LocalDate startDate,
      LocalDate endDate);

}
