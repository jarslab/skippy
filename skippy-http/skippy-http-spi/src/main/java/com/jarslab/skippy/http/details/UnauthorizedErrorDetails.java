package com.jarslab.skippy.http.details;

import com.jarslab.skippy.http.HttpCodes;
import com.jarslab.skippy.http.HttpErrorDetails;

public class UnauthorizedErrorDetails implements HttpErrorDetails
{
    UnauthorizedErrorDetails()
    {
    }

    @Override
    public int getCode()
    {
        return HttpCodes.UNAUTHORIZED_401;
    }

    @Override
    public String getMessage()
    {
        return "Unauthorized";
    }
}
