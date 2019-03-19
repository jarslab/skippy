package org.skipta.skipta.dropwizard.legacy;

import io.dropwizard.jersey.optional.EmptyOptionalException;
import org.eclipse.jetty.server.Response;
import org.skipta.skipta.api.ErrorDetails;
import org.skipta.skipta.core.ExceptionMapper;
import org.skipta.skipta.http.BaseHttpErrorDetails;

public class EmptyOptionalExceptionMapper implements ExceptionMapper
{
    private static final String MESSAGE = "Not found";

    @Override
    public ErrorDetails apply(final Throwable throwable)
    {
        return BaseHttpErrorDetails.of(Response.SC_NOT_FOUND, MESSAGE);
    }

    @Override
    public boolean test(final Throwable throwable)
    {
        return throwable instanceof EmptyOptionalException;
    }
}
