package org.skipta.skipta.api;

public class AcceptThrowableMatcher implements ThrowableMatcher
{
    @Override
    public boolean match(final Throwable throwable)
    {
        return true;
    }
}
