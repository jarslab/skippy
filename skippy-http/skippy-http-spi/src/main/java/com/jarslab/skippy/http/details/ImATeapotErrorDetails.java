package com.jarslab.skippy.http.details;

import com.jarslab.skippy.http.HttpCodes;
import com.jarslab.skippy.http.HttpErrorDetails;

public class ImATeapotErrorDetails implements HttpErrorDetails
{
    @Override
    public int getCode()
    {
        return HttpCodes.IM_A_TEAPOT_418;
    }

    @Override
    public String getMessage()
    {
        return "I'm A Teapot";
    }
}
