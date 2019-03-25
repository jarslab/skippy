package org.skipta.skipta.core;

import org.junit.Test;
import org.skipta.skipta.IllegalStateErrorDetails;
import org.skipta.skipta.OverallErrorDetails;
import org.skipta.skipta.api.ErrorDetails;
import org.skipta.skipta.api.ExceptionMapping;

import static org.assertj.core.api.Assertions.assertThat;

public class ClassExceptionMapperProviderTest
{
    @ExceptionMapping(exception = IllegalStateException.class, errorDetails = OverallErrorDetails.class)
    private class SuspiciousClass
    {
        @ExceptionMapping(exception = IllegalStateException.class, errorDetails = IllegalStateErrorDetails.class)
        public void annotatedMethod()
        {
        }

        public void notAnnotatedMethod()
        {
        }
    }

    private class NotAnnotatedClass
    {
        public void notAnnotatedMethod()
        {
        }
    }

    private ClassExceptionMapperProvider classExceptionMapperProvider;

    @Test
    public void shouldUseClassAnnotation() throws NoSuchMethodException
    {
        //given
        classExceptionMapperProvider = new ClassExceptionMapperProvider(SuspiciousClass.class);
        final IllegalStateException exception = new IllegalStateException();
        //when
        final ExceptionMapper mapper =
                classExceptionMapperProvider.getMapper(SuspiciousClass.class.getMethod("notAnnotatedMethod"));
        final boolean test = mapper.test(exception);
        final ErrorDetails errorDetails = mapper.apply(exception);
        //then
        assertThat(test).isTrue();
        assertThat(errorDetails).isInstanceOf(OverallErrorDetails.class);
    }

    @Test
    public void shouldUseMethodAnnotation() throws NoSuchMethodException
    {
        //given
        classExceptionMapperProvider = new ClassExceptionMapperProvider(SuspiciousClass.class);
        final IllegalStateException exception = new IllegalStateException();
        //when
        final ExceptionMapper mapper =
                classExceptionMapperProvider.getMapper(SuspiciousClass.class.getMethod("annotatedMethod"));
        final boolean test = mapper.test(exception);
        final ErrorDetails errorDetails = mapper.apply(exception);
        //then
        assertThat(test).isTrue();
        assertThat(errorDetails).isInstanceOf(IllegalStateErrorDetails.class);
    }

    @Test
    public void shouldReturnEmptyOptionalMapper() throws NoSuchMethodException
    {
        //given
        classExceptionMapperProvider = new ClassExceptionMapperProvider(NotAnnotatedClass.class);
        //when
        final ExceptionMapper emptyMapper =
                classExceptionMapperProvider.getMapper(NotAnnotatedClass.class.getMethod("notAnnotatedMethod"));
        //then
        assertThat(emptyMapper.isEmpty()).isTrue();
    }

    @Test
    public void shouldReturnEmptyOptionalForDifferentMethod() throws NoSuchMethodException
    {
        //given
        classExceptionMapperProvider = new ClassExceptionMapperProvider(SuspiciousClass.class);
        //when
        final ExceptionMapper emptyMapper =
                classExceptionMapperProvider.getMapper(NotAnnotatedClass.class.getMethod("notAnnotatedMethod"));
        //then
        assertThat(emptyMapper.isEmpty()).isTrue();
    }
}
