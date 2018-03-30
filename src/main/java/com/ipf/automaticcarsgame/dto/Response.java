package com.ipf.automaticcarsgame.dto;

import java.util.ArrayList;
import java.util.List;

public class Response<T> {
    private List<ResponseError> errors = new ArrayList<>();
    private T data;

    public Response() {

    }

    public Response(T data) {
        this.data = data;
    }

    public Response(List<ResponseError> errors) {
        this.errors = errors;
    }

    public boolean isSuccess() {
        return errors.isEmpty();
    }

    public List<ResponseError> getErrors() {
        return errors;
    }

    public void setErrors(List<ResponseError> errors) {
        this.errors = errors;
    }

    public Response<T> addError(ResponseError responseError) {
        this.errors.add(responseError);
        return this;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "Response{" +
                ", errors=" + errors +
                ", data=" + data +
                '}';
    }
}
