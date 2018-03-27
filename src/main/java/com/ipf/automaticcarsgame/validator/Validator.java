package com.ipf.automaticcarsgame.validator;

import com.ipf.automaticcarsgame.dto.Response;

public interface Validator<T> {

    Response<T> validate(T t);
}
