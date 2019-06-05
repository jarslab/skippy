package com.jarslab.skippy.dropwizard;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Objects;
import javax.ws.rs.core.Response;

public class BaseResponseErrorDetails implements ResponseErrorDetails
{
    private final Response response;

    public static ResponseErrorDetails of(final Response response)
    {
        return new BaseResponseErrorDetails(response);
    }

    private BaseResponseErrorDetails(final Response response)
    {
        this.response = checkNotNull(response);
    }

    @Override
    public Response getResponse()
    {
        return response;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        final BaseResponseErrorDetails that = (BaseResponseErrorDetails) o;
        return Objects.equals(response, that.response);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(response);
    }
}
