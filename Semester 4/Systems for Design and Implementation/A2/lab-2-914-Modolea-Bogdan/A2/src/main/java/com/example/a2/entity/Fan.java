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
@Table(name = "FAN_TBL")
public class Fan {
    @Id
    @GeneratedValue
    private int fid;
    private String name;
    private int age;    // I know it's not ok, but I need it :)
    private String nationality;
    private String occupation;
    private String placeOfBirth;
}
