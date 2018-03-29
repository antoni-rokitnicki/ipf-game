package com.ipf.automaticcarsgame.mapper;

import com.ipf.automaticcarsgame.dto.Response;
import com.ipf.automaticcarsgame.dto.ResponseError;
import com.ipf.automaticcarsgame.dto.ResponseErrorBuilder;
import com.ipf.automaticcarsgame.dto.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseMapper {


    public static ResponseEntity<Object> map(Result result) {
        ResponseEntity responseEntity;
        if (result.isValid()) {
            responseEntity = new ResponseEntity<>(new Response<Void>(), HttpStatus.OK);
        } else {
            Response<Object> response = ResponseMapper.validationResultToResponse(result);
            responseEntity = ResponseEntity.badRequest().body(response);
        }

        return responseEntity;
    }


    private static Response<Object> validationResultToResponse(Result result) {
        Response<Object> response = new Response<>();

        for (Result.Error error : result.getErrors()) {
            ResponseError responseError = ResponseErrorBuilder.builder().withCode(error.getCode()).withMessage(error.getMessage()).build();
            response.addError(responseError);
        }

        return response;
    }
}
