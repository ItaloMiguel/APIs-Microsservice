package br.com.microsservice.course.endpoint.controller.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class StardardError {

    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String path;
}
