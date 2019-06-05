package com.jarslab.skippy.http.details;

import com.jarslab.skippy.http.HttpCodes;
import com.jarslab.skippy.http.HttpErrorDetails;

public class MisdirectedRequestErrorDetails implements HttpErrorDetails
{
    @Override
    public int getCode()
    {
        return HttpCodes.MISDIRECTED_REQUEST_421;
    }

    @Override
    public String getMessage()
    {
        return "Misdirected Request";
    }
}
