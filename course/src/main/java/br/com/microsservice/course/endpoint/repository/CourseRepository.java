package br.com.microsservice.course.endpoint.repository;

import br.com.microsservice.course.endpoint.model.Course;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends PagingAndSortingRepository<Course, Long> {
}
