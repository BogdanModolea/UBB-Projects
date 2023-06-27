package com.example.a4.controller;

import com.example.a4.dto.FansWithTeams;
import com.example.a4.dto.ReadHelper;
import com.example.a4.dto.TeamsAndAge;
import com.example.a4.entity.Fan;
import com.example.a4.entity.FanOfTeam;
import com.example.a4.entity.Team;
import com.example.a4.service.FanService;
import com.example.a4.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class FanController {
    @Autowired
    private FanService service;
    @Autowired
    private TeamService teamService;

    @PostMapping("/addFan")
    public Fan addFan(@RequestBody ReadHelper readHelper) {
        Fan fan = new Fan();
        fan.setAge(readHelper.getAge());
        fan.setOccupation(readHelper.getOccupation());
        fan.setNationality(readHelper.getNationality());
        fan.setName(readHelper.getName());
        fan.setPlaceOfBirth(readHelper.getPlaceOfBirth());
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

    @DeleteMapping("/deleteFan/{id}")
    public String deleteFan(@PathVariable int id) {
        return service.deleteFan(id);
    }

    @GetMapping("/filterFan/{age}")
    public List<Fan> filterFans(@PathVariable int age) {
        return service.filterFansByAge(age);
    }

    @GetMapping("/test/{tid}")
    public Team testTeam(@PathVariable("tid") int teamID) {
        return teamService.getTeamById(teamID);
    }

    @PostMapping("/addFan/{fid}/toTeam/{tid}")
    public FanOfTeam addFanToTeam(@PathVariable("tid") int teamID, @PathVariable("fid") int fanID, @RequestBody ReadHelper readHelper) {
        FanOfTeam fan = new FanOfTeam();
        fan.setTeam(teamService.getTeamById(teamID));
        fan.setFan(service.getFanById(fanID));
        fan.setOpinion(readHelper.getOpinion());
        fan.setFanSince(readHelper.getFanSince());

        return service.addFanToTeam(fan);
    }

    @DeleteMapping("/deleteFanOfTeam/{id}")
    String deleteFanOfTeam(@PathVariable int id) {
        if (service.getFansOfTeamsByID(id).isPresent())
            return service.deleteFanOfTeam(id);
        return "Not found";
    }

    @GetMapping("/fansOfTeams")
    public List<FansWithTeams> getAllFansOfTeams() {
        return service.getAllFansOfTeams();
    }

    @GetMapping("/fansOfTeams/{id}")
    public Optional<FanOfTeam> getFansOfTeamsByID(@PathVariable int id) {
        return service.getFansOfTeamsByID(id);
    }

    @GetMapping("/report")
    public List<TeamsAndAge> getReport(){
        List<TeamsAndAge> ans = new ArrayList<>();
        ans = service.getTeamsWithFans();

        return ans;
    }
}
