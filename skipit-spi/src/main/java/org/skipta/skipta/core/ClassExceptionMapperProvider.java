package org.skipta.skipta.core;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

public class ClassExceptionMapperProvider
{
    public final static ExceptionMapper EMPTY_MAPPER = new EmptyExceptionMapper();

    private final Map<Class, ExceptionMapper> classesMapper;
    private final Map<Method, ExceptionMapper> methodsMappers;

    public ClassExceptionMapperProvider(final Collection<Class<?>> classes)
    {
        this.classesMapper = classes.stream()
                .collect(Collectors.toMap(Function.identity(), AnnotatedElementExceptionMapper::new))
                .entrySet()
                .stream()
                .filter(entry -> !entry.getValue().isEmpty())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        this.methodsMappers = classes.stream()
                .map(Class::getDeclaredMethods)
                .flatMap(Stream::of)
                .collect(Collectors.toMap(Function.identity(), AnnotatedElementExceptionMapper::new))
                .entrySet()
                .stream()
                .filter(entry -> !entry.getValue().isEmpty())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public ClassExceptionMapperProvider(final Class<?>... classes)
    {
        this(Stream.of(classes).collect(Collectors.toList()));
    }

    public ExceptionMapper getMapper(final Method method)
    {
        requireNonNull(method);
        return methodsMappers.getOrDefault(method,
                classesMapper.getOrDefault(method.getDeclaringClass(), EMPTY_MAPPER));
    }
}
