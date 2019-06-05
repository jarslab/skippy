package com.jarslab.skippy.http.details;

import com.jarslab.skippy.http.HttpCodes;
import com.jarslab.skippy.http.HttpErrorDetails;

public class PaymentRequiredErrorDetails implements HttpErrorDetails
{
    PaymentRequiredErrorDetails()
    {
    }

    @Override
    public int getCode()
    {
        return HttpCodes.PAYMENT_REQUIRED_402;
    }

    @Override
    public String getMessage()
    {
        return "Payment Required";
    }
}

