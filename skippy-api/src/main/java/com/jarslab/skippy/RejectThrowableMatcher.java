package com.jarslab.skippy;

public class RejectThrowableMatcher implements ThrowableMatcher
{
    @Override
    public boolean match(final Throwable throwable)
    {
        return false;
    }
}
