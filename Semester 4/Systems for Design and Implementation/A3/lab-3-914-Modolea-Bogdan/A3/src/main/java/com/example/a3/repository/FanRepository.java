package com.example.a3.repository;

import com.example.a3.entity.Fan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FanRepository extends JpaRepository<Fan, Integer> {
    @Query("FROM Fan f WHERE f.age > ?1")
    public List<Fan> filterAll(int age);

}
