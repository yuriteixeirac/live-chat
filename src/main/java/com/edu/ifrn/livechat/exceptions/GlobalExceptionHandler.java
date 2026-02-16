package com.edu.ifrn.livechat.exceptions;

import com.edu.ifrn.livechat.DTOs.ErrorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorDTO> handleInvalidCredentialsException(RuntimeException e) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorDTO(e.getMessage()));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorDTO> handleUserAlreadyExistsException(RuntimeException e) {
        return ResponseEntity
                .status(409)
                .body(new ErrorDTO(e.getMessage()));
    }

    @ExceptionHandler(UserDoesNotExistException.class)
    public ResponseEntity<ErrorDTO> handleUserDoesNotExist(RuntimeException e) {
        return ResponseEntity
                .status(404)
                .body(new ErrorDTO(e.getMessage()));
    }
}
