package com.jarslab.skippy.http.details;

import com.jarslab.skippy.http.HttpCodes;
import com.jarslab.skippy.http.HttpErrorDetails;

public class PreconditionFailedErrorDetails implements HttpErrorDetails
{
    @Override
    public int getCode()
    {
        return HttpCodes.PRECONDITION_FAILED_412;
    }

    @Override
    public String getMessage()
    {
        return "Precondition Failed";
    }
}
