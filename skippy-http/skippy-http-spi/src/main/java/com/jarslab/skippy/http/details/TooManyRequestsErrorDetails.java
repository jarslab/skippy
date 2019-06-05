package com.jarslab.skippy.http.details;

import com.jarslab.skippy.http.HttpCodes;
import com.jarslab.skippy.http.HttpErrorDetails;

public class TooManyRequestsErrorDetails implements HttpErrorDetails
{
    @Override
    public int getCode()
    {
        return HttpCodes.TOO_MANY_REQUESTS_429;
    }

    @Override
    public String getMessage()
    {
        return "Too Many Requests";
    }
}
