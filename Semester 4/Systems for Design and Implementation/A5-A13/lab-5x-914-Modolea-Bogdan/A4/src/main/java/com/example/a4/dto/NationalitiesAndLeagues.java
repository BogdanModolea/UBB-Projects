package com.example.a4.dto;

import com.example.a4.entity.League;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NationalitiesAndLeagues {
    private int lid;
    private String leagueName;
    private String region;
    private long counter;
}
