package ru.iunusov.testbench.users.web;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.iunusov.testbench.users.service.NotFoundException;

@ControllerAdvice
@Slf4j
public class ErrorAdvice {

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<String> internalServerError(@NotNull final RuntimeException e) {
    log.error("Internal server error", e);
    return new ResponseEntity<>(
        "Internal server error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<String> notFoundError(@NotNull final NotFoundException e) {
    return new ResponseEntity<>("Not found error: " + e.getMessage(), HttpStatus.NOT_FOUND);
  }
}
