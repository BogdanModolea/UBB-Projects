package com.example.exam.service;

import com.example.exam.entity.Student;
import com.example.exam.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStuds() {
        return studentRepository.findAll();
    }
}
