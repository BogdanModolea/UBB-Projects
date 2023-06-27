package com.example.a2.controller;

import com.example.a2.entity.Fan;
import com.example.a2.entity.Team;
import com.example.a2.service.FanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FanController {
    @Autowired
    private FanService service;

    @PostMapping("/addFan")
    public Fan addFan(@RequestBody Fan fan) {
        return service.saveFan(fan);
    }

    @PostMapping("/addFans")
    public List<Fan> addFans(@RequestBody List<Fan> fans) {
        return service.saveFans(fans);
    }

    @GetMapping("/fans")
    public List<Fan> findAllFans() {
        return service.getFans();
    }

    @GetMapping("/fan/{id}")
    public Fan findFanById(@PathVariable int id) {
        return service.getFanById(id);
    }

    @PutMapping("/updateFan")
    public Fan updateTeam(@RequestBody Fan fan) {
        return service.updateFan(fan);
    }

    @DeleteMapping("deleteFan/{id}")
    public String deleteFan(@PathVariable int id) {
        return service.deleteFan(id);
    }

    @GetMapping("filterFan/{age}")
    public List<Fan> filterFans(@PathVariable int age) {
        return service.filterFansByAge(age);
    }
}
