package br.com.microsservice.course.endpoint.service.impl;

import br.com.microsservice.course.endpoint.model.Course;
import br.com.microsservice.course.endpoint.model.dto.CourseDTO;
import br.com.microsservice.course.endpoint.repository.CourseRepository;
import br.com.microsservice.course.endpoint.service.CourseService;
import br.com.microsservice.course.endpoint.service.exception.MyObjectNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {

    private static final String OBJECT_NOR_FOUND = "Object with id %d not found";

    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    @Override
    public Iterable<CourseDTO> listAll(Pageable pageable) {
        log.info("Find courses service");
        Page<Course> obj = courseRepository.findAll(pageable);
        return obj.stream().map(this::toCourseDTO).toList();
    }

    @Override
    @Transactional
    public CourseDTO createNewCourse(CourseDTO courseDTO) {
        log.info("Create a new user :: service");
        courseRepository.save(toCouseEntity(courseDTO));
        return courseDTO;
    }

    @Override
    public CourseDTO seachCourseById(Long id) {
        log.info("Finding course by id :: service");
        Course course = verifyIfCourseIsPresent(courseRepository.findById(id), id);
        return toCourseDTO(course);
    }

    @Override
    @Transactional
    public CourseDTO updateInCourse(CourseDTO courseDTO, Long id) {
        log.info("Update the course :: service");
        Course course = verifyIfCourseIsPresent(courseRepository.findById(id), id);
        Course updateCourse = course.update(courseDTO);
        courseRepository.save(updateCourse);
        return toCourseDTO(updateCourse);
    }

    @Override
    @Transactional
    public void removeCourse(Long id) {
        courseRepository.deleteById(id);
    }

    private Course verifyIfCourseIsPresent(Optional<Course> optional, long id) {
        log.info(String.format("Verify if course with id %d is present", id));
        return optional.orElseThrow(() -> new MyObjectNotFoundException(String.format(OBJECT_NOR_FOUND, id)));
    }

    private Course toCouseEntity(CourseDTO courseDTO) {
        return modelMapper.map(courseDTO, Course.class);
    }

    private CourseDTO toCourseDTO(Course course) {
        return modelMapper.map(course, CourseDTO.class);
    }
}

