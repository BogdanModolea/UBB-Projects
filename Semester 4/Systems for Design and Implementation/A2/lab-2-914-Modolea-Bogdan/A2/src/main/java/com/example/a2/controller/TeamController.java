package com.example.a2.controller;

import com.example.a2.dto.LeagueAndTeam;
import com.example.a2.dto.LeagueIdAndTeams;
import com.example.a2.entity.League;
import com.example.a2.entity.Team;
import com.example.a2.service.LeagueService;
import com.example.a2.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class TeamController {
    @Autowired
    private TeamService service;
    @Autowired
    private LeagueService leagueService;

    @PostMapping("/addTeam")
    public Team addTeam(@RequestBody Team team){
        return service.saveTeam(team);
    }

    @PostMapping("/addTeams")
    public List<Team> addTeams(@RequestBody List<Team> teams){
        return service.saveTeams(teams);
    }

    @GetMapping("/teams")
    public List<LeagueIdAndTeams> findAllTeams(){
        List<Team> teams = service.getTeams();


        List<League> leagues = leagueService.getWithoutNullLeagues();

        List<LeagueIdAndTeams> ans = new ArrayList<>();

        for(Team team : teams){
            for(League league : leagues){
                List<Team> newTeams = league.getTeams();
                for(Team newTeam : newTeams){
                    if(Objects.equals(newTeam.getName(), team.getName())){
                        ans.add(new LeagueIdAndTeams(league.getLid(), team));
                        break;
                    }
                }
            }
        }

        return ans;
    }

    @GetMapping("/team/{id}")
    public List<LeagueAndTeam>findTeamById(@PathVariable int id){

        List<LeagueAndTeam> ans = new ArrayList<>();

        Team team = service.getTeamById(id);
        List<League> leagues = leagueService.getWithoutNullLeagues();

        for(League league : leagues){
            List<Team> newTeams = league.getTeams();
            for(Team newTeam : newTeams){
                if(Objects.equals(newTeam.getName(), team.getName())){
                    ans.add(new LeagueAndTeam(team, league));
                    break;
                }
            }
        }

        for(LeagueAndTeam elem : ans)
            elem.getLeague().setTeams(null);

        return ans;
    }

    @PutMapping("/updateTeam")
    public Team updateTeam(@RequestBody Team team){
        return service.updateTeam(team);
    }

    @DeleteMapping("deleteTeam/{id}")
    public String deleteTeam(@PathVariable int id){
        return service.deleteTeam(id);
    }
}
