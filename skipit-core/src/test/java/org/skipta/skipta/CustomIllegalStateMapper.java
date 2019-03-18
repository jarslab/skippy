package org.skipta.skipta;

import org.skipta.skipta.api.ErrorDetails;
import org.skipta.skipta.api.ThrowableMapper;

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
