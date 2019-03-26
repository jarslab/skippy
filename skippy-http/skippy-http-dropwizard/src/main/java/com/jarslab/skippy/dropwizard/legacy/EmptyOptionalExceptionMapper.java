package com.jarslab.skippy.dropwizard.legacy;

import com.jarslab.skippy.http.BaseHttpErrorDetails;
import io.dropwizard.jersey.optional.EmptyOptionalException;
import org.eclipse.jetty.server.Response;
import com.jarslab.skippy.ErrorDetails;
import com.jarslab.skippy.spi.ExceptionMapper;

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
