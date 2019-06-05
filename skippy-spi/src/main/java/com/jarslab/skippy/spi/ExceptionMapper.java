package com.jarslab.skippy.spi;

import com.jarslab.skippy.ErrorDetails;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Contract for {@link Function} that maps throwables to {@link ErrorDetails}.
 *
 * <h4>Implementation notes</h4>
 * Before executing mapping function throwable have to be matched using {@code test(Throwable throwable} function.
 */
public interface ExceptionMapper extends Predicate<Throwable>, Function<Throwable, ErrorDetails>
{
}
