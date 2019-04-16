package com.jarslab.skippy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Provides the formula for matching and mapping throwables for annotated classes and methods.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Repeatable(value = ExceptionMappings.class)
public @interface ExceptionMapping
{
    /**
     * @return array of class throwables that will be mapped
     */
    Class<? extends Throwable>[] exceptions() default EmptyException.class;

    /**
     * @return flag to enable strict (equals) throwables matching
     */
    boolean strict() default false;

    /**
     * @return class of details describing matched throwable
     * @see ErrorDetails
     */
    Class<? extends ErrorDetails> errorDetails() default EmptyErrorDetails.class;

    /**
     * @return class of throwable matcher
     * @see ThrowableMatcher
     */
    Class<? extends ThrowableMatcher> matcher() default RejectThrowableMatcher.class;

    /**
     * @return class of throwable mapper
     * @see ThrowableMapper
     */
    Class<? extends ThrowableMapper> mapper() default EmptyThrowableMapper.class;
}
