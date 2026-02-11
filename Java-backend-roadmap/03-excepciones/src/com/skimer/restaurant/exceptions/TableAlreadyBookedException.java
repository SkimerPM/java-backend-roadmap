package com.skimer.restaurant.exceptions;

public class TableAlreadyBookedException extends RuntimeException {
  public TableAlreadyBookedException(String message) {
    super(message);
  }
}
