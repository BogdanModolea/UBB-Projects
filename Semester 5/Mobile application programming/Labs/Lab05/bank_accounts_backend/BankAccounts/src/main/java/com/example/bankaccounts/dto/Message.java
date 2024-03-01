package com.example.bankaccounts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Message {
    public enum Type{
        ADD,
        DELETE,
        UPDATE
    }

    private Type type;
    private Object content;
    private String appId;
}
