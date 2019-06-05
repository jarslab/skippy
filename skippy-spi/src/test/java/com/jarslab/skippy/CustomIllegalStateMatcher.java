package com.jarslab.skippy;

public class CustomIllegalStateMatcher implements ThrowableMatcher
{
    public boolean match(final Throwable throwable)
    {
        return throwable.getMessage().startsWith("custom");
    }
}
