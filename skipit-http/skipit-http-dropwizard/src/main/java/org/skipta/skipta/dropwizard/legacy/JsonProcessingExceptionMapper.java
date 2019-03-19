package org.skipta.skipta.dropwizard.legacy;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import org.eclipse.jetty.server.Response;
import org.skipta.skipta.api.ErrorDetails;
import org.skipta.skipta.core.ExceptionMapper;
import org.skipta.skipta.http.BaseHttpErrorDetails;


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
        if (throwable.getClass().isAssignableFrom(JsonProcessingException.class)) {
            return !(throwable instanceof JsonGenerationException || throwable instanceof InvalidDefinitionException);
        }
        return false;
    }
}
