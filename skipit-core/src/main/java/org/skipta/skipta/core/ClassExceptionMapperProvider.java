package org.skipta.skipta.core;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClassExceptionMapperProvider
{
    private final Map<Class, CombinedExceptionMapper> classesMapper;
    private final Map<Method, CombinedExceptionMapper> methodsMappers;

    public ClassExceptionMapperProvider(final Collection<Class<?>> classes)
    {
        this.classesMapper = classes.stream()
                .collect(Collectors.toMap(Function.identity(), AnnotatedElementExceptionMapper::new));
        this.methodsMappers = classes.stream()
                .map(Class::getDeclaredMethods)
                .flatMap(Stream::of)
                .collect(Collectors.toMap(Function.identity(), AnnotatedElementExceptionMapper::new));
    }

    public ClassExceptionMapperProvider(final Class<?>... classes)
    {
        this(Stream.of(classes).collect(Collectors.toList()));
    }

    public Optional<ExceptionMapper> getMapper(final Method method)
    {
        return Optional.ofNullable(method)
                .map(m -> methodsMappers.getOrDefault(m, classesMapper.get(m.getDeclaringClass())))
                .flatMap(this::decorateEmptyMapper);
    }

    private Optional<ExceptionMapper> decorateEmptyMapper(final CombinedExceptionMapper combinedExceptionMapper)
    {
        return combinedExceptionMapper.getMappers().isEmpty() ? Optional.empty() : Optional.of(combinedExceptionMapper);
    }
}
