package com.jarslab.skippy.http.details;

import com.jarslab.skippy.http.HttpCodes;
import com.jarslab.skippy.http.HttpErrorDetails;

public class UnavailableForLegalReasonsErrorDetails implements HttpErrorDetails
{
    @Override
    public int getCode()
    {
        return HttpCodes.UNAVAILABLE_FOR_LEGAL_REASONS_451;
    }

    @Override
    public String getMessage()
    {
        return "Unavailable For Legal Reasons";
    }
}
