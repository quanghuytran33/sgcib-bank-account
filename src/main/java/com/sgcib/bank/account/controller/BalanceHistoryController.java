package com.sgcib.bank.account.controller;

import static com.sgcib.bank.account.controller.BankAccountController.ACCOUNT_PATH;

import com.sgcib.bank.account.entity.BalanceHistory;
import com.sgcib.bank.account.service.BalanceHistoryService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = BalanceHistoryController.BALANCE_HISTORY_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class BalanceHistoryController {

  public static final String BALANCE_HISTORY_PATH = "/balanceHistory";

  private final BalanceHistoryService balanceHistoryService;

  @GetMapping(value = ACCOUNT_PATH + "/{accountId}/actual")
  public BalanceHistory retrieveAccountBalance(@PathVariable("accountId") long accountId) {
    return balanceHistoryService.retrieveAccountBalance(accountId);
  }

  @GetMapping(value = ACCOUNT_PATH + "/{accountId}")
  public List<BalanceHistory> retrieveAccountBalanceByDate(
      @PathVariable("accountId") long accountId,
      @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = ISO.DATE) Optional<LocalDate> startDate,
      @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = ISO.DATE) Optional<LocalDate> endDate) {
    return balanceHistoryService.retrieveAllAccountBalance(accountId, startDate, endDate);
  }

}
