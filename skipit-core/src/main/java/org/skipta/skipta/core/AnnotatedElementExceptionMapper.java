package org.skipta.skipta.core;

import org.skipta.skipta.api.EmptyErrorDetails;
import org.skipta.skipta.api.ErrorDetails;
import org.skipta.skipta.api.ThrowableMapper;
import org.skipta.skipta.api.ThrowableMatcher;
import org.skipta.skipta.api.ExceptionMapping;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AnnotatedElementExceptionMapper implements ExceptionMapper
{
    private final List<ExceptionMapper> exceptionMappers;

    AnnotatedElementExceptionMapper(final AnnotatedElement annotatedElement)
    {
        this.exceptionMappers = createExceptionMappers(annotatedElement);
    }

    @Override
    public ErrorDetails apply(final Throwable throwable)
    {
        return exceptionMappers.stream()
                .filter(exceptionMapper -> exceptionMapper.test(throwable))
                .findFirst()
                .orElse(DefaultExceptionMapper.EMPTY_MAPPER)
                .apply(throwable);
    }

    @Override
    public boolean test(final Throwable throwable)
    {
        return exceptionMappers.stream().anyMatch(exceptionMapper -> exceptionMapper.test(throwable));
    }

    private List<ExceptionMapper> createExceptionMappers(final AnnotatedElement annotatedElement)
    {
        final ExceptionMapping[] exceptionMappings = annotatedElement.getDeclaredAnnotationsByType(ExceptionMapping.class);
        final Stream<BaseExceptionMapper> baseExceptionMappers = Stream.of(exceptionMappings)
                .flatMap(Stream::of)
                .map(this::createExceptionMapper);

        return Stream.concat(baseExceptionMappers, Stream.empty()).collect(Collectors.toList());
    }

    private BaseExceptionMapper createExceptionMapper(final ExceptionMapping exceptionMapping)
    {
        final ThrowableMatcher throwableMatcher = createThrowableMatcher(exceptionMapping);
        final ThrowableMapper throwableMapper = createThrowableMapper(exceptionMapping);
        return new BaseExceptionMapper(throwableMatcher, throwableMapper);
    }

    private ThrowableMapper createThrowableMapper(final ExceptionMapping exceptionMapping)
    {
        final ThrowableMapper throwableMapper;
        if (!exceptionMapping.errorDetails().equals(EmptyErrorDetails.class)) {
            throwableMapper = new StaticThrowableMapper((ErrorDetails) initializeObject(exceptionMapping.errorDetails()));
        } else {
            throwableMapper = (ThrowableMapper) initializeObject(exceptionMapping.mapper());
        }
        return throwableMapper;
    }

    private ThrowableMatcher createThrowableMatcher(final ExceptionMapping exceptionMapping)
    {
        final ThrowableMatcher throwableMatcher;
        if (!exceptionMapping.exception().equals(ExceptionMapping.EmptyException.class)) {
            throwableMatcher = new StaticThrowableMatcher(exceptionMapping.exception());
        } else {
            throwableMatcher = (ThrowableMatcher) initializeObject(exceptionMapping.matcher());
        }
        return throwableMatcher;
    }

    private Object initializeObject(final Class clazz)
    {
        try {
            return clazz.getConstructors()[0].newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException(); // TODO
        }
    }
}
