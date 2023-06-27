package com.example.a4.service;

import com.example.a4.dto.FansWithTeams;
import com.example.a4.dto.TeamsAndAge;
import com.example.a4.entity.Fan;
import com.example.a4.entity.FanOfTeam;
import com.example.a4.entity.Team;
import com.example.a4.repository.FanOfTeamRepository;
import com.example.a4.repository.FanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Map.Entry;

@Service

public class FanService {
    @Autowired
    private FanRepository repository;
    @Autowired
    private FanOfTeamRepository fanOfTeamRepository;

    public Fan saveFan(Fan fan) {
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

    public List<Fan> filterFansByAge(int age) {
        return repository.filterAll();
    }

    public String deleteFanOfTeam(int id) {
        if (fanOfTeamRepository.findById(id).isPresent()) {
            int fid = fanOfTeamRepository.findById(id).get().getFan().getFid();
            fanOfTeamRepository.deleteById(id);
            return "Fan with id " + fid + " is no longer a supporter";
        }
        return "Not Found";
    }

    public FanOfTeam addFanToTeam(FanOfTeam fan) {
        return fanOfTeamRepository.save(fan);
    }

    public Optional<FanOfTeam> getFansOfTeamsByID(int id) {
        return fanOfTeamRepository.findById(id);
    }

    public List<FansWithTeams> getAllFansOfTeams() {
        List<FansWithTeams> ans = new ArrayList<>();
        List<FanOfTeam> fanOfTeams = fanOfTeamRepository.findAll();
        HashMap<Fan, List<Team>> fanAndTeams = new HashMap<>();

        for (FanOfTeam fanOfTeam : fanOfTeams) {
            Team team = fanOfTeam.getTeam();
            Fan fan = fanOfTeam.getFan();

            if (fanAndTeams.get(fan) == null) {
                fanAndTeams.put(fan, new ArrayList<>());
                fanAndTeams.get(fan).add(team);
            } else
                fanAndTeams.get(fan).add(team);
        }

        for (Entry<Fan, List<Team>> entry : fanAndTeams.entrySet()) {
            entry.getKey().setSupporter(null);
            ans.add(new FansWithTeams(entry.getKey(), entry.getValue()));
        }
        return ans;
    }

    public List<TeamsAndAge> getTeamsWithFans(){
        List<TeamsAndAge> ans = new ArrayList<>();
        List<FanOfTeam> fanOfTeams = fanOfTeamRepository.findAll();
        HashMap<Team, List<Fan>> teamsAndFans = new HashMap<>();

        for (FanOfTeam fanOfTeam : fanOfTeams) {
            Team team = fanOfTeam.getTeam();
            Fan fan = fanOfTeam.getFan();

            if (teamsAndFans.get(team) == null) {
                teamsAndFans.put(team, new ArrayList<>());
                teamsAndFans.get(team).add(fan);
            } else
                teamsAndFans.get(team).add(fan);
        }

        for (Entry<Team, List<Fan>> entry : teamsAndFans.entrySet()) {
            float avg = 0;
            for(Fan fan : entry.getValue())
                avg += fan.getAge();
            avg /= entry.getValue().size();

            ans.add(new TeamsAndAge(entry.getKey(), avg));
        }

        return ans;
    }
}
