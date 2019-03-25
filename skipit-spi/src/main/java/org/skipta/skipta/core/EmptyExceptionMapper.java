package org.skipta.skipta.core;

import org.skipta.skipta.api.EmptyErrorDetails;
import org.skipta.skipta.api.ErrorDetails;

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

    @Override
    public boolean isEmpty()
    {
        return true;
    }
}
