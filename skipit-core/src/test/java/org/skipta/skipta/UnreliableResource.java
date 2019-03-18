package org.skipta.skipta;

import org.skipta.skipta.api.ExceptionMapping;
import org.skipta.skipta.api.ExceptionMappings;

@ExceptionMappings(
        defaultMapping = @ExceptionMapping(exception = Throwable.class, errorDetails = OverallErrorDetails.class),
        value = @ExceptionMapping(exception = IllegalStateException.class, errorDetails = IllegalStateErrorDetails.class)
)
public class UnreliableResource
{
    public void notAnnotatedMethod()
    {
    }

    @ExceptionMapping(matcher = CustomIllegalStateMatcher.class, mapper = CustomIllegalStateMapper.class)
    public void customAnnotatedMethod()
    {
    }
}
