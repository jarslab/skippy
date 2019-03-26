package com.jarslab.skippy.spi;

import com.jarslab.skippy.ErrorDetails;

import java.util.function.Function;
import java.util.function.Predicate;

public interface ExceptionMapper extends Predicate<Throwable>, Function<Throwable, ErrorDetails>
{
    default boolean isEmpty()
    {
        return false;
    }
}
