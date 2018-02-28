package io.ermdev.alice.controller;

import io.ermdev.alice.dto.SubjectDto;
import io.ermdev.alice.entity.Class;
import io.ermdev.alice.entity.Subject;
import io.ermdev.alice.entity.Term;
import io.ermdev.alice.repository.ClassRepository;
import io.ermdev.alice.repository.SubjectRepository;
import io.ermdev.alice.repository.TermRepository;
import mapfierj.SimpleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SubjectController {

    private SubjectRepository subjectRepository;
    private TermRepository termRepository;
    private ClassRepository classRepository;
    private SimpleMapper mapper;

    @Autowired
    public SubjectController(SubjectRepository subjectRepository, TermRepository termRepository,
                             ClassRepository classRepository, SimpleMapper mapper) {
        this.subjectRepository = subjectRepository;
        this.termRepository = termRepository;
        this.classRepository = classRepository;
        this.mapper = mapper;
    }

    @GetMapping("subject/{subjectId}")
    public SubjectDto getSubjectById(@PathVariable("subjectId") Long subjectId) {
        return mapper.set(subjectRepository.findById(subjectId)).mapAllTo(SubjectDto.class);
    }

    @GetMapping("subject/all")
    public List<SubjectDto> getAllSubject() {
        return mapper.set(subjectRepository.findAll()).mapToList(SubjectDto.class);
    }

    @PostMapping("subject/add")
    public SubjectDto add(@RequestParam(value = "termIds", required = false) List<Long> termIds,
                          @RequestParam(value = "classIds", required = false) List<Long> classIds,
                          @RequestBody Subject subject) {
        List<Term> terms = new ArrayList<>();
        List<Class> classes = new ArrayList<>();

        //Make it persist or detach object
        subject.getTerms().clear();
        subject.getClasses().clear();
        subject=subjectRepository.save(subject);

        final Long subjectId = subject.getId();
        if(termIds != null && termIds.size() > 0) {
            termIds.parallelStream().forEach(termId-> {
                Term term = termRepository.findById(termId);
                term.getSubjects().add(new Subject(subjectId));
                terms.add(term);
            });
            subject.getTerms().addAll(terms);
        }
        if(classIds != null && classIds.size() > 0) {
            classIds.parallelStream().forEach(classId -> {
                Class _class = classRepository.findById(classId);
                _class.setSubject(new Subject(subjectId));
                classes.add(_class);
            });
            subject.getClasses().addAll(classes);
        }
        subject=subjectRepository.save(subject);

        SubjectDto subjectDto = mapper.set(subject).mapTo(SubjectDto.class);
        return mapper.set(subject).mapAllTo(SubjectDto.class);
    }

    @PutMapping("subject/update/{subjectId}")
    public SubjectDto updateById(@PathVariable("subjectId") Long subjectId,
                                 @RequestParam(value = "termIds", required = false) List<Long> termIds,
                                 @RequestParam(value = "classIds", required = false) List<Long> classIds,
                                 @RequestBody(required = false) Subject subject) {
        Subject currentSubject = subjectRepository.findById(subjectId);
        List<Term> terms = new ArrayList<>();
        List<Class> classes = new ArrayList<>();

        if(subject != null) {
            subject.getTerms().clear();
            subject.getClasses().clear();
        } else {
            subject = new Subject();
        }
        subject.setId(subjectId);
        if(subject.getName() == null || subject.getName().trim().equals("")) {
            subject.setName(currentSubject.getName());
        }
        if(subject.getDescription() == null || subject.getDescription().trim().equals("")) {
            subject.setDescription(currentSubject.getDescription());
        }
        if(subject.getUnit() == null) {
            subject.setUnit(currentSubject.getUnit());
        }
        if(termIds != null && termIds.size() > 0) {
            currentSubject.getTerms().parallelStream().forEach(term -> term.getSubjects().remove(currentSubject));
            termIds.parallelStream().forEach(termId-> {
                Term term = termRepository.findById(termId);
                term.getSubjects().add(new Subject(subjectId));
                terms.add(term);
            });
            subject.getTerms().addAll(terms);
        } else {
            subject.getTerms().addAll(currentSubject.getTerms());
        }
        if(classIds != null && classIds.size() > 0) {
            currentSubject.getClasses().parallelStream().forEach(_class -> _class.setSubject(null));
            classIds.parallelStream().forEach(classId -> {
                Class _class = classRepository.findById(classId);
                _class.setSubject(new Subject(subjectId));
                classes.add(_class);
            });
            subject.getClasses().addAll(classes);
        } else {
            subject.getClasses().addAll(currentSubject.getClasses());
        }
        subjectRepository.save(subject);
        return mapper.set(subject).mapAllTo(SubjectDto.class);
    }

    @DeleteMapping("subject/delete/{subjectId}")
    public SubjectDto deleteById(@PathVariable("subjectId") Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId);

        subject.getTerms().parallelStream().forEach(term -> term.getSubjects().remove(subject));
        subject.getClasses().parallelStream().forEach(_class-> _class.setSubject(null));

        subject.getTerms().clear();
        subject.getClasses().clear();

        subjectRepository.save(subject);
        subjectRepository.delete(subject);

        return mapper.set(subject).mapAllTo(SubjectDto.class);
    }
}
