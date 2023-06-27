package com.example.a4.dto;

import com.example.a4.entity.Team;
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
}
