package com.example.exam.repository;

import com.example.exam.entity.Examination;
import com.example.exam.entity.Examinator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExaminatorRepository extends JpaRepository<Examinator, Integer> {
    @Query("SELECT new com.example.exam.entity.Examinator(" +
            "e.exid, e.name) " +
            "FROM Examinator e " +
            "where e.name = ?1")
    public Examinator getByName(String name);
}
