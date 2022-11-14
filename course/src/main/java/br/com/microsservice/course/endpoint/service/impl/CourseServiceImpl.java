package br.com.microsservice.course.endpoint.service.impl;

import br.com.microsservice.course.endpoint.model.Course;
import br.com.microsservice.course.endpoint.repository.CourseRepository;
import br.com.microsservice.course.endpoint.service.CourseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Override
    public Iterable<Course> list(Pageable pageable) {
        log.info("Listing all course");
        return courseRepository.findAll(pageable);
    }
}
