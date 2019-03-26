package com.jarslab.skippy.dropwizard.legacy;

import com.jarslab.skippy.http.BaseHttpErrorDetails;
import org.eclipse.jetty.server.Response;
import com.jarslab.skippy.ErrorDetails;
import com.jarslab.skippy.spi.ExceptionMapper;

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
        return throwable instanceof EOFException;
    }
}
