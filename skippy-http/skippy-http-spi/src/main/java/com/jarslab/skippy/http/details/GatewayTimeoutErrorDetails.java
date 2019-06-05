package com.jarslab.skippy.http.details;

import com.jarslab.skippy.http.HttpCodes;
import com.jarslab.skippy.http.HttpErrorDetails;

public class GatewayTimeoutErrorDetails implements HttpErrorDetails
{
    @Override
    public int getCode()
    {
        return HttpCodes.GATEWAY_TIMEOUT_504;
    }

    @Override
    public String getMessage()
    {
        return "Gateway Timeout";
    }
}
