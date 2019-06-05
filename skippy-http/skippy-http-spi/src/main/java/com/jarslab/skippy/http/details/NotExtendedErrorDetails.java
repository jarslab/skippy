package com.jarslab.skippy.http.details;

import com.jarslab.skippy.http.HttpCodes;
import com.jarslab.skippy.http.HttpErrorDetails;

public class NotExtendedErrorDetails implements HttpErrorDetails
{
    @Override
    public int getCode()
    {
        return HttpCodes.NOT_EXTENDED_510;
    }

    @Override
    public String getMessage()
    {
        return "Not Extended";
    }
}
