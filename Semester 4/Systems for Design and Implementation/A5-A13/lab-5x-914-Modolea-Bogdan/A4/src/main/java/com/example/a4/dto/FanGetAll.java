package com.example.a4.dto;

import com.example.a4.entity.user.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FanGetAll {
    private int fid;
    private String name;
    private int age;
    private String nationality;
    private String occupation;
    private String placeOfBirth;
}
