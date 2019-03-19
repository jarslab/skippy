package org.skipta.skipta;

import com.fasterxml.jackson.core.JsonParseException;
import org.eclipse.jetty.server.Response;
import org.skipta.skipta.api.ErrorDetails;
import org.skipta.skipta.core.ExceptionMapper;
import org.skipta.skipta.http.BaseHttpErrorDetails;

public class JsonParseExceptionMapper implements ExceptionMapper
{
    @Override
    public ErrorDetails apply(final Throwable throwable)
    {
        return BaseHttpErrorDetails.of(Response.SC_BAD_REQUEST, throwable.getMessage());
    }

    @Override
    public boolean test(final Throwable throwable)
    {
        return throwable instanceof JsonParseException;
    }
}
