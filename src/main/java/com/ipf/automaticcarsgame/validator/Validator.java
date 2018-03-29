package com.ipf.automaticcarsgame.validator;

public interface Validator<T> {
    Result validate(T obj);

    default Result createSuccessValidation() {
        return new Result();
    }
}
