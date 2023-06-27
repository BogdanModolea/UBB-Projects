package com.example.exam.service;

import com.example.exam.entity.Examination;
import com.example.exam.entity.Examinator;
import com.example.exam.repository.ExaminatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExaminatorService {
    @Autowired
    private ExaminatorRepository examinatorRepository;

    public List<Examinator> getAllExaminators() {
        return examinatorRepository.findAll();
    }

    public Optional<Examinator> getAExaminatorByID(int examinatorID) {
        return examinatorRepository.findById(examinatorID);
    }

    public Examinator getExaminatorByName(String examinatorName) {
        return examinatorRepository.getByName(examinatorName);
    }
}
