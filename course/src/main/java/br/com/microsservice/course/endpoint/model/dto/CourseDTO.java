package br.com.microsservice.course.endpoint.model.dto;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {

    @NotNull(message = "This field cannot be empty")
    @Column(unique = true)
    private String name;
}
