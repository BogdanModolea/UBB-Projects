package com.example.a2.dto;

import com.example.a2.entity.League;
import com.example.a2.entity.Team;
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
