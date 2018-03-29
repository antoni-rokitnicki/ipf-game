package com.ipf.automaticcarsgame.validator;

import com.ipf.automaticcarsgame.dto.Result;

public interface Validator<T> {
    Result validate(T obj);

    default Result createSuccessValidation() {
        return new Result();
    }

    default Result createError(String code, String message) {
        final Result validation = new Result();
        final Result.Error error = new Result.Error();
        error.setCode(code);
        error.setMessage(message);
        validation.addError(error);
        return validation;
    }
}
