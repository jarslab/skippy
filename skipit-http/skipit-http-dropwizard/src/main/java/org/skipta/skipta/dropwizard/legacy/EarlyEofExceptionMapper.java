package org.skipta.skipta.dropwizard.legacy;

import org.eclipse.jetty.server.Response;
import org.skipta.skipta.api.ErrorDetails;
import org.skipta.skipta.core.ExceptionMapper;
import org.skipta.skipta.http.BaseHttpErrorDetails;

import java.io.EOFException;

public class EarlyEofExceptionMapper implements ExceptionMapper
{
    private static final String MESSAGE = "End of stream unexpectedly reached.";

    @Override
    public ErrorDetails apply(final Throwable throwable)
    {
        return BaseHttpErrorDetails.of(Response.SC_BAD_REQUEST, MESSAGE);
    }

    @Override
    public boolean test(final Throwable throwable)
    {
        return throwable.getClass().isAssignableFrom(EOFException.class);
    }
}
