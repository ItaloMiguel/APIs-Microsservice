package br.com.microsservice.course.endpoint.controller;

import br.com.microsservice.course.endpoint.model.Course;
import br.com.microsservice.course.endpoint.service.CourseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/v1/admin/course")
public class CourseController {

    private final CourseService courseService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Course>> list(@PageableDefault(value = 10) Pageable pageable) {
        return ResponseEntity.ok(courseService.list(pageable));
    }

}
