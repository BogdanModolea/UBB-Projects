package com.example.a2.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private int year;
    private String bestPlayer;
    private int audience;

    @OneToMany(targetEntity = Team.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "tl_fk", referencedColumnName = "lid")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Team> teams;
}
