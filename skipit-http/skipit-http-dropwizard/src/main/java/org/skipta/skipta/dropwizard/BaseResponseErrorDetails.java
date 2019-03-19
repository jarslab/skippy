package org.skipta.skipta.dropwizard;

import javax.ws.rs.core.Response;

import static com.google.common.base.Preconditions.checkNotNull;

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
}
