package com.example.exam.service;

import com.example.exam.entity.Examination;
import com.example.exam.repository.ExaminationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExaminationService {
    @Autowired
    private ExaminationRepository examinationRepository;

    public Examination saveExamination(Examination examination) {
        return examinationRepository.save(examination);
    }

    public List<Examination> getAllExaminations() {
        return examinationRepository.findAll();
    }

    public Optional<Examination> getAExaminationByID(int examinationID) {
        return examinationRepository.findById(examinationID);
    }
}
