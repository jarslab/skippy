package com.jarslab.skippy.dropwizard.legacy;

import com.jarslab.skippy.http.BaseHttpErrorDetails;
import org.eclipse.jetty.server.Response;
import org.glassfish.jersey.server.internal.LocalizationMessages;
import com.jarslab.skippy.ErrorDetails;
import com.jarslab.skippy.spi.ExceptionMapper;

public class IllegalFormParamContentTypeExceptionMapper implements ExceptionMapper
{
    @Override
    public ErrorDetails apply(final Throwable throwable)
    {
        return BaseHttpErrorDetails.of(Response.SC_UNSUPPORTED_MEDIA_TYPE, throwable.getLocalizedMessage());
    }

    @Override
    public boolean test(final Throwable throwable)
    {
        return throwable.getClass().isAssignableFrom(IllegalStateException.class) &&
                LocalizationMessages.FORM_PARAM_CONTENT_TYPE_ERROR().equals(throwable.getMessage());
    }
}
