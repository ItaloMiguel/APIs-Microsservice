package br.com.microsservice.course.endpoint.controller;

import br.com.microsservice.course.endpoint.model.dto.CourseDTO;
import br.com.microsservice.course.endpoint.service.CourseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/v1/admin/course")
public class CourseController {

    private final CourseService courseService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<CourseDTO>> listAll(@PageableDefault(value = 10) Pageable pageable) {
        log.info("Listing all courses. page size: " + pageable);
         return ResponseEntity.ok(courseService.listAll(pageable));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CourseDTO> findCourseForId(@PathVariable("id") @NotNull Long id) {
        log.info("Finding course by id. ID: " + id);
        return ResponseEntity.ok().body(courseService.seachCourseById(id));
    }


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CourseDTO> registerNewCourse(@RequestBody @Valid CourseDTO courseDTO) {
        log.info("Create a new course: " + courseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.createNewCourse(courseDTO));
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CourseDTO> updateCouse(
            @RequestBody @Valid CourseDTO courseDTO, @PathVariable("id") @NotNull Long id) {
        log.info("Updating course: " + courseDTO + " with ID: " + id);
        return ResponseEntity.ok().body(courseService.updateInCourse(courseDTO, id));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void removeCourse(@PathVariable @NotNull Long id) {
        log.info("Remove course with ID: " + id);
        courseService.removeCourse(id);
    }
}
