package com.app.msuser.client;

import com.app.msuser.model.response.ApiError;
import com.app.msuser.exception.ClientException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class PaymentDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        ApiError apiError;

        try {
            apiError = new ObjectMapper().readValue(response.body().asInputStream(), ApiError.class);
        } catch (IOException e) {
            log.error("Action.start.User.Client exception {}", e.getMessage());
            throw new ClientException("500", "UNEXPECT_ERROR");
        }

        return new ClientException(apiError.getCode(), String.valueOf(response.status()));
    }
}
