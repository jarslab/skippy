package com.jarslab.skippy.http.details;

import com.jarslab.skippy.http.HttpCodes;
import com.jarslab.skippy.http.HttpErrorDetails;

public class RangeNotSatisfiableErrorDetails implements HttpErrorDetails
{
    @Override
    public int getCode()
    {
        return HttpCodes.RANGE_NOT_SATISFIABLE_416;
    }

    @Override
    public String getMessage()
    {
        return "Range Not Satisfiable";
    }
}
