package org.skipta.skipta.core;

import org.skipta.skipta.api.ErrorDetails;

import java.util.List;

public interface CombinedExceptionMapper extends ExceptionMapper
{
    @Override
    default ErrorDetails apply(final Throwable throwable)
    {
        return getMappers().stream()
                .filter(exceptionMapper -> exceptionMapper.test(throwable))
                .findFirst()
                .orElse(ClassExceptionMapperProvider.EMPTY_MAPPER)
                .apply(throwable);
    }

    @Override
    default boolean test(final Throwable throwable)
    {
        return getMappers().stream().anyMatch(exceptionMapper -> exceptionMapper.test(throwable));
    }

    @Override
    default boolean isEmpty()
    {
        return getMappers().isEmpty();
    }

    List<ExceptionMapper> getMappers();
}
