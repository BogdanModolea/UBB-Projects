package com.example.a4.entity.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * user entity
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    @Id
    private int id;
    private String name;
    private String email;
    private String password;
    private String roles;

    private boolean enabled;
    private String verificationCode;
    private LocalDateTime confirmationCodeSentAt;

    private String age;
    private String location;
    private int recordsOnPage;
}
