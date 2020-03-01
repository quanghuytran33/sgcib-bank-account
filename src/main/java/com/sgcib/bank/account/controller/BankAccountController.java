package com.sgcib.bank.account.controller;

import static com.sgcib.bank.account.controller.BankAccountController.ACCOUNT_PATH;

import com.sgcib.bank.account.entity.BankAccount;
import com.sgcib.bank.account.repository.BankAccountRepository;
import com.sgcib.bank.account.service.BankAccountService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = ACCOUNT_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class BankAccountController {

  public static final String ACCOUNT_PATH = "/account";

  private final BankAccountService bankAccountService;

  @GetMapping
  public List<BankAccount> retrieveAllAccount() {
    return bankAccountService.retrieveAllBankAccount();
  }

}
