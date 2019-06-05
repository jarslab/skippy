package com.jarslab.skippy.http.details;

import com.jarslab.skippy.http.HttpCodes;
import com.jarslab.skippy.http.HttpErrorDetails;

public class ConflictErrorDetails implements HttpErrorDetails
{
    @Override
    public int getCode()
    {
        return HttpCodes.CONFLICT_409;
    }

    @Override
    public String getMessage()
    {
        return "Conflict";
    }
}
