package com.jarslab.skippy.spi;

import com.jarslab.skippy.EmptyErrorDetails;
import com.jarslab.skippy.ErrorDetails;

class EmptyExceptionMapper implements ExceptionMapper
{
    @Override
    public ErrorDetails apply(final Throwable throwable)
    {
        return new EmptyErrorDetails();
    }

    @Override
    public boolean test(final Throwable throwable)
    {
        return false;
    }
}
