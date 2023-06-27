package com.example.a2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TEAM_TBL")
public class Team {
    @Id
    @GeneratedValue
    private int tid;
    private String name;
    private String top;
    private String jungle;
    private String mid;
    private String bot;
    private String support;
}
