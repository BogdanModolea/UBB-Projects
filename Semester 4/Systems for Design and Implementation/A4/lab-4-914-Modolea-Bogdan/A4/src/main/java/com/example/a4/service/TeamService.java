package com.example.a4.service;

import com.example.a4.dto.LeagueIdAndTeams;
import com.example.a4.entity.FanOfTeam;
import com.example.a4.entity.Team;
import com.example.a4.repository.FanOfTeamRepository;
import com.example.a4.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService {
    @Autowired
    private TeamRepository repository;
    @Autowired
    private FanOfTeamRepository fanOfTeamRepository;

    public Team saveTeam(Team team) {
        return repository.save(team);
    }

    public List<Team> saveTeams(List<Team> teams) {
        return repository.saveAll(teams);
    }

    public List<Team> getTeams() {
        return repository.findAll();
    }

    public Team getTeamById(int id) {
        return repository.findById(id).orElse(null);
    }

    public Team getTeamByName(String name) {
        List<Team> teams = repository.findAll();

        teams = teams.stream()
                .filter(team -> team.getName().equals(name))
                .collect(Collectors.toList());

        if (teams.size() != 0)
            return teams.get(0);

        return null;
    }

    public int getTeamIdByName(String name) {
        Team team = getTeamByName(name);
        return team.getTid();
    }

    public String deleteTeam(int id) {
        repository.deleteById(id);
        return "Team " + id + " deleted.\n";
    }

    public Team updateTeam(Team team) {
        Team current = repository.findById(team.getTid()).orElse(null);

        current.setName(team.getName());
        current.setTop(team.getTop());
        current.setJungle(team.getJungle());
        current.setMid(team.getMid());
        current.setBot(team.getBot());
        current.setSupport(team.getSupport());
        current.setLeague(team.getLeague());

        return repository.save(current);
    }
}
