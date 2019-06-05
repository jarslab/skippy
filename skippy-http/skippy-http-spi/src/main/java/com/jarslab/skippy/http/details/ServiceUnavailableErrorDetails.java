package com.jarslab.skippy.http.details;

import com.jarslab.skippy.http.HttpCodes;
import com.jarslab.skippy.http.HttpErrorDetails;

public class ServiceUnavailableErrorDetails implements HttpErrorDetails
{
    @Override
    public int getCode()
    {
        return HttpCodes.SERVICE_UNAVAILABLE_503;
    }

    @Override
    public String getMessage()
    {
        return "Service Unavailable";
    }
}
