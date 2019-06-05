package com.jarslab.skippy.http.details;

import com.jarslab.skippy.http.HttpCodes;
import com.jarslab.skippy.http.HttpErrorDetails;

public class NetworkAuthenticationRequiredErrorDetails implements HttpErrorDetails
{
    @Override
    public int getCode()
    {
        return HttpCodes.NETWORK_AUTHENTICATION_REQUIRED_511;
    }

    @Override
    public String getMessage()
    {
        return "Network Authentication Required";
    }
}
