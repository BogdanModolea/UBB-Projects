package com.example.a2.dto;

import com.example.a2.entity.Team;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeagueIdAndTeams {
    private int lid;
    private Team team;

//    public LeagueIdAndTeams(int lid, List<Team> teams) {
//        this.lid = lid;
//        this.teams = teams;
//    }
}
