package org.skipta.skipta.core;

import org.skipta.skipta.api.EmptyThrowableMapper;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

public class ClassExceptionMapperProvider implements MethodExceptionMapperProvider
{
    private final ExceptionMapper defaultExceptionMapper;
    private final Map<Class, ExceptionMapper> classesMapper;
    private final Map<Method, ExceptionMapper> methodsMappers;

    public ClassExceptionMapperProvider(final ExceptionMapper defaultExceptionMapper,
                                        final Collection<Class<?>> classes)
    {
        this.defaultExceptionMapper = requireNonNull(defaultExceptionMapper);
        this.classesMapper = classes.stream()
                .collect(Collectors.toMap(Function.identity(), AnnotatedElementExceptionMapper::new));
        this.methodsMappers = classes.stream()
                .map(Class::getDeclaredMethods)
                .flatMap(Stream::of)
                .collect(Collectors.toMap(Function.identity(), AnnotatedElementExceptionMapper::new));
    }

    public ClassExceptionMapperProvider(final Collection<Class<?>> classes)
    {
        this(new DefaultExceptionMapper(new EmptyThrowableMapper()), classes);
    }

    public ClassExceptionMapperProvider(final Class<?>... classes)
    {
        this(Stream.of(classes).collect(Collectors.toList()));
    }

    @Override
    public ExceptionMapper apply(final Method method)
    {
        return methodsMappers.getOrDefault(method,
                classesMapper.getOrDefault(method.getDeclaringClass(), defaultExceptionMapper));
    }
}
