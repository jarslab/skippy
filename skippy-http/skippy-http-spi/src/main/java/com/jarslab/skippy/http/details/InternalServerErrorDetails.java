package com.jarslab.skippy.http.details;

import com.jarslab.skippy.http.HttpCodes;
import com.jarslab.skippy.http.HttpErrorDetails;

public class InternalServerErrorDetails implements HttpErrorDetails
{
    @Override
    public int getCode()
    {
        return HttpCodes.INTERNAL_SERVER_ERROR_500;
    }

    @Override
    public String getMessage()
    {
        return "Internal Server Error";
    }
}
