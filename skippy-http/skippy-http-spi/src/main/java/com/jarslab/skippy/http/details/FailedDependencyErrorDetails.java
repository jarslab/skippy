package com.jarslab.skippy.http.details;

import com.jarslab.skippy.http.HttpCodes;
import com.jarslab.skippy.http.HttpErrorDetails;

public class FailedDependencyErrorDetails implements HttpErrorDetails
{
    @Override
    public int getCode()
    {
        return HttpCodes.FAILED_DEPENDENCY_424;
    }

    @Override
    public String getMessage()
    {
        return "Failed Dependency";
    }
}
