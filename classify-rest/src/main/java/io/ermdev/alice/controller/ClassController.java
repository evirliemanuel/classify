package io.ermdev.alice.controller;

import io.ermdev.alice.dto.ClassDto;
import io.ermdev.alice.entity.Class;
import io.ermdev.alice.entity.Student;
import io.ermdev.alice.entity.Subject;
import io.ermdev.alice.repository.ClassRepository;
import io.ermdev.alice.repository.StudentRepository;
import io.ermdev.alice.repository.SubjectRepository;
import mapfierj.SimpleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ClassController {

    private ClassRepository classRepository;
    private StudentRepository studentRepository;
    private SubjectRepository subjectRepository;
    private SimpleMapper mapper;

    @Autowired
    public ClassController(ClassRepository classRepository, StudentRepository studentRepository,
                           SubjectRepository subjectRepository, SimpleMapper mapper) {
        this.classRepository = classRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.mapper = mapper;
    }

    @GetMapping("class/{classId}")
    public ClassDto getById(@PathVariable("classId") Long classId) {
        return mapper.set(classRepository.findById(classId)).mapTo(ClassDto.class);
    }

    @GetMapping("class/all")
    public List<ClassDto> getAll() {
        List<ClassDto> classes = new ArrayList<>();
        classRepository.findAll().parallelStream().forEach(_class ->
                classes.add(mapper.set(_class).mapTo(ClassDto.class)));
        return classes;
    }

    @PostMapping("class/add")
    public ClassDto add(@RequestParam(value = "studentId", required = true) Long studentId,
                        @RequestParam(value = "subjectId", required = true) Long subjectId, @RequestBody Class _class) {
        Student student = studentRepository.findById(studentId);
        Subject subject = subjectRepository.findById(subjectId);

        _class.setStudent(student);
        _class.setSubject(subject);

        return mapper.set(classRepository.save(_class)).mapTo(ClassDto.class);
    }

    @PutMapping("class/update/{classId}")
    public ClassDto updateById(@PathVariable("classId") Long classId,
                               @RequestParam(value = "studentId", required = false) Long studentId,
                               @RequestParam(value = "subjectId", required = false) Long subjectId,
                               @RequestBody Class _class) {
        Class currentClass = classRepository.findById(classId);
        Student student = currentClass.getStudent();
        Subject subject = currentClass.getSubject();

        if (studentId != null) {
            student = studentRepository.findById(studentId);
        }
        if (subjectId != null) {
            subject = subjectRepository.findById(subjectId);
        }
        _class.setId(classId);
        _class.setStudent(student);
        _class.setSubject(subject);

        return mapper.set(classRepository.save(_class)).mapTo(ClassDto.class);
    }

    @DeleteMapping("class/delete/{classId}")
    public ClassDto deleteById(@PathVariable("classId") Long classId) {
        Class _class = classRepository.findOne(classId);

        _class.setStudent(null);
        _class.setSubject(null);

        classRepository.save(_class);
        classRepository.delete(classId);

        return mapper.set(_class).mapTo(ClassDto.class);
    }
}
