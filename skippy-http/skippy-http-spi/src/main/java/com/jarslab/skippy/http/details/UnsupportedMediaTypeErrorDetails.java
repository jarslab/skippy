package com.jarslab.skippy.http.details;

import com.jarslab.skippy.http.HttpCodes;
import com.jarslab.skippy.http.HttpErrorDetails;

public class UnsupportedMediaTypeErrorDetails implements HttpErrorDetails
{
    @Override
    public int getCode()
    {
        return HttpCodes.UNSUPPORTED_MEDIA_TYPE_415;
    }

    @Override
    public String getMessage()
    {
        return "Unsupported Media Type";
    }
}
