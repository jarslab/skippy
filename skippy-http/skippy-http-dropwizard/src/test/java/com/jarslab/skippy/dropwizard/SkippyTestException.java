package com.jarslab.skippy.dropwizard;

public abstract class SkippyTestException extends RuntimeException
{
    public SkippyTestException(final String message)
    {
        super(message);
    }
}
