package com.jarslab.skippy.spi;

import static java.util.Objects.requireNonNull;

import com.jarslab.skippy.ErrorDetails;
import com.jarslab.skippy.ThrowableMapper;
import com.jarslab.skippy.ThrowableMatcher;

class BaseExceptionMapper implements ExceptionMapper
{
    private final ThrowableMatcher throwableMatcher;
    private final ThrowableMapper throwableMapper;

    BaseExceptionMapper(final ThrowableMatcher throwableMatcher, final ThrowableMapper throwableMapper)
    {
        this.throwableMatcher = requireNonNull(throwableMatcher);
        this.throwableMapper = requireNonNull(throwableMapper);
    }

    @Override
    public ErrorDetails apply(final Throwable throwable)
    {
        return throwableMapper.map(throwable);
    }

    @Override
    public boolean test(final Throwable throwable)
    {
        return throwableMatcher.match(throwable);
    }
}
