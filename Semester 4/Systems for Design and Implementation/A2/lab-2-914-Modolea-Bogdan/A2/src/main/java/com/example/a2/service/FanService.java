package com.example.a2.service;

import com.example.a2.entity.Fan;
import com.example.a2.repository.FanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FanService {
    @Autowired
    private FanRepository repository;

    public Fan saveFan(Fan fan){
        return repository.save(fan);
    }

    public List<Fan> saveFans(List<Fan> fans) {
        return repository.saveAll(fans);
    }

    public List<Fan> getFans() {
        return repository.findAll();
    }

    public Fan getFanById(int id) {
        return repository.findById(id).orElse(null);
    }

    public String deleteFan(int id) {
        repository.deleteById(id);
        return "Fan " + id + " deleted.\n";
    }

    public Fan updateFan(Fan fan) {
        Fan current = repository.findById(fan.getFid()).orElse(null);

        current.setName(fan.getName());
        current.setAge(fan.getAge());
        current.setOccupation(fan.getOccupation());
        current.setNationality(fan.getNationality());
        current.setPlaceOfBirth(fan.getPlaceOfBirth());
        return repository.save(current);
    }

    public List<Fan> filterFansByAge(int age){
        return repository.filterAll(age);
    }
}
