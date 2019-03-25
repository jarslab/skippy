package org.skipta.skipta.core;

import org.skipta.skipta.api.ThrowableMatcher;

import java.util.Arrays;
import java.util.List;

public class ExactExceptionMatcher implements ThrowableMatcher
{
    private final List<Class<? extends Throwable>> throwableClasses;

    public ExactExceptionMatcher(final Class<? extends Throwable>... throwableClasses)
    {
        this.throwableClasses = Arrays.asList(throwableClasses);
    }

    @Override
    public boolean match(final Throwable throwable)
    {
        return throwableClasses.contains(throwable.getClass());
    }
}
