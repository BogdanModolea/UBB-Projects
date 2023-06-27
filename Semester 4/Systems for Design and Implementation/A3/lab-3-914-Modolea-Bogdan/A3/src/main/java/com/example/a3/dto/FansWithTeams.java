package com.example.a3.dto;

import com.example.a3.entity.Fan;
import com.example.a3.entity.FanOfTeam;
import com.example.a3.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FansWithTeams {
    private Fan fan;
    private List<Team> teams;
}
