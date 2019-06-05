package com.jarslab.skippy.http.details;

import com.jarslab.skippy.http.HttpCodes;
import com.jarslab.skippy.http.HttpErrorDetails;

public class EnhanceYourCalmErrorDetails implements HttpErrorDetails
{
    @Override
    public int getCode()
    {
        return HttpCodes.ENHANCE_YOUR_CALM_420;
    }

    @Override
    public String getMessage()
    {
        return "Enhance Your Calm";
    }
}
