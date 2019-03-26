package com.jarslab.skippy;

import com.jarslab.skippy.ThrowableMatcher;

public class CustomIllegalStateMatcher implements ThrowableMatcher
{
    public boolean match(final Throwable throwable)
    {
        return throwable.getMessage().startsWith("custom");
    }
}
