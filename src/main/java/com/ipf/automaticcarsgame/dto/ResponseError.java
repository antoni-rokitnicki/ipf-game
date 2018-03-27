package com.ipf.automaticcarsgame.dto;

import java.util.ArrayList;
import java.util.List;

public class ResponseError {
    private int code;
    private List<String> messages = new ArrayList<>();

    public ResponseError() {
    }

    public ResponseError(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void addMessage(String message) {
        this.messages.add(message);
    }

    @Override
    public String toString() {
        return "ResponseError{" +
                "code=" + code +
                ", messages=" + messages +
                '}';
    }
}
