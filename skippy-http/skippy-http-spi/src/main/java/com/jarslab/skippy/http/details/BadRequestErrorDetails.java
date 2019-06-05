package com.jarslab.skippy.http.details;

import com.jarslab.skippy.http.HttpCodes;
import com.jarslab.skippy.http.HttpErrorDetails;

public class BadRequestErrorDetails implements HttpErrorDetails
{
    @Override
    public int getCode()
    {
        return HttpCodes.BAD_REQUEST_400;
    }

    @Override
    public String getMessage()
    {
        return "Bad Request";
    }
}
