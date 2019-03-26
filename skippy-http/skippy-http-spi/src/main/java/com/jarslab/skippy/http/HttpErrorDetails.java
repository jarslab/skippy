package com.jarslab.skippy.http;

import com.jarslab.skippy.ErrorDetails;

public interface HttpErrorDetails extends ErrorDetails
{
    int getStatusCode();

    String getMessage();

    default String getDetails()
    {
        return null;
    }
}
