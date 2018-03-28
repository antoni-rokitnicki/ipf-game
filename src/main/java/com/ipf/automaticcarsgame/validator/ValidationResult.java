package com.ipf.automaticcarsgame.validator;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {
    private List<Error> errors = new ArrayList<>();

    public ValidationResult() {
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

    public ValidationResult addError(Error error){
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


    public static final class ValidationResultBuilder {
        private List<Error> errors = new ArrayList<>();

        private ValidationResultBuilder() {
        }

        public static ValidationResultBuilder builder() {
            return new ValidationResultBuilder();
        }

        public ValidationResultBuilder addError(Error error) {
            this.errors.add(error);
            return this;
        }

        public ValidationResult build() {
            ValidationResult validationResult = new ValidationResult();
            validationResult.setErrors(errors);
            return validationResult;
        }
    }
}
