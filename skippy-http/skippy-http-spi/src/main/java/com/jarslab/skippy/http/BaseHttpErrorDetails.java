package com.jarslab.skippy.http;

import static java.util.Objects.requireNonNull;

public class BaseHttpErrorDetails implements HttpErrorDetails
{
    private final int statusCode;
    private final String message;
    private final String details;

    public static HttpErrorDetails of(final int statusCode, final String message)
    {
        return new BaseHttpErrorDetails(statusCode, message, null);
    }

    public static HttpErrorDetails of(final int statusCode, final String message, final String details)
    {
        return new BaseHttpErrorDetails(statusCode, message, details);
    }

    private BaseHttpErrorDetails(final int statusCode, final String message, final String details)
    {
        this.statusCode = statusCode;
        this.message = requireNonNull(message);
        this.details = details;
    }

    @Override
    public int getStatusCode()
    {
        return statusCode;
    }

    @Override
    public String getMessage()
    {
        return message;
    }

    @Override
    public String getDetails()
    {
        return details;
    }
}
