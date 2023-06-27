package com.example.a3.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
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


    @ManyToOne
    @JoinColumn(name = "league_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private League league;

    @OneToMany(mappedBy = "team")
    @JsonManagedReference
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    List<FanOfTeam> supporter;
}
