package com.example.a4.dto;

import com.example.a4.entity.FanOfTeam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FanByID {
    private int fid;
    private String name;
    private int age;
    private String nationality;
    private String occupation;
    private String placeOfBirth;
    private List<FanOfTeam> fanOfTeamList = new ArrayList<>();
}
