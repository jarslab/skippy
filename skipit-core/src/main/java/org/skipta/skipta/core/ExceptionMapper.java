package org.skipta.skipta.core;

import org.skipta.skipta.api.ErrorDetails;

import java.util.function.Function;
import java.util.function.Predicate;

public interface ExceptionMapper extends Predicate<Throwable>, Function<Throwable, ErrorDetails>
{
}
