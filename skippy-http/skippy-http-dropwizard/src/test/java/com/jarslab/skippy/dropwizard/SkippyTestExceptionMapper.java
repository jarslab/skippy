package com.jarslab.skippy.dropwizard;

import com.jarslab.skippy.ErrorDetails;
import com.jarslab.skippy.ThrowableMapper;

public class SkippyTestExceptionMapper implements ThrowableMapper
{
    @Override
    public ErrorDetails map(final Throwable throwable)
    {
        return BaseHttpErrorDetails.of(500, throwable.getMessage());
    }
}
