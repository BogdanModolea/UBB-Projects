package com.example.exam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "STUDENT_TBL")
public class Student {
    @Id
    private int sid;
    @NotBlank(message = "Please insert a name")
    private String name;

    private int grade;

    @ManyToOne
    @JoinColumn(name = "examination_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Examination examination;
}
