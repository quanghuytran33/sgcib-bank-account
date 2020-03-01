package com.sgcib.bank.account.service;

import com.sgcib.bank.account.entity.BalanceHistory;
import com.sgcib.bank.account.entity.BankAccount;
import com.sgcib.bank.account.exception.ResourceNotFoundException;
import com.sgcib.bank.account.repository.BalanceHistoryRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BalanceHistoryService {

  private final BalanceHistoryRepository balanceHistoryRepository;

  public BalanceHistory retrieveAccountBalance(long accountId) {
    return balanceHistoryRepository
        .findFirstByBankAccountOrderByDateDesc(BankAccount.builder().id(accountId).build())
        .orElseThrow(() -> new ResourceNotFoundException(BalanceHistory.class.getName(), null));
  }

  public List<BalanceHistory> retrieveAllAccountBalance(long accountId,
      Optional<LocalDate> startDate, Optional<LocalDate> endDate) {
    return balanceHistoryRepository
        .findByBankAccountAndDateBetween(BankAccount.builder().id(accountId).build(),
            startDate.orElse(LocalDate.now()), endDate.orElse(LocalDate.now()));
  }

}
