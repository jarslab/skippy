package com.jarslab.skippy.http.details;


import com.jarslab.skippy.http.HttpCodes;
import com.jarslab.skippy.http.HttpErrorDetails;

public class NotImplementedErrorDetails implements HttpErrorDetails
{
    @Override
    public int getCode()
    {
        return HttpCodes.NOT_IMPLEMENTED_501;
    }

    @Override
    public String getMessage()
    {
        return "Not Implemented";
    }
}
