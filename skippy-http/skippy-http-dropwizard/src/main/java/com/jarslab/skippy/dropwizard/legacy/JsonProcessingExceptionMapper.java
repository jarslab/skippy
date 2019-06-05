package com.jarslab.skippy.dropwizard.legacy;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.jarslab.skippy.ErrorDetails;
import com.jarslab.skippy.dropwizard.BaseHttpErrorDetails;
import com.jarslab.skippy.spi.ExceptionMapper;
import org.eclipse.jetty.server.Response;

/**
 * {@link io.dropwizard.jersey.jackson.JsonProcessingExceptionMapper}
 */
public class JsonProcessingExceptionMapper implements ExceptionMapper
{
    private static final String MESSAGE = "Unable to process JSON";

    private final boolean showDetails;

    public JsonProcessingExceptionMapper(final boolean showDetails)
    {
        this.showDetails = showDetails;
    }

    @Override
    public ErrorDetails apply(final Throwable throwable)
    {
        final JsonProcessingException exception = (JsonProcessingException) throwable;
        final String details = showDetails ? exception.getOriginalMessage() : null;
        return BaseHttpErrorDetails.of(Response.SC_BAD_REQUEST, MESSAGE, details);
    }

    @Override
    public boolean test(final Throwable throwable)
    {
        if (JsonProcessingException.class.isAssignableFrom(throwable.getClass())) {
            return !(throwable instanceof JsonGenerationException || throwable instanceof InvalidDefinitionException);
        }
        return false;
    }
}
