package com.example.a4.dto;

import com.example.a4.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LeaguesAndNoTeams {
    private int lid;
    private String abbreviation;
    private String region;
    private int year;
    private String bestPlayer;
    private int audience;
    private String description;
    private long counter;
    private String username;
}
