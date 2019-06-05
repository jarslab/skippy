package com.jarslab.skippy;

/**
 * A {@code ThrowableMapper} represents function that accepts {@link Throwable} and returns {@link ErrorDetails}.
 */
public interface ThrowableMapper
{
    ErrorDetails map(Throwable throwable);
}
