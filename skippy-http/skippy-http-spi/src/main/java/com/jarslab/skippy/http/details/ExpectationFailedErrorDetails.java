package com.jarslab.skippy.http.details;

import com.jarslab.skippy.http.HttpCodes;
import com.jarslab.skippy.http.HttpErrorDetails;

public class ExpectationFailedErrorDetails implements HttpErrorDetails
{
    @Override
    public int getCode()
    {
        return HttpCodes.EXPECTATION_FAILED_417;
    }

    @Override
    public String getMessage()
    {
        return "Expectation Failed";
    }
}
