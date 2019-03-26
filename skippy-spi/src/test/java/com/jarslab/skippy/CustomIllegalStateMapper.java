package com.jarslab.skippy;

public class CustomIllegalStateMapper implements ThrowableMapper
{
    @Override
    public ErrorDetails map(final Throwable throwable)
    {
        final MessageErrorDetails errorDetails = new MessageErrorDetails();
        errorDetails.message = throwable.getMessage();
        return errorDetails;
    }
}
