package com.ipf.automaticcarsgame.dto;

public class ResponseError {
    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResponseError{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
