package com.example.a4.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LeagueRequest {
    private int lid;
    private String abbreviation;
    private String region;
    private int year;
    private String bestPlayer;
    private int audience;
    private String description;
    private String username;
}
