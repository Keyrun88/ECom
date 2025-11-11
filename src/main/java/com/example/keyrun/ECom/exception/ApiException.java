package com.example.keyrun.ECom.exception;

import java.io.Serial;

public class ApiException extends RuntimeException
{
    @Serial
    private final static long serialVersionUID = 1L;

    public ApiException()
    {
    }

    public ApiException(String message)
    {
        super(message);
    }


}
