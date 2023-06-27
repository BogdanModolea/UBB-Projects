package com.example.a4.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * relation between a fan and a team
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "FANOF_TBL")
public class FanOfTeam {
    @Id
    int id;

    @ManyToOne
    @JoinColumn(name = "team_id")
    @JsonBackReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    Team team;

    @ManyToOne
    @JoinColumn(name = "fan_id")
    @JsonBackReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    Fan fan;

    int fanSince;
    String opinion;
}
