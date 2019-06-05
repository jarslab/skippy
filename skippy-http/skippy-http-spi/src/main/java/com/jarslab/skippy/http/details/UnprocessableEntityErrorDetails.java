package com.jarslab.skippy.http.details;

import com.jarslab.skippy.http.HttpCodes;
import com.jarslab.skippy.http.HttpErrorDetails;

public class UnprocessableEntityErrorDetails implements HttpErrorDetails
{
    @Override
    public int getCode()
    {
        return HttpCodes.UNPROCESSABLE_ENTITY_422;
    }

    @Override
    public String getMessage()
    {
        return "Unprocessable Entity";
    }
}
