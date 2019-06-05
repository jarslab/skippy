package com.jarslab.skippy.http.details;

import com.jarslab.skippy.http.HttpCodes;
import com.jarslab.skippy.http.HttpErrorDetails;

public class HttpVersionNotSupportedErrorDetails implements HttpErrorDetails
{
    @Override
    public int getCode()
    {
        return HttpCodes.HTTP_VERSION_NOT_SUPPORTED_505;
    }

    @Override
    public String getMessage()
    {
        return "Http Version Not Supported";
    }
}
