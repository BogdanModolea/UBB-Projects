package com.example.a4.dto;

import com.example.a4.entity.Team;
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
