package com.example.exam.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "EXAMINATOR_TBL")
public class Examinator {
    @Id
    private int exid;
    @NotBlank(message = "Please insert a name")
    private String name;
}
