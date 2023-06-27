package com.example.a4.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FanAndTeam {
    private int fid;
    private int fanSince;
    private String opinion;
}
