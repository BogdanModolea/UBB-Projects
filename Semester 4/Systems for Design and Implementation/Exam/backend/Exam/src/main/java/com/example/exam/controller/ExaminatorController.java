package com.example.exam.controller;

import com.example.exam.entity.Examination;
import com.example.exam.entity.Examinator;
import com.example.exam.service.ExaminationService;
import com.example.exam.service.ExaminatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/examinator")
public class ExaminatorController {
    @Autowired
    private ExaminatorService examinatorService;

    @GetMapping
    public ResponseEntity<List<Examinator>> getAllExaminators() {
        return ResponseEntity.ok(examinatorService.getAllExaminators());
    }

    @GetMapping("/{examinatorID}")
    public ResponseEntity<Optional<Examinator>> getExaminatorByID(@PathVariable int examinatorID) {
        return ResponseEntity.ok(examinatorService.getAExaminatorByID(examinatorID));
    }

    @GetMapping("/name/{examinatorName}")
    public ResponseEntity<Examinator> getExaminatorByName(@PathVariable String examinatorName) {
        return ResponseEntity.ok(examinatorService.getExaminatorByName(examinatorName));
    }
}
