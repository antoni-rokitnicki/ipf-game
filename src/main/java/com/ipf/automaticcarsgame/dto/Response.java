package com.ipf.automaticcarsgame.dto;

public class Response<T> {
    private boolean success;
    private ResponseError error;
    private T data;

    public Response(T data) {
        this.success = true;
        this.data = data;
    }

    public Response(ResponseError responseError) {
        this.success = false;
        this.error = responseError;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ResponseError getError() {
        return error;
    }

    public void setError(ResponseError error) {
        this.error = error;
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
                "success=" + success +
                ", error=" + error +
                ", data=" + data +
                '}';
    }
}
