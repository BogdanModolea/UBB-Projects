package com.example.a3.dto;

import com.example.a3.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeamsAndAge {
    private Team team;
    private float age;
}
