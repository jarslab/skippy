package com.jarslab.skippy.http.details;

import com.jarslab.skippy.http.HttpCodes;
import com.jarslab.skippy.http.HttpErrorDetails;

public class LoopDetectedErrorDetails implements HttpErrorDetails
{
    @Override
    public int getCode()
    {
        return HttpCodes.LOOP_DETECTED_508;
    }

    @Override
    public String getMessage()
    {
        return "Loop Detected";
    }
}
