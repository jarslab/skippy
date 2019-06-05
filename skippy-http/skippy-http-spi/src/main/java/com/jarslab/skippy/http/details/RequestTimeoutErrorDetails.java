package com.jarslab.skippy.http.details;

import com.jarslab.skippy.http.HttpCodes;
import com.jarslab.skippy.http.HttpErrorDetails;

public class RequestTimeoutErrorDetails implements HttpErrorDetails
{
    @Override
    public int getCode()
    {
        return HttpCodes.REQUEST_TIMEOUT_408;
    }

    @Override
    public String getMessage()
    {
        return "Request Timeout";
    }
}
