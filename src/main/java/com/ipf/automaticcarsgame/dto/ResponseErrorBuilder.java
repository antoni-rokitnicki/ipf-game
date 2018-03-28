package com.ipf.automaticcarsgame.dto;

import java.util.ArrayList;
import java.util.List;


public final class ResponseErrorBuilder {
    private int code;
    private List<String> messages = new ArrayList<>();

    private ResponseErrorBuilder() {
    }

    public static ResponseErrorBuilder responseError() {
        return new ResponseErrorBuilder();
    }

    public ResponseErrorBuilder withCode(int code) {
        this.code = code;
        return this;
    }

    public ResponseErrorBuilder withMessages(List<String> messages) {
        this.messages = messages;
        return this;
    }

    public ResponseErrorBuilder addMessage(String message) {
        this.messages.add(message);
        return this;
    }

    public ResponseError build() {
        ResponseError responseError = new ResponseError();
        this.messages.stream().forEach(mess -> responseError.addMessage(mess));
        responseError.setCode(code);
        return responseError;
    }
}
