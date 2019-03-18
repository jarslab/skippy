package org.skipta.skipta.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Repeatable(value = ExceptionMappings.class)
public @interface ExceptionMapping
{
    Class<? extends Throwable> exception() default EmptyException.class;

    Class<? extends ErrorDetails> errorDetails() default EmptyErrorDetails.class;

    Class<? extends ThrowableMatcher> matcher() default RejectThrowableMatcher.class;

    Class<? extends ThrowableMapper> mapper() default EmptyThrowableMapper.class;

    abstract class EmptyException extends Throwable {}
}
