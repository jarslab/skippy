package com.jarslab.skippy.spi;

import com.jarslab.skippy.ThrowableMatcher;

import java.util.Arrays;
import java.util.List;

public class ExactExceptionMatcher implements ThrowableMatcher
{
    private final boolean strict;
    private final List<Class<? extends Throwable>> throwableClasses;

    @SafeVarargs
    public ExactExceptionMatcher(final boolean strict, final Class<? extends Throwable>... throwableClasses)
    {
        this.strict = strict;
        this.throwableClasses = Arrays.asList(throwableClasses);
    }

    @Override
    public boolean match(final Throwable throwable)
    {
        return throwableClasses.stream().anyMatch(exceptionClass -> {
            if (strict) {
                return throwable.getClass().equals(exceptionClass);
            } else {
                return exceptionClass.isAssignableFrom(throwable.getClass());
            }
        });
    }
}
