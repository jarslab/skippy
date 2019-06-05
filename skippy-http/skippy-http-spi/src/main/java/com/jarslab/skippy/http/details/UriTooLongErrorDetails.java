package com.jarslab.skippy.http.details;

import com.jarslab.skippy.http.HttpCodes;
import com.jarslab.skippy.http.HttpErrorDetails;

public class UriTooLongErrorDetails implements HttpErrorDetails
{
    @Override
    public int getCode()
    {
        return HttpCodes.URI_TOO_LONG_414;
    }

    @Override
    public String getMessage()
    {
        return "Uri Too Long";
    }
}
