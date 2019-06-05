package com.jarslab.skippy.http.details;

import com.jarslab.skippy.http.HttpCodes;
import com.jarslab.skippy.http.HttpErrorDetails;

public class ProxyAuthenticationRequiredErrorDetails implements HttpErrorDetails
{
    @Override
    public int getCode()
    {
        return HttpCodes.PROXY_AUTHENTICATION_REQUIRED_407;
    }

    @Override
    public String getMessage()
    {
        return "Proxy Authentication Required";
    }
}
