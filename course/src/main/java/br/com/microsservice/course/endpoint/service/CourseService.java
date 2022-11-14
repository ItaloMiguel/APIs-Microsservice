package br.com.microsservice.course.endpoint.service;

import br.com.microsservice.course.endpoint.model.Course;
import org.springframework.data.domain.Pageable;

public interface CourseService {
    public Iterable<Course> list(Pageable pageable);
}
