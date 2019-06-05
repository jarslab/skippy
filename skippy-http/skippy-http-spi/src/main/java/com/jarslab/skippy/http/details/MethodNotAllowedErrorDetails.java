package com.jarslab.skippy.http.details;

import com.jarslab.skippy.http.HttpCodes;
import com.jarslab.skippy.http.HttpErrorDetails;

public class MethodNotAllowedErrorDetails implements HttpErrorDetails
{
    @Override
    public int getCode()
    {
        return HttpCodes.METHOD_NOT_ALLOWED_405;
    }

    @Override
    public String getMessage()
    {
        return "Method Not Allowed";
    }
}
