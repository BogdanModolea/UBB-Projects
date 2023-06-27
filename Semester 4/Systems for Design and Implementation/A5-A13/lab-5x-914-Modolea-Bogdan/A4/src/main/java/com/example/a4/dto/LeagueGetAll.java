package com.example.a4.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LeagueGetAll {
    private int lid;
    private String abbreviation;
    private String region;
    private int year;
    private String bestPlayer;
    private int audience;
    private String description;
}
