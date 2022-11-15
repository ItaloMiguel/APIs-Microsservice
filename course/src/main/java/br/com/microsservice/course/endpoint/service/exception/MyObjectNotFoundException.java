package br.com.microsservice.course.endpoint.service.exception;

public class MyObjectNotFoundException extends RuntimeException {

    public MyObjectNotFoundException(String message) {
        super(message);
    }
}
