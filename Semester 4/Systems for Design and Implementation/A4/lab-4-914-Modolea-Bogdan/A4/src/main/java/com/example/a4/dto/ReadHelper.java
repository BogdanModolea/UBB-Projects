package com.example.a4.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
// to avoid Content-Type 'application/json;charset=UTF-8' is not supported
public class ReadHelper {
    private int fanSince;
    private String opinion;

    private String name;
    private int age;
    private String nationality;
    private String occupation;
    private String placeOfBirth;
}
