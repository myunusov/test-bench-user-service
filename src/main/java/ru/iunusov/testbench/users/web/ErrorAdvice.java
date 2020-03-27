package ru.iunusov.testbench.users.web;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.iunusov.testbench.users.service.NotFoundException;

@ControllerAdvice
@Slf4j
public class ErrorAdvice {

  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ApiResponses(value = { @ApiResponse(responseCode = "500", description = "Internal server error") })
  public ResponseEntity<String> internalServerError(@NotNull final RuntimeException e) {
    log.error("Internal server error", e);
    return new ResponseEntity<>(
        "Internal server error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ApiResponses(value = { @ApiResponse(responseCode = "400", description = "Bad request") })
  public ResponseEntity<String> badRequest(@NotNull final IllegalArgumentException e) {
    return new ResponseEntity<>("Bad request: " + e.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ApiResponses(value = { @ApiResponse(responseCode = "404", description = "Resource not found") })
  public ResponseEntity<String> notFoundError(@NotNull final NotFoundException e) {
    return new ResponseEntity<>("Not found error: " + e.getMessage(), HttpStatus.NOT_FOUND);
  }

}
