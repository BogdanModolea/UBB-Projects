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
public class TeamAndNoFans {
    private int tid;
    private String name;
    private String top;
    private String jungle;
    private String mid;
    private String bot;
    private String support;
    private long counter;
    private String username;
}
