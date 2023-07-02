package com.meeting_planner.app.Handler;

import com.meeting_planner.app.Exception.BadRequestException;
import com.meeting_planner.app.Templates.UseResponse;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<Object> handleEntityNotFoundException(
    EntityNotFoundException ex
  ) {
    UseResponse<String> response = new UseResponse<>();
    response.setError("ENTITY_NOT_FOUND");
    response.setMessage(ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleException(
    MethodArgumentNotValidException ex
  ) {
    BindingResult bindingResult = ex.getBindingResult();
    List<FieldError> fieldErrors = bindingResult.getFieldErrors();
    List<String> errorMessages = new ArrayList<>();

    for (FieldError fieldError : fieldErrors) {
      String fieldName = fieldError.getField();
      String errorMessage = fieldError.getDefaultMessage();
      String fullErrorMessage = fieldName + ": " + errorMessage;
      errorMessages.add(fullErrorMessage);
    }

    UseResponse<List<String>> response = new UseResponse<>();
    response.setError("BAD_REQUEST");
    response.setMessage(errorMessages);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<Object> handleException(
    HttpMessageNotReadableException ex
  ) {
    UseResponse<String> response = new UseResponse<>();
    response.setError("BAD_REQUEST");
    response.setMessage(ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<Object> handleException(BadRequestException ex) {
    UseResponse<String> response = new UseResponse<>();
    response.setError("BAD_REQUEST");
    response.setMessage(ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleException(Exception ex) {
    UseResponse<String> response = new UseResponse<>();
    response.setError("ERROR");
    response.setMessage(ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }
}
