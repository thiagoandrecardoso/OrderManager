package br.com.project.orderreceipt.controllers;

import br.com.project.orderreceipt.exceptions.DuplicateOrderException;
import br.com.project.orderreceipt.exceptions.ExceptionOrder;
import br.com.project.orderreceipt.exceptions.SendToQueueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DuplicateOrderException.class)
    public ResponseEntity<ExceptionOrder> handleDuplicateOrderException(DuplicateOrderException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ExceptionOrder(HttpStatus.CONFLICT.value(), e.getMessage())
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors =  new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(SendToQueueException.class)
    public ResponseEntity<String> handleSendToQueueException(SendToQueueException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
