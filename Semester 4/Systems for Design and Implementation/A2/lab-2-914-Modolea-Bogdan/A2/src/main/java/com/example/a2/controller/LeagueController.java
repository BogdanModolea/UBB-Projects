package com.example.a2.controller;

import com.example.a2.entity.League;
import com.example.a2.entity.Team;
import com.example.a2.service.LeagueService;
import com.example.a2.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class LeagueController {
    @Autowired
    private LeagueService service;
    @Autowired
    private TeamService teamService;

    @PostMapping("/addLeague")
    public League addLeague(@RequestBody League league) {
        List<Team> teams = league.getTeams();
        List<Team> newTeams = new ArrayList<>();
        int len = teams.size();

        for (Team team : teams) {
            if (teamService.getTeamByName(team.getName()) == null)
                return null;

            newTeams.add(team);
        }

        if (newTeams.size() == len) {
            for (Team team : newTeams) {
                int teamID = teamService.getTeamIdByName(team.getName());
                teamService.deleteTeam(teamID);
            }

            league.setTeams(newTeams);
            return service.saveLeague(league);
        }
        return null;
    }

    @PostMapping("/addLeagues")
    public List<League> addLeagues(@RequestBody List<League> leagues) {
        return service.saveLeagues(leagues);
    }

    @GetMapping("/leagues")
    public List<League> findAllLeagues() {
        return service.getLeagues();
    }

    @GetMapping("/league/{id}")
    public League findLeagueById(@PathVariable int id) {
        return service.getLeagueById(id);
    }

    @PutMapping("/updateLeague")
    public League updateTeam(@RequestBody League league) {
        List<Team> teams = league.getTeams();
        List<Team> newTeams = new ArrayList<>();
        int len = teams.size();

        for (Team team : teams) {
            if (teamService.getTeamByName(team.getName()) == null)
                return null;

            newTeams.add(team);
        }

        if (newTeams.size() == len) {
            for (Team team : newTeams) {
                int teamID = teamService.getTeamIdByName(team.getName());
                teamService.deleteTeam(teamID);
            }

            league.setTeams(newTeams);
            return service.updateLeague(league);
        }
        return null;
    }

    @DeleteMapping("deleteLeague/{id}")
    public String deleteLeague(@PathVariable int id) {
        List<Team> teams = service.getLeagueById(id).getTeams();
        String msg = service.deleteLeague(id);
        teamService.saveTeams(teams);

        return msg;
    }
}
