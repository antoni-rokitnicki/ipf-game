package com.ipf.automaticcarsgame.dto;

public final class ResponseErrorBuilder {
    private String code;
    private String message;

    private ResponseErrorBuilder() {
    }

    public static ResponseErrorBuilder builder() {
        return new ResponseErrorBuilder();
    }

    public ResponseErrorBuilder withCode(String code) {
        this.code = code;
        return this;
    }

    public ResponseErrorBuilder withMessage(String message) {
        this.message = message;
        return this;
    }

    public ResponseError build() {
        ResponseError responseError = new ResponseError();
        responseError.setCode(code);
        responseError.setMessage(message);

        return responseError;
    }
}
