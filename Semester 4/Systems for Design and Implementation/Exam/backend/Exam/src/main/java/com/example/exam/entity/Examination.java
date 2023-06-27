package com.example.exam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "EXAMINATION_TBL")
public class Examination {
    @Id
    private Long eid;
    @NotBlank(message = "Please insert a title")
    private String title;

    @NotBlank(message = "Please insert a name1")
    private String name1;

    @NotBlank(message = "Please insert a name2")
    private String name2;

    @NotBlank(message = "Please insert a name3")
    private String name3;

    @OneToMany(mappedBy="examination", fetch = FetchType.EAGER)
    private List<Student> students;

//    @ManyToOne
//    @JoinColumn(name = "exid")
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonIgnore
//    private Examinator examinator1;
//
//    @ManyToOne
//    @JoinColumn(name = "exid")
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonIgnore
//    private Examinator examinator2;
//
//    @ManyToOne
//    @JoinColumn(name = "exid")
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonIgnore
//    private Examinator examinator3;
}
