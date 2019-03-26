package com.jarslab.skippy.spi;

import com.jarslab.skippy.ThrowableMatcher;

import java.util.Arrays;
import java.util.List;

public class ExactExceptionMatcher implements ThrowableMatcher
{
    private final List<Class<? extends Throwable>> throwableClasses;

    @SafeVarargs
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
