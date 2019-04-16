package com.jarslab.skippy.spi;

import com.jarslab.skippy.EmptyErrorDetails;
import com.jarslab.skippy.EmptyException;
import com.jarslab.skippy.EmptyThrowableMapper;
import com.jarslab.skippy.ErrorDetails;
import com.jarslab.skippy.ExceptionMapping;
import com.jarslab.skippy.RejectThrowableMatcher;
import com.jarslab.skippy.ThrowableMapper;
import com.jarslab.skippy.ThrowableMatcher;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AnnotatedElementExceptionMapper implements CombinedExceptionMapper
{
    private static final String ILLEGAL_MAPPERS_MESSAGE = "Error details can't be defined with mapper.";
    private static final String ILLEGAL_MATCHERS_MESSAGE = "Literal exceptions cannot be defined along with matcher.";
    private static final String INITIALIZATION_ERROR_MESSAGE = "Class %s cannot be instantiated.";

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
        final Class<? extends Throwable>[] exceptions = exceptionMapping.exceptions();
        final boolean defaultExceptionsDefined = !(exceptions.length == 1 && exceptions[0] == EmptyException.class);
        final boolean matcherDefined = exceptionMapping.matcher() != RejectThrowableMatcher.class;
        if (defaultExceptionsDefined && matcherDefined) {
            throw new IllegalArgumentException(ILLEGAL_MATCHERS_MESSAGE);
        } else if (matcherDefined) {
            return (ThrowableMatcher) initializeObject(exceptionMapping.matcher());
        } else {
            final boolean strict = exceptionMapping.strict();
            return new ExactExceptionMatcher(strict, exceptions);
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
