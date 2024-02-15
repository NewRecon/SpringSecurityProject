package com.example.security.controllers;

import com.example.security.models.Student;
import lombok.Builder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("api/v1/students")
@EnableMethodSecurity
public class StudentRestController {
    private List<Student> students = Stream.of(new Student(1,"Alex","7A"), new Student(2,"Petr","7A"),new Student(3,"Carl","7B")).collect(Collectors.toList());

    @GetMapping
    public List<Student> getAll(){
        return students;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    public Student getStudentByID(@PathVariable long id){
//        for (var el: students) {
//            if (el.getId()==id) return el;
//        }
//        return null;
        return students.stream().filter(e->e.getId()==id).findFirst().get();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    public Student delStudentById(@PathVariable long id){
        for (var el: students) {
            if (el.getId()==id) {
                students.remove(el);
                return el;
            }
        }
        return null;
    }

    @PostMapping()
    public Student addStudent(@RequestBody Student student){
        students.add(student);
        return students.stream().filter(e->e==student).findFirst().get();
    }


}
