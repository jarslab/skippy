package com.jarslab.skippy.spi;

import com.jarslab.skippy.ExceptionMapping;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A provider that returns matched {@link ExceptionMapper} for {@link Method}.
 * <p>
 * {@code ClassExceptionMapperProvider} builds the map of exception mappers for specified classes using formulas
 * extracted from {@link ExceptionMapping} annotation. All mappers are created during instance initialization.
 * <p>
 * This returns the {@link CombinedExceptionMapper} that merges mappers defined on method level and class level. Method
 * level mappers are matched <i>before</i> class level.
 */
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
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        this.methodsMappers = classes.stream()
            .map(Class::getDeclaredMethods)
            .flatMap(Stream::of)
            .collect(Collectors.toMap(Function.identity(), AnnotatedElementExceptionMapper::new))
            .entrySet()
            .stream()
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public ClassExceptionMapperProvider(final Class<?>... classes)
    {
        this(Stream.of(classes).collect(Collectors.toList()));
    }

    public ExceptionMapper getMapper(final Method method)
    {
        if (method == null) {
            return EMPTY_MAPPER;
        } else {
            final ExceptionMapper classMapper =
                classesMapper.getOrDefault(method.getDeclaringClass(), EMPTY_MAPPER);
            final ExceptionMapper methodMapper = methodsMappers.getOrDefault(method, EMPTY_MAPPER);
            return (CombinedExceptionMapper) () -> Arrays.asList(methodMapper, classMapper);
        }
    }
}
