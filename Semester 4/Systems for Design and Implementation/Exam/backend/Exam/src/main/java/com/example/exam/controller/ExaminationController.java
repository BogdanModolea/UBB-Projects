package com.example.exam.controller;

import com.example.exam.entity.Examination;
import com.example.exam.entity.Student;
import com.example.exam.service.ExaminationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/examination")
public class ExaminationController {
    @Autowired
    private ExaminationService service;

    @PostMapping
    public ResponseEntity<Examination> addExamination(@RequestBody @Valid Examination examination) {
        return new ResponseEntity<>(service.saveExamination(examination), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Examination>> getAllExaminations() {
        return ResponseEntity.ok(service.getAllExaminations());
    }

    @GetMapping("/{examinationID}")
    public ResponseEntity<Optional<Examination>> getExaminationByID(@PathVariable int examinationID) {
        return ResponseEntity.ok(service.getAExaminationByID(examinationID));
    }

    @GetMapping("/{examinationID}/{studName}")
    public boolean getExaminationByIDAndName(@PathVariable int examinationID, @PathVariable String studName) {
        Examination examination = service.getAExaminationByID(examinationID).get();

        for(Student student : examination.getStudents())
            if(student.getName().equals(studName))
                return false;

        return true;
    }
}
