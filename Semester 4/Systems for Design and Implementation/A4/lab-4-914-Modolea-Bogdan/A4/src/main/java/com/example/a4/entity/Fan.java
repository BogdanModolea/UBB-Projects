package com.example.a4.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "FAN_TBL")
public class Fan {
    @Id
    @GeneratedValue
    private int fid;

    @NotBlank(message = "Please insert a name")
    private String name;
    private int age;    // I know it's not ok, but I need it :)
    private String nationality;
    private String occupation;
    private String placeOfBirth;

    @OneToMany(mappedBy = "fan")
    @JsonManagedReference
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    List<FanOfTeam> supporter;
}
