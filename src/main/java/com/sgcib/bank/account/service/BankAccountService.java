package com.sgcib.bank.account.service;

import com.sgcib.bank.account.entity.BankAccount;
import com.sgcib.bank.account.exception.ResourceNotFoundException;
import com.sgcib.bank.account.repository.BankAccountRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankAccountService {

  private final BankAccountRepository bankAccountRepository;

  public void checkAccountExists(long accountId) {
    if (!bankAccountRepository.existsById(accountId)) {
      throw new ResourceNotFoundException(BankAccount.class.getName(), String.valueOf(accountId));
    }
  }

  public List<BankAccount> retrieveAllBankAccount() {
    return bankAccountRepository.findAll();
  }

}
