package br.com.ada.challange.resources.exceptions;

import br.com.ada.challange.services.exceptions.DataIntegrityViolationException;
import br.com.ada.challange.services.exceptions.NotEnougthMoviesException;
import br.com.ada.challange.services.exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(NotEnougthMoviesException.class)
    public ResponseEntity<StandardError> objectNotFound(NotEnougthMoviesException ex, HttpServletRequest request) {
        return this.defaultErrorConstructor(HttpStatus.PRECONDITION_FAILED, ex, request);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException ex, HttpServletRequest request) {
        return this.defaultErrorConstructor(HttpStatus.NOT_FOUND, ex, request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> DataIntegrityViolation(DataIntegrityViolationException ex, HttpServletRequest request) {
        return this.defaultErrorConstructor(HttpStatus.BAD_REQUEST, ex, request);
    }

    private ResponseEntity<StandardError> defaultErrorConstructor(HttpStatus status, RuntimeException ex, HttpServletRequest request) {
        StandardError error = StandardError.builder()
                .timeStamp(LocalDateTime.now()).error(ex.getMessage()).status(status.value())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(status).body(error);
    }

}
