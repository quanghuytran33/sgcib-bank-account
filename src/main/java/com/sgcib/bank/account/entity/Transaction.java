package com.sgcib.bank.account.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sgcib.bank.account.enumeration.ETransactionType;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Immutable;

@Entity
@Table
@Builder
@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Immutable
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column
  @Enumerated(EnumType.STRING)
  @NotNull
  private ETransactionType transactionType;

  @Column
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate transactionDate;

  @Column(updatable = false)
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate valueDate;

  @Column
  @DecimalMin(value = "0", inclusive = false)
  @Digits(integer = 11, fraction = 2)
  @NotNull
  private BigDecimal amount;

  @ManyToOne
  @JoinColumn(name = "account_id", nullable = false)
  private BankAccount bankAccount;

}
