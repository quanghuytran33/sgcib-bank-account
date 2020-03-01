package com.sgcib.bank.account.controller;

import static com.sgcib.bank.account.controller.BankAccountController.ACCOUNT_PATH;

import com.sgcib.bank.account.entity.Transaction;
import com.sgcib.bank.account.service.TransactionService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = TransactionController.TRANSACTION_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TransactionController {

  public static final String TRANSACTION_PATH = "/transaction";

  private final TransactionService transactionService;

  @PostMapping(value = ACCOUNT_PATH + "/{accountId}", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public Transaction addTransaction(@PathVariable("accountId") long accountId,
      @RequestBody Transaction transaction) {
    return transactionService.addTransaction(accountId, transaction);
  }

  @GetMapping(value = ACCOUNT_PATH + "/{accountId}")
  public List<Transaction> retrieveTransactionsByAccount(@PathVariable("accountId") long accountId,
      @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = ISO.DATE) Optional<LocalDate> startDate,
      @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = ISO.DATE) Optional<LocalDate> endDate) {
    return transactionService.findTransactionsByAccount(accountId, startDate, endDate);
  }

  @GetMapping
  public List<Transaction> retrieveAllTransaction() {
    return transactionService.retrieveAllTransaction();
  }

}
