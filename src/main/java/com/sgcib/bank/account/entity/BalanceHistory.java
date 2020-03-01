package com.sgcib.bank.account.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"account_id", "date"}))
@Builder
@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BalanceHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(updatable = false)
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate date;

  @Column
  private BigDecimal balance;

  @ManyToOne
  @JoinColumn(name = "account_id", nullable = false)
  private BankAccount bankAccount;
}
