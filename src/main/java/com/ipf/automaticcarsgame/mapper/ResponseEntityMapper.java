package com.ipf.automaticcarsgame.mapper;

import com.ipf.automaticcarsgame.dto.Response;
import com.ipf.automaticcarsgame.dto.ResponseError;
import com.ipf.automaticcarsgame.dto.ResponseErrorBuilder;
import com.ipf.automaticcarsgame.dto.Result;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ResponseEntityMapper {

    private ResponseEntityMapper() {
    }

    public static ResponseEntity<Response<Void>> mapToResponseEntity(Result result) {
        if (!result.isValid()) {
            return createBadRequestResponse(result);
        }
        return ResponseEntity.ok().body(new Response<Void>());
    }

    public static <T> ResponseEntity<Response<T>> mapToResponseEntity(Result validationResult, T result) {
        if (!validationResult.isValid()) {
            return createBadRequestResponse(validationResult);
        }
        return ResponseEntity.ok().body(new Response<>(result));
    }

    public static <T> ResponseEntity<Response<T>> mapToResponseEntity(T data) {
        return ResponseEntity.ok().body(new Response<>(data));
    }

    private static <T> ResponseEntity<Response<T>> createBadRequestResponse(Result validationResult) {
        final Response response = new Response<>();
        final List<ResponseError> responseErrors = validationResult.getErrors().stream().map(error -> ResponseErrorBuilder.builder().withCode(error.getCode()).withMessage(error.getMessage()).build()).collect(Collectors.toList());
        response.setErrors(responseErrors);
        return ResponseEntity.badRequest().body(response);
    }

}
