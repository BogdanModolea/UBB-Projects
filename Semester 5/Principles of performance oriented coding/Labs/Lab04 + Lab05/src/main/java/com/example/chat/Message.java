package com.example.chat;

import javax.json.JsonObject;
import java.io.Serializable;

public class Message implements Serializable {
    private String message;
    private MessageType type;

    public Message(JsonObject jsonObject) {
        this.message = jsonObject.getString("message");
        this.type = MessageType.valueOf(jsonObject.getString("type"));
    }

    public String getMessage() {
        return message;
    }

    public MessageType getType() {
        return type;
    }

}
