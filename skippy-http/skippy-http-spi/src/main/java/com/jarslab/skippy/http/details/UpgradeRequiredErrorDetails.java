package com.jarslab.skippy.http.details;

import com.jarslab.skippy.http.HttpCodes;
import com.jarslab.skippy.http.HttpErrorDetails;

public class UpgradeRequiredErrorDetails implements HttpErrorDetails
{
    @Override
    public int getCode()
    {
        return HttpCodes.UPGRADE_REQUIRED_426;
    }

    @Override
    public String getMessage()
    {
        return "Upgrade Required";
    }
}
