package com.jarslab.skippy.http.details;

import com.jarslab.skippy.http.HttpCodes;
import com.jarslab.skippy.http.HttpErrorDetails;

public class PreconditionRequiredErrorDetails implements HttpErrorDetails
{
    @Override
    public int getCode()
    {
        return HttpCodes.PRECONDITION_REQUIRED_428;
    }

    @Override
    public String getMessage()
    {
        return "Precondition Required";
    }
}
