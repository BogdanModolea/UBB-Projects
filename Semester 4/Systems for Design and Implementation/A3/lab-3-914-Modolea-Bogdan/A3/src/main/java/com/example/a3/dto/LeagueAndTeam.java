package com.example.a3.dto;

import com.example.a3.entity.League;
import com.example.a3.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeagueAndTeam {
    private Team team;
    private League league;
}
