package org.example.Shelter.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleSecurityException(Exception exception) {
        HttpStatus status;
        String description;

        // TODO: Log the exception

        if (exception instanceof BadCredentialsException) {
            status = HttpStatus.UNAUTHORIZED;
            description = "The username or password is incorrect";
        } else if (exception instanceof AccountStatusException) {
            status = HttpStatus.FORBIDDEN;
            description = "The account is locked";
        } else if (exception instanceof AccessDeniedException) {
            status = HttpStatus.FORBIDDEN;
            description = "You are not authorized to access this resource";
        } else if (exception instanceof SignatureException) {
            status = HttpStatus.FORBIDDEN;
            description = "The JWT signature is invalid";
        } else if (exception instanceof ExpiredJwtException) {
            status = HttpStatus.FORBIDDEN;
            description = "The JWT token has expired";
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            description = "Unknown internal server error.";
        }

        return ResponseEntity.status(status).body(description);
    }
}