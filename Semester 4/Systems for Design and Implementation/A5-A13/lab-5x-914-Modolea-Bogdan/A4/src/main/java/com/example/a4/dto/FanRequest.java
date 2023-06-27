package com.example.a4.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FanRequest {
    private String name;
    private int age;
    private String nationality;
    private String occupation;
    private String placeOfBirth;
    private String username;
}
