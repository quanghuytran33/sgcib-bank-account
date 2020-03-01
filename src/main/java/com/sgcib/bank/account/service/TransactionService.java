package com.sgcib.bank.account.service;

import com.sgcib.bank.account.entity.BalanceHistory;
import com.sgcib.bank.account.entity.BankAccount;
import com.sgcib.bank.account.entity.Transaction;
import com.sgcib.bank.account.enumeration.ETransactionType;
import com.sgcib.bank.account.exception.ResourceNotFoundException;
import com.sgcib.bank.account.repository.BalanceHistoryRepository;
import com.sgcib.bank.account.repository.TransactionRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionService {

  private final BankAccountService bankAccountService;
  private final TransactionRepository transactionRepository;
  private final BalanceHistoryRepository balanceHistoryRepository;

  @Transactional
  public Transaction addTransaction(long accountId, Transaction transaction) {

    bankAccountService.checkAccountExists(accountId);

    transaction.setValueDate(LocalDate.now());
    transaction.setBankAccount(BankAccount.builder().id(accountId).build());
    Transaction result = transactionRepository.saveAndFlush(transaction);
    computeBalanceAtDate(result);
    return result;
  }

  @Transactional(readOnly = true)
  public List<Transaction> findTransactionsByAccount(long accountId, Optional<LocalDate> startDate,
      Optional<LocalDate> endDate) {
    bankAccountService.checkAccountExists(accountId);

    return transactionRepository
        .findByBankAccountAndValueDateBetween(BankAccount.builder().id(accountId).build(),
            startDate.orElse(LocalDate.now()), endDate.orElse(LocalDate.now()));
  }

  public List<Transaction> retrieveAllTransaction() {
    return transactionRepository.findAll();
  }

  private BalanceHistory computeBalanceAtDate(Transaction transaction) {

    Optional<BalanceHistory> balanceHistoryAtDate = balanceHistoryRepository
        .findByBankAccountAndDateOrderByDateDesc(transaction.getBankAccount(),
            transaction.getValueDate());

    BalanceHistory balanceHistoryToSave;
    if (balanceHistoryAtDate.isPresent()) {
      balanceHistoryToSave = balanceHistoryAtDate.get();
      balanceHistoryToSave.setBalance(
          computeAmountByTransactionType(balanceHistoryToSave.getBalance(), transaction.getAmount(),
              transaction.getTransactionType()));
    } else {
      Optional<BalanceHistory> lastBalanceHistory = balanceHistoryRepository
          .findFirstByBankAccountOrderByDateDesc(transaction.getBankAccount());
      BalanceHistory.BalanceHistoryBuilder balanceHistoryBuilder =
          BalanceHistory.builder()
              .bankAccount(transaction.getBankAccount())
              .date(LocalDate.now());
      if (lastBalanceHistory.isPresent()) {
        balanceHistoryBuilder
            .balance(computeAmountByTransactionType(lastBalanceHistory.get().getBalance(),
                transaction.getAmount(), transaction.getTransactionType()));
      } else {
        balanceHistoryBuilder
            .balance(computeAmountByTransactionType(BigDecimal.ZERO, transaction.getAmount(),
                transaction.getTransactionType()));
      }
      balanceHistoryToSave = balanceHistoryBuilder.build();
    }

    return balanceHistoryRepository.saveAndFlush(balanceHistoryToSave);
  }

  private BigDecimal computeAmountByTransactionType(BigDecimal initialAmount,
      BigDecimal transactionAmount, ETransactionType transactionType) {
    if (transactionType == ETransactionType.CREDIT) {
      return initialAmount.add(transactionAmount);
    } else {
      return initialAmount.subtract(transactionAmount);
    }
  }

}
