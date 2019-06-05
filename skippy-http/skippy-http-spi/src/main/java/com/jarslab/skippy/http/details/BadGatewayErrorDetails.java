package com.jarslab.skippy.http.details;

import com.jarslab.skippy.http.HttpCodes;
import com.jarslab.skippy.http.HttpErrorDetails;

public class BadGatewayErrorDetails implements HttpErrorDetails
{
    @Override
    public int getCode()
    {
        return HttpCodes.BAD_GATEWAY_502;
    }

    @Override
    public String getMessage()
    {
        return "Bad Gateway";
    }
}
