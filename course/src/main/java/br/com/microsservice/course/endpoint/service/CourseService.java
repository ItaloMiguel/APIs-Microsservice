package br.com.microsservice.course.endpoint.service;

import br.com.microsservice.course.endpoint.model.dto.CourseDTO;
import org.springframework.data.domain.Pageable;

public interface CourseService {
    Iterable<CourseDTO> listAll(Pageable pageable);

    CourseDTO createNewCourse(CourseDTO courseRegistration);

    CourseDTO seachCourseById(Long id);

    CourseDTO updateInCourse(CourseDTO courseDTO, Long id);

    void removeCourse(Long id);
}
