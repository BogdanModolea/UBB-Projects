package com.example.a4.entity;

import com.example.a4.entity.user.UserInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

/**
 * fan entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "FAN_TBL")
public class Fan {
    @Id
    private int fid;

    @NotBlank(message = "Please insert a name")
    private String name;

    @Min(1)
    private int age;    // I know it's not ok, but I need it :)
    private String nationality;
    private String occupation;

    @NotBlank(message = "Don't leave this field blank, please")
    private String placeOfBirth;

    @OneToMany(mappedBy = "fan")
    @JsonManagedReference
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    List<FanOfTeam> supporter;

    @ManyToOne
    @JoinColumn(name = "id")
    @JsonIgnore
    private UserInfo user;
}
