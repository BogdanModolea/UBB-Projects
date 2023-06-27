package com.example.exam.repository;

import com.example.exam.entity.Examinator;
import com.example.exam.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}
