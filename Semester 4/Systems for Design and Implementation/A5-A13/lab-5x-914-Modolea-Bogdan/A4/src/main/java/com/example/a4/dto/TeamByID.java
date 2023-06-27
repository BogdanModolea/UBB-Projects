package com.example.a4.dto;

import com.example.a4.entity.FanOfTeam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeamByID {
    private int id;
    private String name;
    private String top;
    private String jungle;
    private String mid;
    private String bot;
    private String support;
    private LeagueGetAll league;
    private List<FanOfTeam> fanOfTeamList = new ArrayList<>();
}
