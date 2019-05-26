package com.jarslab.skippy;

/**
 * Mapper that always returns {@link EmptyErrorDetails}.
 */
public class EmptyThrowableMapper implements ThrowableMapper
{
    @Override
    public ErrorDetails map(final Throwable throwable)
    {
        return new EmptyErrorDetails();
    }
}
