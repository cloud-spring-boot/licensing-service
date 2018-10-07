package com.max.licensing.client;

public class MessageDto {

    public static final MessageDto UNDEFINED = new MessageDto("UNDEFINED");

    private String message;

    public MessageDto() {
    }

    public MessageDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
