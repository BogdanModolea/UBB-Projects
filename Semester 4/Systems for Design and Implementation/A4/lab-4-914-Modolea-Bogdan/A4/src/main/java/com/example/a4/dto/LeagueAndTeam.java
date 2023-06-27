package com.example.a4.dto;

import com.example.a4.entity.League;
import com.example.a4.entity.Team;
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
