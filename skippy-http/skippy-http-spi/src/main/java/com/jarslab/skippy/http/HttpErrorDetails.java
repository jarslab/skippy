package com.jarslab.skippy.http;


import com.jarslab.skippy.ErrorDetails;

public interface HttpErrorDetails extends ErrorDetails
{
    int getCode();

    String getMessage();

    default String getDetails()
    {
        return null;
    }
}
