package com.ipf.automaticcarsgame.validator;

public interface Validator<T> {
    ValidationResult validate(T obj);

    default ValidationResult createSuccessValidation() {
        return new ValidationResult();
    }
}
