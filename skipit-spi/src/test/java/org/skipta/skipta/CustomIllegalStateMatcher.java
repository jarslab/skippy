package org.skipta.skipta;

import org.skipta.skipta.api.ThrowableMatcher;

public class CustomIllegalStateMatcher implements ThrowableMatcher
{
    public boolean match(final Throwable throwable)
    {
        return throwable.getMessage().startsWith("custom");
    }
}
