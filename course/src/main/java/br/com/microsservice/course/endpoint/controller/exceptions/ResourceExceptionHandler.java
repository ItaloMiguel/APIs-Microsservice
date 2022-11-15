package br.com.microsservice.course.endpoint.controller.exceptions;

import br.com.microsservice.course.endpoint.service.exception.MyObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(MyObjectNotFoundException.class)
    public ResponseEntity<StardardError> objectNotFound(
            MyObjectNotFoundException exception, HttpServletRequest request) {
        StardardError stardardError = new StardardError(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(stardardError);
    }
}
