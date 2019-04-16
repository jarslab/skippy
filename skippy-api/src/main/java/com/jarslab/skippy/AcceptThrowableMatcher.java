package com.jarslab.skippy;

/**
 * Matcher that always returns true.
 */
public class AcceptThrowableMatcher implements ThrowableMatcher
{
    @Override
    public boolean match(final Throwable throwable)
    {
        return true;
    }
}
