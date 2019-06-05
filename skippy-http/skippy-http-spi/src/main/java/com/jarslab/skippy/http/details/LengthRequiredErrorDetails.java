package com.jarslab.skippy.http.details;

import com.jarslab.skippy.http.HttpCodes;
import com.jarslab.skippy.http.HttpErrorDetails;

public class LengthRequiredErrorDetails implements HttpErrorDetails
{
    @Override
    public int getCode()
    {
        return HttpCodes.LENGTH_REQUIRED_411;
    }

    @Override
    public String getMessage()
    {
        return "Length Required";
    }
}
