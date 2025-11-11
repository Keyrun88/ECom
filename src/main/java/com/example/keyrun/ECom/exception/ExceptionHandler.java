package com.example.keyrun.ECom.exception;

import com.example.keyrun.ECom.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandler
{
   @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> MethodArgumentNotValidException(MethodArgumentNotValidException e)
    {
        Map<String,String> response = new HashMap<>();
        e.getBindingResult()
                .getAllErrors()
                .forEach((error)->
                {
                    response.put(((FieldError)error).getField(), error.getDefaultMessage());
                });
         return  new ResponseEntity<Map<String,String>>(response, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse> apiException(ApiException e)
    {
        String message = e.getMessage();
        ApiResponse apiResponse = new ApiResponse(message,false);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}
