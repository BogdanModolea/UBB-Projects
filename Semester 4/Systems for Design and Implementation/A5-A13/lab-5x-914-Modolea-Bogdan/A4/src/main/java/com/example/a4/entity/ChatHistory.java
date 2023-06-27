package com.example.a4.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * chat messages entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CHAT_HISTORY")
public class ChatHistory {
    @Id
    private int cid;
    private String senderName;
    private String message;
}
