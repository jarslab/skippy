package org.skipta.skipta.core;

import org.skipta.skipta.api.ErrorDetails;
import org.skipta.skipta.api.ThrowableMapper;
import org.skipta.skipta.api.ThrowableMatcher;

import static java.util.Objects.requireNonNull;

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
