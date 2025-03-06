package ru.jabka.x6order.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.jabka.x6order.model.ServiceError;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ServiceError> handleException(Exception e) {
        return ResponseEntity.internalServerError()
                .body(ServiceError.builder()
                        .success(false)
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ServiceError> handleBadRequestException(BadRequestException e) {
        return ResponseEntity.badRequest()
                .body(ServiceError.builder()
                        .success(false)
                        .message(e.getMessage())
                        .build());
    }
}