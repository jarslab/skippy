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

class AnnotatedElementExceptionMapper implements CombinedExceptionMapper
{
    private final List<ExceptionMapper> exceptionMappers;

    AnnotatedElementExceptionMapper(final AnnotatedElement annotatedElement)
    {
        exceptionMappers = createExceptionMappers(annotatedElement);
    }

    @Override
    public List<ExceptionMapper> getMappers()
    {
        return exceptionMappers;
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
