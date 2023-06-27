package com.example.exam.controller;

import com.example.exam.entity.Examinator;
import com.example.exam.entity.Student;
import com.example.exam.service.ExaminatorService;
import com.example.exam.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/student")
public class StudentContoller {
    @Autowired
    private StudentService studentService;

    @GetMapping
    public ResponseEntity<List<Student>> getAllStuds() {
        return ResponseEntity.ok(studentService.getAllStuds());
    }
}
