package com.example.a1.controller;

import com.example.a1.model.EsportsTeams;
import com.example.a1.repository.TeamRepository;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class teamController {
    private final TeamRepository repository;


    public teamController(TeamRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<EsportsTeams> getAll(){
        return repository.getAll();
    }

    @GetMapping("/{id}")
    public EsportsTeams getById(@PathVariable String id){
        return repository.getById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public EsportsTeams create(@RequestBody EsportsTeams team){
        return repository.create(team);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@RequestBody EsportsTeams team, @PathVariable String id){
        repository.update(team, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void detele(@PathVariable String id){
        repository.delete(id);
    }
}
