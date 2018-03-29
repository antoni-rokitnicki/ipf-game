package com.ipf.automaticcarsgame.mapper;

import com.ipf.automaticcarsgame.dto.Response;
import com.ipf.automaticcarsgame.dto.ResponseError;
import com.ipf.automaticcarsgame.dto.ResponseErrorBuilder;
import com.ipf.automaticcarsgame.validator.ValidationResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseMapper {


    public static ResponseEntity<Object> map(ValidationResult validationResult) {
        ResponseEntity responseEntity;
        if (validationResult.isValid()) {
            responseEntity = new ResponseEntity<>(new Response<Void>(), HttpStatus.OK);
        } else {
            Response<Object> response = ResponseMapper.validationResultToResponse(validationResult);
            responseEntity = ResponseEntity.badRequest().body(response);
        }

        return responseEntity;
    }


    private static Response<Object> validationResultToResponse(ValidationResult validationResult) {
        Response<Object> response = new Response<>();

        for (ValidationResult.Error error : validationResult.getErrors()) {
            ResponseError responseError = ResponseErrorBuilder.builder().withCode(error.getCode()).withMessage(error.getMessage()).build();
            response.addError(responseError);
        }

        return response;
    }
}
