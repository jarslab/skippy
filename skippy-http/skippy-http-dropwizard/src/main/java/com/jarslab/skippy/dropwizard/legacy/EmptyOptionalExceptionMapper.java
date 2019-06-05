package com.jarslab.skippy.dropwizard.legacy;

import com.jarslab.skippy.ErrorDetails;
import com.jarslab.skippy.dropwizard.BaseResponseErrorDetails;
import com.jarslab.skippy.spi.ExceptionMapper;
import io.dropwizard.jersey.optional.EmptyOptionalException;
import javax.ws.rs.core.Response;

/**
 * {@link io.dropwizard.jersey.optional.EmptyOptionalExceptionMapper}
 */
public class EmptyOptionalExceptionMapper implements ExceptionMapper
{
    @Override
    public ErrorDetails apply(final Throwable throwable)
    {
        return BaseResponseErrorDetails.of(Response.status(Response.Status.NOT_FOUND).build());
    }

    @Override
    public boolean test(final Throwable throwable)
    {
        return throwable instanceof EmptyOptionalException;
    }
}
