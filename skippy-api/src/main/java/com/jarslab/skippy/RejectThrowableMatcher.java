package com.jarslab.skippy;

/**
 * Matcher that always returns false.
 */
public class RejectThrowableMatcher implements ThrowableMatcher
{
    @Override
    public boolean match(final Throwable throwable)
    {
        return false;
    }
}
