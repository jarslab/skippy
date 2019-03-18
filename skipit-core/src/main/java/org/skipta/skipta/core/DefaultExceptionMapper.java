package org.skipta.skipta.core;

import org.skipta.skipta.api.EmptyThrowableMapper;
import org.skipta.skipta.api.ErrorDetails;
import org.skipta.skipta.api.ThrowableMapper;

import static java.util.Objects.requireNonNull;

class DefaultExceptionMapper implements ExceptionMapper
{
    final static ExceptionMapper EMPTY_MAPPER = new DefaultExceptionMapper(new EmptyThrowableMapper());

    private final ThrowableMapper throwableMapper;

    DefaultExceptionMapper(final ThrowableMapper throwableMapper)
    {
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
        return true;
    }
}
