package com.example.a4.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "LEAGUE_TBL")
public class League {
    @Id
    @GeneratedValue
    private int lid;
    private String abbreviation;
    private String region;

    @Min(2010)
    @Max(2023)
    private int year;
    private String bestPlayer;
    private int audience;

    @OneToMany(mappedBy="league", fetch = FetchType.EAGER)
    private List<Team> teams;
}
