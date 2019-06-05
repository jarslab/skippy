package com.jarslab.skippy.http.details;

import com.jarslab.skippy.http.HttpCodes;
import com.jarslab.skippy.http.HttpErrorDetails;

public class NotAcceptableErrorDetails implements HttpErrorDetails
{
    @Override
    public int getCode()
    {
        return HttpCodes.NOT_ACCEPTABLE_406;
    }

    @Override
    public String getMessage()
    {
        return "Not Acceptable";
    }
}
