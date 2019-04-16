package com.jarslab.skippy;

/**
 * A {@link ThrowableMatcher} represents predicate that returns true <i>if</i> throwable should be
 * matched.
 */
public interface ThrowableMatcher
{
    boolean match(Throwable throwable);
}
