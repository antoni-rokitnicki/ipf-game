package com.ipf.automaticcarsgame.validator;

import com.ipf.automaticcarsgame.dto.Result;

public interface Validator<T> {
    Result validate(T obj);

    default Result createSuccessValidation() {
        return new Result();
    }
}
