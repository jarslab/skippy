package com.jarslab.skippy.spi;

import static org.assertj.core.api.Assertions.assertThat;

import com.jarslab.skippy.AcceptThrowableMatcher;
import com.jarslab.skippy.EmptyErrorDetails;
import com.jarslab.skippy.ErrorDetails;
import com.jarslab.skippy.ExceptionMapping;
import com.jarslab.skippy.ExceptionMappings;
import com.jarslab.skippy.IllegalStateErrorDetails;
import com.jarslab.skippy.OverallErrorDetails;
import org.junit.Test;

public class AnnotatedElementExceptionMapperTest
{
    private AnnotatedElementExceptionMapper annotatedElementExceptionMapper;

    @ExceptionMapping(exceptions = IllegalStateException.class,
        errorDetails = IllegalStateErrorDetails.class,
        strict = true)
    @ExceptionMapping(exceptions = RuntimeException.class, errorDetails = OverallErrorDetails.class)
    private class AnnotatedClass
    {
    }

    private class NotAnnotatedClass
    {
    }

    private class MethodClass
    {
        @ExceptionMapping(exceptions = IllegalStateException.class, errorDetails = IllegalStateErrorDetails.class)
        public void annotatedMethod()
        {
        }

        public void notAnnotatedMethod()
        {
        }
    }

    @ExceptionMappings({
        @ExceptionMapping(exceptions = IllegalStateException.class, errorDetails = IllegalStateErrorDetails.class),
        @ExceptionMapping(matcher = AcceptThrowableMatcher.class, errorDetails = OverallErrorDetails.class)
    })
    private class DefaultMappingClass
    {
    }

    @Test
    public void shouldMapClassThrowable()
    {
        //given
        annotatedElementExceptionMapper = new AnnotatedElementExceptionMapper(AnnotatedClass.class);
        final IllegalStateException exception = new IllegalStateException();
        //when
        final boolean result = annotatedElementExceptionMapper.test(exception);
        final ErrorDetails errorDetails = annotatedElementExceptionMapper.apply(exception);
        //then
        assertThat(result).isTrue();
        assertThat(errorDetails).isInstanceOf(IllegalStateErrorDetails.class);
    }

    @Test
    public void shouldMapDescendantThrowable()
    {
        //given
        annotatedElementExceptionMapper = new AnnotatedElementExceptionMapper(AnnotatedClass.class);
        final IllegalArgumentException exception = new IllegalArgumentException();
        //when
        final boolean result = annotatedElementExceptionMapper.test(exception);
        final ErrorDetails errorDetails = annotatedElementExceptionMapper.apply(exception);
        //then
        assertThat(result).isTrue();
        assertThat(errorDetails).isInstanceOf(OverallErrorDetails.class);
    }

    @Test
    public void shouldMapToEmptyWhenNotAnnotatedClass()
    {
        //given
        annotatedElementExceptionMapper = new AnnotatedElementExceptionMapper(NotAnnotatedClass.class);
        final IllegalStateException exception = new IllegalStateException();
        //when
        final boolean result = annotatedElementExceptionMapper.test(exception);
        final ErrorDetails errorDetails = annotatedElementExceptionMapper.apply(exception);
        //then
        assertThat(result).isFalse();
        assertThat(errorDetails).isInstanceOf(EmptyErrorDetails.class);
    }

    @Test
    public void shouldMapMethodThrowable() throws NoSuchMethodException
    {
        //given
        annotatedElementExceptionMapper =
            new AnnotatedElementExceptionMapper(MethodClass.class.getMethod("annotatedMethod"));
        final IllegalStateException exception = new IllegalStateException();
        //when
        final boolean result = annotatedElementExceptionMapper.test(exception);
        final ErrorDetails errorDetails = annotatedElementExceptionMapper.apply(exception);
        //then
        assertThat(result).isTrue();
        assertThat(errorDetails).isInstanceOf(IllegalStateErrorDetails.class);
    }

    @Test
    public void shouldMapToEmptyWhenNotAnnotatedMethod() throws NoSuchMethodException
    {
        //given
        annotatedElementExceptionMapper =
            new AnnotatedElementExceptionMapper(MethodClass.class.getMethod("notAnnotatedMethod"));
        final IllegalStateException exception = new IllegalStateException();
        //when
        final boolean result = annotatedElementExceptionMapper.test(exception);
        final ErrorDetails errorDetails = annotatedElementExceptionMapper.apply(exception);
        //then
        assertThat(result).isFalse();
        assertThat(errorDetails).isInstanceOf(EmptyErrorDetails.class);
    }

    @Test
    public void shouldMatchOrderInMappings()
    {
        //given
        annotatedElementExceptionMapper = new AnnotatedElementExceptionMapper(DefaultMappingClass.class);
        //when
        final ErrorDetails errorDetails1 = annotatedElementExceptionMapper.apply(new IllegalStateException());
        final ErrorDetails errorDetails2 = annotatedElementExceptionMapper.apply(new IllegalArgumentException());
        //then
        assertThat(errorDetails1).isInstanceOf(IllegalStateErrorDetails.class);
        assertThat(errorDetails2).isInstanceOf(OverallErrorDetails.class);
    }
}