package org.skipta.skipta.core;

import org.skipta.skipta.api.ThrowableMatcher;

import static java.util.Objects.requireNonNull;

public class StaticThrowableMatcher implements ThrowableMatcher
{
    private final Class<? extends Throwable> throwableClass;

    public StaticThrowableMatcher(final Class<? extends Throwable> throwableClass)
    {
        this.throwableClass = requireNonNull(throwableClass);
    }

    @Override
    public boolean match(final Throwable throwable)
    {
        return throwable.getClass().equals(throwableClass);
    }
}
