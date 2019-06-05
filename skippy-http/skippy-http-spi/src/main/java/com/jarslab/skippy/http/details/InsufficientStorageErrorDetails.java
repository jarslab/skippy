package com.jarslab.skippy.http.details;

import com.jarslab.skippy.http.HttpCodes;
import com.jarslab.skippy.http.HttpErrorDetails;

public class InsufficientStorageErrorDetails implements HttpErrorDetails
{
    @Override
    public int getCode()
    {
        return HttpCodes.INSUFFICIENT_STORAGE_507;
    }

    @Override
    public String getMessage()
    {
        return "Insufficient Storage";
    }
}
