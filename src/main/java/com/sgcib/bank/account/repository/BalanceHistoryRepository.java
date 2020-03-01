package com.sgcib.bank.account.repository;

import com.sgcib.bank.account.entity.BalanceHistory;
import com.sgcib.bank.account.entity.BankAccount;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceHistoryRepository extends JpaRepository<BalanceHistory, Long> {

  Optional<BalanceHistory> findFirstByBankAccountOrderByDateDesc(BankAccount bankAccount);
  Optional<BalanceHistory> findByBankAccountAndDateOrderByDateDesc(BankAccount bankAccount,
      LocalDate date);

  List<BalanceHistory> findByBankAccountAndDateBetween(BankAccount bankAccount, LocalDate startDate,
      LocalDate endDate);

}
