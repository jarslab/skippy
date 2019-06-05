package com.jarslab.skippy.http.details;

import com.jarslab.skippy.http.HttpCodes;
import com.jarslab.skippy.http.HttpErrorDetails;

public class PayloadTooLargeErrorDetails implements HttpErrorDetails
{
    @Override
    public int getCode()
    {
        return HttpCodes.PAYLOAD_TOO_LARGE_413;
    }

    @Override
    public String getMessage()
    {
        return "Payload Too Large";
    }
}
