package com.deenn.datawarehouse.exception;

import com.deenn.datawarehouse.dtos.response.APIResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(DealNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public APIResponse<String> handleNotFoundException(DealNotFoundException ex) {
        log.error("throwing ::::: NotFoundException1() at : {}",ex.getStackTrace()[0]);
        log.error(ex.getMessage());
        return new APIResponse<>(ex.getMessage(), false, null);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public APIResponse<String> handleValidationException(ValidationException ex) {
        log.error("throwing ::::: ValidationException() at : {}",ex.getStackTrace()[0]);
        log.error(ex.getMessage());
        return new APIResponse<>(ex.getMessage(), false, null);
    }


    @ExceptionHandler(InvalidDealException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public APIResponse<String> handleDroneLoadedException(InvalidDealException ex) {
        log.error("throwing ::::: ValidationException() at : {}",ex.getStackTrace()[0]);
        log.error(ex.getMessage());
        return new APIResponse<>(ex.getMessage(), false, null);
    }



    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public APIResponse<String> handleBadRequestException(BadRequestException ex) {
        log.error("throwing ::::: BadRequestException() at : {}",ex.getStackTrace()[0]);
        log.error(ex.getMessage());
        return new APIResponse<>(
                ex.getMessage(),
                false,
                null
        );
    }

    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public APIResponse<String> handleInternalServerErrorException(InternalServerErrorException ex) {
        log.error("throwing ::::: InternalServerErrorException() at : {}",ex.getStackTrace()[0]);
        log.error(ex.getMessage());
        return new APIResponse<>(
                ex.getMessage(),
                false,
                null
        );
    }







    @ResponseBody
    @ExceptionHandler(value = { HttpMessageConversionException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public APIResponse<String> httpMessageConversionExceptionHandler(HttpMessageConversionException ex) {
        log.error("throwing this::::::::::::: HttpMessageConversionException : ");
        log.error(ex.getMessage());
        return new APIResponse<>(
                Objects.requireNonNullElse(ex.getLocalizedMessage()," ").split(";")[0],
                false,
                null
        );
    }

    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public APIResponse<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex) {
        log.error("throwing this::::::::::::: MethodArgumentNotValidException");
        log.error(ex.getMessage());
        List<String> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());


        String message = ex.getBindingResult().getFieldError() == null ?
                ex.getBindingResult().getAllErrors().get(0).getDefaultMessage() :
                ex.getBindingResult().getFieldError().getDefaultMessage();

        return new APIResponse<>(
                message,
                false,
                null
        );
    }



}

