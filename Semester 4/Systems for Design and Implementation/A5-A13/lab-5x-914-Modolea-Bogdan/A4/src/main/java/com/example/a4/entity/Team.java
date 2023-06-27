package com.example.a4.entity;

import com.example.a4.entity.user.UserInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

/**
 * team entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "TEAM_TBL")
public class Team {
    @Id
    private int tid;

    @NotBlank(message = "Don't leave this field blank, please")
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

    @ManyToOne
    @JoinColumn(name = "id")
    @JsonIgnore
    private UserInfo user;
}
