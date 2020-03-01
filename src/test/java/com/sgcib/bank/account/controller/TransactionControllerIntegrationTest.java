package com.sgcib.bank.account.controller;

import static com.sgcib.bank.account.controller.BalanceHistoryController.BALANCE_HISTORY_PATH;
import static com.sgcib.bank.account.controller.BankAccountController.ACCOUNT_PATH;
import static com.sgcib.bank.account.controller.TransactionController.TRANSACTION_PATH;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sgcib.bank.account.AbstractBankAccountControllerIntegrationTest;
import com.sgcib.bank.account.entity.BalanceHistory;
import com.sgcib.bank.account.entity.Transaction;
import com.sgcib.bank.account.enumeration.ETransactionType;
import io.restassured.response.ExtractableResponse;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.hamcrest.Matchers;
import org.hamcrest.number.BigDecimalCloseTo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class TransactionControllerIntegrationTest extends
    AbstractBankAccountControllerIntegrationTest {

  @Test
  public void addTransaction_ACreditTransaction_PositiveBalance()
      throws JsonProcessingException {

    Transaction positiveTransaction =
        Transaction.builder()
            .amount(BigDecimal.valueOf(100))
            .transactionType(ETransactionType.CREDIT)
            .build();

    // @formatter:off
    addTransaction(positiveTransaction);

    Transaction[] transactions = when()
        .get(uri + TRANSACTION_PATH + ACCOUNT_PATH + "/1")
    .then()
        .extract()
        .as(Transaction[].class);
    assertEquals(positiveTransaction.getAmount().compareTo(transactions[0].getAmount()), 0);
    assertEquals(positiveTransaction.getTransactionType(), transactions[0].getTransactionType());


    BalanceHistory balanceHistoryExpected = BalanceHistory.builder().balance(BigDecimal.valueOf(100)).build();
    BalanceHistory balanceHistoryActual = given()
      .body(mapper.writeValueAsString(positiveTransaction))
      .contentType(MediaType.APPLICATION_JSON_VALUE)
    .when()
      .get(uri + BALANCE_HISTORY_PATH + "/account/1/actual")
    .then()
      .assertThat()
      .statusCode(HttpStatus.OK.value())
      .extract().body().as(BalanceHistory.class);

    assertEquals(balanceHistoryExpected.getBalance().compareTo(balanceHistoryActual.getBalance()), 0);
    // @formatter:on
  }

  @Test
  public void addTransaction_200CreditTransactionAnd100DebitTransaction_PositiveBalance()
      throws JsonProcessingException {

    Transaction positiveTransaction =
        Transaction.builder()
            .amount(BigDecimal.valueOf(200))
            .transactionType(ETransactionType.CREDIT)
            .build();

    Transaction negativeTransaction =
        Transaction.builder()
            .amount(BigDecimal.valueOf(100))
            .transactionType(ETransactionType.DEBIT)
            .build();

    // @formatter:off
    addTransaction(positiveTransaction);

    addTransaction(negativeTransaction);

    List<Transaction> transactions = Arrays.asList(
        when()
          .get(uri + TRANSACTION_PATH + ACCOUNT_PATH + "/1")
        .then()
          .extract()
          .as(Transaction[].class));

    transactions.sort(Comparator.comparing(Transaction::getValueDate));

    assertEquals(positiveTransaction.getAmount().compareTo(transactions.get(0).getAmount()), 0);
    assertEquals(positiveTransaction.getTransactionType(), transactions.get(0).getTransactionType());

    assertEquals(negativeTransaction.getAmount().compareTo(transactions.get(1).getAmount()), 0);
    assertEquals(negativeTransaction.getTransactionType(), transactions.get(1).getTransactionType());


    BalanceHistory balanceHistoryExpected = BalanceHistory.builder().balance(BigDecimal.valueOf(100)).build();
    BalanceHistory balanceHistoryActual = given()
        .body(mapper.writeValueAsString(positiveTransaction))
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when()
        .get(uri + BALANCE_HISTORY_PATH + "/account/1/actual")
        .then()
        .assertThat()
        .statusCode(HttpStatus.OK.value())
        .extract().body().as(BalanceHistory.class);

    assertEquals(balanceHistoryExpected.getBalance().compareTo(balanceHistoryActual.getBalance()), 0);
    // @formatter:on
  }

  private void addTransaction(Transaction transaction) throws JsonProcessingException {

    // @formatter:off
    given()
        .body(mapper.writeValueAsString(transaction))
        .contentType(MediaType.APPLICATION_JSON_VALUE)
    .when()
        .post(uri + TRANSACTION_PATH + ACCOUNT_PATH + "/1")
    .then()
        .assertThat()
        .statusCode(HttpStatus.CREATED.value());
    // @formatter:on
  }

}
