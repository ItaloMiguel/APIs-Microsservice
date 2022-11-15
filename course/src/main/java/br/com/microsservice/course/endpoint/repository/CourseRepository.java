package br.com.microsservice.course.endpoint.repository;

import br.com.microsservice.course.endpoint.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
