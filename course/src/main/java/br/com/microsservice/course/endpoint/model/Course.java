package br.com.microsservice.course.endpoint.model;

import br.com.microsservice.course.endpoint.model.dto.CourseDTO;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class Course implements Serializable {

    @Id
    @SequenceGenerator(
            name = "course_id",
            sequenceName = "course_id"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "course_id"
    )
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull(message = "This field cannot be empty")
    @Column(unique = true)
    private String name;

    public Course update(CourseDTO courseDTO) {
        this.setName(courseDTO.getName());
        return this;
    }
}
