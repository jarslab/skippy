package com.jarslab.skippy.dropwizard.legacy;

import com.jarslab.skippy.ErrorDetails;
import com.jarslab.skippy.dropwizard.BaseHttpErrorDetails;
import com.jarslab.skippy.dropwizard.BaseResponseErrorDetails;
import com.jarslab.skippy.spi.ExceptionMapper;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class WebApplicationExceptionMapper implements ExceptionMapper
{
    @Override
    public ErrorDetails apply(final Throwable throwable)
    {
        final WebApplicationException exception = (WebApplicationException) throwable;
        final Response response = exception.getResponse();
        Response.Status.Family family = response.getStatusInfo().getFamily();
        if (family.equals(Response.Status.Family.REDIRECTION)) {
            return BaseResponseErrorDetails.of(response);
        }
        return BaseHttpErrorDetails.of(response.getStatus(), exception.getLocalizedMessage());
    }

    @Override
    public boolean test(final Throwable throwable)
    {
        return WebApplicationException.class.isAssignableFrom(throwable.getClass()) &&
            !((WebApplicationException) throwable).getResponse().getStatusInfo().getFamily()
                .equals(Response.Status.Family.SERVER_ERROR);
    }
}
