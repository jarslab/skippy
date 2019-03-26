package com.jarslab.skippy;

public class AcceptThrowableMatcher implements ThrowableMatcher
{
    @Override
    public boolean match(final Throwable throwable)
    {
        return true;
    }
}
