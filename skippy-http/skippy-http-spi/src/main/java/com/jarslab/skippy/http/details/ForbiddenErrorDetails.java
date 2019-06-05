package com.jarslab.skippy.http.details;

import com.jarslab.skippy.http.HttpCodes;
import com.jarslab.skippy.http.HttpErrorDetails;

public class ForbiddenErrorDetails implements HttpErrorDetails
{
    @Override
    public int getCode()
    {
        return HttpCodes.FORBIDDEN_403;
    }

    @Override
    public String getMessage()
    {
        return "Forbidden";
    }
}
