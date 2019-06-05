package com.jarslab.skippy.http.details;

import com.jarslab.skippy.http.HttpCodes;
import com.jarslab.skippy.http.HttpErrorDetails;

public class NotFoundErrorDetails implements HttpErrorDetails
{
    @Override
    public int getCode()
    {
        return HttpCodes.NOT_FOUND_404;
    }

    @Override
    public String getMessage()
    {
        return "Not Found";
    }
}
