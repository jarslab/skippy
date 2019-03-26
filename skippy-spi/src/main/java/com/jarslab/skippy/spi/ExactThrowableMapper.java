package com.jarslab.skippy.spi;

import com.jarslab.skippy.ErrorDetails;
import com.jarslab.skippy.ThrowableMapper;

import static java.util.Objects.requireNonNull;

public class ExactThrowableMapper implements ThrowableMapper
{
    private final ErrorDetails errorDetails;

    public ExactThrowableMapper(final ErrorDetails errorDetails)
    {
        this.errorDetails = requireNonNull(errorDetails);
    }

    @Override
    public ErrorDetails map(final Throwable throwable)
    {
        return errorDetails;
    }
}
