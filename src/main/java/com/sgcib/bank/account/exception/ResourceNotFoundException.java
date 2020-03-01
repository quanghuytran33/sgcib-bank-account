package com.sgcib.bank.account.exception;

public class ResourceNotFoundException extends RuntimeException {

  private static String MESSAGE = "Resource %s not found with %s";

  public ResourceNotFoundException(String resource, String id) {
    super(String.format(MESSAGE, resource, id));
  }

}
