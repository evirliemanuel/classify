package io.ermdev.alice.controller;

import io.ermdev.alice.dto.ClassDto;
import io.ermdev.alice.dto.StudentDto;
import io.ermdev.alice.entity.Student;
import io.ermdev.alice.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {

    private StudentRepository studentRepository;
    private ClassController classController;

    @Autowired
    public StudentController(StudentRepository studentRepository, ClassController classController){
        this.studentRepository = studentRepository;
        this.classController = classController;
    }

    @GetMapping("student/{studentId}")
    public StudentDto getStudentById(@PathVariable("studentId") Long studentId) {
        Student student = studentRepository.findById(studentId);
        List<ClassDto> classes = new ArrayList<>();
        student.getClasses().parallelStream().forEach(_class->{

        });
        return null;
    }
}
