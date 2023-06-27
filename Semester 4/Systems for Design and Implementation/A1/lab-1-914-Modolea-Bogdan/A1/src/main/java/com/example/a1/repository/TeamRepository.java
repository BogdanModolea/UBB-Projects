package com.example.a1.repository;

import com.example.a1.model.EsportsTeams;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class TeamRepository {
    List<EsportsTeams> teamsList = new ArrayList<>();

    public TeamRepository(){
        teamsList.add(new EsportsTeams(
                "FNC",
                "Wunder",
                "Razork",
                "Humanoid",
                "Rekkles",
                "Rhuckz"
        ));
    }

    public List<EsportsTeams> getAll(){
        return teamsList;
    }

    public EsportsTeams getById(String id){
        return teamsList.stream()
                .filter(team -> team.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Team not found"));
    }

    public EsportsTeams create(EsportsTeams team){
        teamsList.add(team);
        return team;
    }

    public void update(EsportsTeams newTeam, String id){
        EsportsTeams currentTeam = teamsList.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Team not found"));

        int index = teamsList.indexOf(currentTeam);
        teamsList.set(index, newTeam);
    }

    public void delete(String id){
        teamsList.removeIf(team -> team.getId().equals(id));
    }
}
