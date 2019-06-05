package com.jarslab.skippy.http.details;

import com.jarslab.skippy.http.HttpCodes;
import com.jarslab.skippy.http.HttpErrorDetails;

public class RequestHeaderFieldsTooLargeErrorDetails implements HttpErrorDetails
{
    @Override
    public int getCode()
    {
        return HttpCodes.REQUEST_HEADER_FIELDS_TOO_LARGE_431;
    }

    @Override
    public String getMessage()
    {
        return "Request Header Fields Too Large";
    }
}
