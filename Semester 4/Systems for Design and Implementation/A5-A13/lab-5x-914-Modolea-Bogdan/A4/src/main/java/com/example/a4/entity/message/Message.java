package com.example.a4.entity.message;

import lombok.*;

/**
 * message entity
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Message {
    private String senderName;
    private String receiverName;
    private String message;
    private String date;
    private Status status;
}
