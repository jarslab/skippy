package com.jarslab.skippy.spi;

import org.junit.Test;
import com.jarslab.skippy.IllegalStateErrorDetails;
import com.jarslab.skippy.OverallErrorDetails;
import com.jarslab.skippy.AcceptThrowableMatcher;
import com.jarslab.skippy.EmptyErrorDetails;
import com.jarslab.skippy.ErrorDetails;
import com.jarslab.skippy.ExceptionMapping;
import com.jarslab.skippy.ExceptionMappings;

import static org.assertj.core.api.Assertions.assertThat;

public class AnnotatedElementExceptionMapperTest
{
    private AnnotatedElementExceptionMapper annotatedElementExceptionMapper;

    @ExceptionMapping(exception = IllegalStateException.class, errorDetails = IllegalStateErrorDetails.class)
    private class AnnotatedClass
    {
    }

    private class NotAnnotatedClass
    {
    }

    private class MethodClass
    {
        @ExceptionMapping(exception = IllegalStateException.class, errorDetails = IllegalStateErrorDetails.class)
        public void annotatedMethod()
        {
        }

        public void notAnnotatedMethod()
        {
        }
    }

    @ExceptionMappings({
            @ExceptionMapping(exception = IllegalStateException.class, errorDetails = IllegalStateErrorDetails.class),
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
        //when
        final boolean result = annotatedElementExceptionMapper.test(new IllegalStateException());
        final ErrorDetails errorDetails = annotatedElementExceptionMapper.apply(new IllegalStateException());
        //then
        assertThat(result).isTrue();
        assertThat(errorDetails).isInstanceOf(IllegalStateErrorDetails.class);
    }

    @Test
    public void shouldMapToEmptyWhenNotAnnotatedClass()
    {
        //given
        annotatedElementExceptionMapper = new AnnotatedElementExceptionMapper(NotAnnotatedClass.class);
        //when
        final boolean result = annotatedElementExceptionMapper.test(new IllegalStateException());
        final ErrorDetails errorDetails = annotatedElementExceptionMapper.apply(new IllegalStateException());
        //then
        assertThat(result).isFalse();
        assertThat(errorDetails).isInstanceOf(EmptyErrorDetails.class);
    }

    @Test
    public void shouldMapMethodThrowable() throws NoSuchMethodException
    {
        //given
        annotatedElementExceptionMapper = new AnnotatedElementExceptionMapper(MethodClass.class.getMethod("annotatedMethod"));
        //when
        final boolean result = annotatedElementExceptionMapper.test(new IllegalStateException());
        final ErrorDetails errorDetails = annotatedElementExceptionMapper.apply(new IllegalStateException());
        //then
        assertThat(result).isTrue();
        assertThat(errorDetails).isInstanceOf(IllegalStateErrorDetails.class);
    }

    @Test
    public void shouldMapToEmptyWhenNotAnnotatedMethod() throws NoSuchMethodException
    {
        //given
        annotatedElementExceptionMapper = new AnnotatedElementExceptionMapper(MethodClass.class.getMethod("notAnnotatedMethod"));
        //when
        final boolean result = annotatedElementExceptionMapper.test(new IllegalStateException());
        final ErrorDetails errorDetails = annotatedElementExceptionMapper.apply(new IllegalStateException());
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