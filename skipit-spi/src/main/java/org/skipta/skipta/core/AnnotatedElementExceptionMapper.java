package org.skipta.skipta.core;

import org.skipta.skipta.api.EmptyErrorDetails;
import org.skipta.skipta.api.EmptyThrowableMapper;
import org.skipta.skipta.api.ErrorDetails;
import org.skipta.skipta.api.ExceptionMapping;
import org.skipta.skipta.api.RejectThrowableMatcher;
import org.skipta.skipta.api.ThrowableMapper;
import org.skipta.skipta.api.ThrowableMatcher;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AnnotatedElementExceptionMapper implements CombinedExceptionMapper
{
    private static final String ILLEGAL_MAPPERS_MESSAGE = "Error details can't be defined with mapper.";
    private static final String ILLEGAL_MATCHERS_MESSAGE = "Exception can't be defined with matcher.";
    private static final String INITIALIZATION_ERROR_MESSAGE = "Class %s can't be initialized.";

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
        final boolean errorDetailsDefined = !exceptionMapping.errorDetails().equals(EmptyErrorDetails.class);
        final boolean mapperDefined = !exceptionMapping.mapper().equals(EmptyThrowableMapper.class);

        if (errorDetailsDefined && mapperDefined) {
            throw new IllegalArgumentException(ILLEGAL_MAPPERS_MESSAGE);
        } else if (mapperDefined) {
            return (ThrowableMapper) initializeObject(exceptionMapping.mapper());
        } else {
            return new ExactThrowableMapper((ErrorDetails) initializeObject(exceptionMapping.errorDetails()));
        }
    }

    private ThrowableMatcher createThrowableMatcher(final ExceptionMapping exceptionMapping)
    {
        final Class<? extends Throwable>[] exceptions = exceptionMapping.exception();
        final boolean exceptionsDefined = !(exceptions.length == 1 &&
                Arrays.asList(exceptions).contains(ExceptionMapping.EmptyException.class));
        final boolean matcherDefined = exceptionMapping.matcher() != RejectThrowableMatcher.class;
        if (exceptionsDefined && matcherDefined) {
            throw new IllegalArgumentException(ILLEGAL_MATCHERS_MESSAGE);
        } else if (matcherDefined) {
            return (ThrowableMatcher) initializeObject(exceptionMapping.matcher());
        } else {
            return new ExactExceptionMatcher(exceptions);
        }
    }

    private Object initializeObject(final Class clazz)
    {
        try {
            return clazz.getConstructors()[0].newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException(String.format(INITIALIZATION_ERROR_MESSAGE, clazz));
        }
    }
}
