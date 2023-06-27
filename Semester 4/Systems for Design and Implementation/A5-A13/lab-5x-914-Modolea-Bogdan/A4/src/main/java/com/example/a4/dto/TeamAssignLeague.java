package com.example.a4.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeamAssignLeague {
    private int tid;
    private String name;
    private String top;
    private String jungle;
    private String mid;
    private String bot;
    private String support;
}
