package com.ipf.automaticcarsgame.dto;

import java.util.ArrayList;
import java.util.List;

public class Result {
    private List<Error> errors = new ArrayList<>();

    public Result() {
    }

    public boolean isValid() {
        return errors.isEmpty();
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    public Result addError(Error error) {
        this.errors.add(error);
        return this;
    }

    public static class Error {
        private String code;
        private String message;

        public Error() {
        }

        public Error(String message) {
            this.message = message;
        }

        public Error(String code, String message) {
            this.code = code;
            this.message = message;
        }

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
            return "Error{" +
                    "code='" + code + '\'' +
                    ", message='" + message + '\'' +
                    '}';
        }
    }


    public static final class ResultBuilder {
        private List<Error> errors = new ArrayList<>();

        private ResultBuilder() {
        }

        public static ResultBuilder builder() {
            return new ResultBuilder();
        }

        public ResultBuilder addError(Error error) {
            this.errors.add(error);
            return this;
        }

        public ResultBuilder addErrors(List<Error> errors) {
            this.errors.addAll(errors);
            return this;
        }

        public Result build() {
            Result result = new Result();
            result.setErrors(errors);
            return result;
        }
    }


}
